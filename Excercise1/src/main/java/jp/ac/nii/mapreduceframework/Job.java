package jp.ac.nii.mapreduceframework;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import jp.ac.nii.mapreduceframework.util.Pair;

public class Job<InKey, InValue, InterKey extends Comparable<InterKey>, InterValue, OutKey, OutValue> {
	private Class<? extends InputFormat<InKey, InValue>> inputFormatClass;
	private Class<? extends OutputFormat> outputFormatClass;
	private Class<? extends Mapper<InKey, InValue, InterKey, InterValue>> mapperClass;
	private Class<? extends Reducer<InterKey, InterValue, OutKey, OutValue>> reducerClass;
	private Class<? extends Partitioner<InterKey, InterValue>> partitionerClass;
	private Class<? extends Comparator<InterKey>> sortComparatorClass;
	private Class<? extends Comparator<InterKey>> groupingComparatorClass;
	private int numReduceTasks;

	private Job() {
	}

	public static <InKey, InValue, InterKey extends Comparable<InterKey>, InterValue, OutKey, OutValue> Job<InKey, InValue, InterKey, InterValue, OutKey, OutValue> getInstance() {
		return new Job<InKey, InValue, InterKey, InterValue, OutKey, OutValue>();
	}

	/**
	 * MapReduce を開始します。
	 * 
	 * @param lines
	 *            MapReduceの対象となるデータ。
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void waitForCompletion()
			throws InstantiationException, IllegalAccessException, FileNotFoundException, UnsupportedEncodingException {
		if (inputFormatClass == null || outputFormatClass == null || mapperClass == null || reducerClass == null
				|| numReduceTasks <= 0) {
			throw new RuntimeException("MapReduce does not be correctly initialized.");
		}

		// 各種オブジェクトの初期化
		Shuffler<InterKey, InterValue> shuffler = initializeShuffler();

		InputFormat<InKey, InValue> inputFormat = inputFormatClass.newInstance();
		List<InputData<InKey, InValue>> inputData = inputFormat.readAll(this);
		int numMapperTasks = inputData.size();

		OutputFormat outputFormat = outputFormatClass.newInstance();

		// 並列実行するために複数のMapperタスクに対応するオブジェクトを生成する
		List<Mapper<InKey, InValue, InterKey, InterValue>> mappers = Lists.newArrayList();
		for (int i = 0; i < numMapperTasks; i++) {
			mappers.add(mapperClass.newInstance());
		}
		System.out.println("# Mappers: " + mappers.size());

		// 並列実行するために複数のReducerタスクに対応するオブジェクトを生成する
		List<Reducer<InterKey, InterValue, OutKey, OutValue>> reducers = Lists.newArrayList();
		for (int i = 0; i < numReduceTasks; i++) {
			reducers.add(reducerClass.newInstance());
		}
		System.out.println("# Reducers: " + reducers.size());

		// Mapperでmapを実行してから、Reducerでreduceを実行する
		mapParallel(shuffler, mappers, inputData);
		shuffler.shuffleAndSort();
		reduceParallel(shuffler, reducers, outputFormat);
	}

	/**
	 * ShufflerとShufflerの初期化に必要なオブジェクトを初期化します。
	 * 
	 * @return 初期化したShufflerのオブジェクト。
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Shuffler<InterKey, InterValue> initializeShuffler() throws InstantiationException, IllegalAccessException {
		Comparator<InterKey> sortComparator;
		if (sortComparatorClass == null) {
			sortComparator = Ordering.natural();
		} else {
			sortComparator = sortComparatorClass.newInstance();
		}
		Partitioner<InterKey, InterValue> partitioner;
		if (partitionerClass == null) {
			partitioner = new HashPartitioner<InterKey, InterValue>();
		} else {
			partitioner = partitionerClass.newInstance();
		}
		Comparator<InterKey> groupingComparator;
		if (groupingComparatorClass == null) {
			groupingComparator = Ordering.natural();
		} else {
			groupingComparator = groupingComparatorClass.newInstance();
		}
		Shuffler<InterKey, InterValue> shuffler = new Shuffler<InterKey, InterValue>(sortComparator, partitioner,
				groupingComparator, numReduceTasks);
		return shuffler;
	}

	/**
	 * 複数のMapperタスクでmapを並列に実行する。
	 * 
	 * @param shuffler
	 * @param mappers
	 *            Mapperインスタンスのリスト
	 * @param inputDataList
	 *            入力データ
	 */
	private void mapParallel(final Shuffler<InterKey, InterValue> shuffler,
			List<Mapper<InKey, InValue, InterKey, InterValue>> mappers, List<InputData<InKey, InValue>> inputDataList) {
		// 簡単のため並列ではなく逐次実行する
		for (int i = 0; i < mappers.size(); i++) {
			Mapper<InKey, InValue, InterKey, InterValue> mapper = mappers.get(i);
			final InputData<InKey, InValue> inputData = inputDataList.get(i);
			for (final Pair<InKey, InValue> pair : inputData.getData()) {
				Context context = new Context() {
					@SuppressWarnings("unchecked")
					@Override
					public void write(Object key, Object value) {
						shuffler.add((InterKey) key, (InterValue) value);
					}

					@Override
					public String getPath() {
						return inputData.getPath();
					}
				};
				mapper.map(pair.getKey(), pair.getValue(), context);
			}
		}
	}

	/**
	 * 複数のReducerタスクでreduceを並列に実行する。
	 * 
	 * @param shuffler
	 *            Shuffler インスタンス
	 * @param reducers
	 *            Reducer インスタンスのリスト
	 * @param outputFormat
	 */
	private void reduceParallel(Shuffler<InterKey, InterValue> shuffler,
			List<Reducer<InterKey, InterValue, OutKey, OutValue>> reducers, final OutputFormat outputFormat) {
		// 簡単のため並列ではなく逐次実行する
		for (int i = 0; i < reducers.size(); i++) {
			outputFormat.setup(this, i);
			Reducer<InterKey, InterValue, OutKey, OutValue> reducer = reducers.get(i);
			TreeMap<InterKey, List<InterValue>> keyValueMap = shuffler.getKeyValueMaps().get(i);
			System.out.println("Reducer " + i + " will process " + keyValueMap.size() + " records.");
			for (Entry<InterKey, List<InterValue>> keyValue : keyValueMap.entrySet()) {
				Context context = new Context() {
					@SuppressWarnings("unchecked")
					@Override
					public void write(Object key, Object value) {
						outputFormat.write((OutKey) key, (OutValue) value);
					}

					@Override
					public String getPath() {
						return null;
					}
				};
				reducer.reduce(keyValue.getKey(), keyValue.getValue(), context);
			}
			outputFormat.cleanup();
		}
	}

	public void setInputFormatClass(Class<? extends InputFormat<InKey, InValue>> inputFormatClass) {
		this.inputFormatClass = inputFormatClass;
	}

	public void setOutputFormatClass(Class<? extends OutputFormat> outputFormatClass) {
		this.outputFormatClass = outputFormatClass;
	}

	public void setMapperClass(Class<? extends Mapper<InKey, InValue, InterKey, InterValue>> mapperClass) {
		this.mapperClass = mapperClass;
	}

	public void setReducerClass(Class<? extends Reducer<InterKey, InterValue, OutKey, OutValue>> reducerClass) {
		this.reducerClass = reducerClass;
	}

	public void setPartitionerClass(Class<? extends Partitioner<InterKey, InterValue>> partitionerClass) {
		this.partitionerClass = partitionerClass;
	}

	public void setSortComparatorClass(Class<? extends Comparator<InterKey>> sortComparatorClass) {
		this.sortComparatorClass = sortComparatorClass;
	}

	public void setGroupingComparatorClass(Class<? extends Comparator<InterKey>> groupingComparatorClass) {
		this.groupingComparatorClass = groupingComparatorClass;
	}

	public void setNumReduceTasks(int numReduceTasks) {
		this.numReduceTasks = numReduceTasks;
	}
}
