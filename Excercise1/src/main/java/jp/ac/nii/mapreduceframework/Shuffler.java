package jp.ac.nii.mapreduceframework;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import jp.ac.nii.mapreduceframework.util.Pair;

public class Shuffler<InterKey, InterValue> {
	private final Comparator<InterKey> sortComparator;
	private final Partitioner<InterKey, InterValue> partitioner;
	private final int numReduceTasks;
	private final List<Pair<InterKey, InterValue>> keyValueList;
	private final List<TreeMap<InterKey, List<InterValue>>> keyValueMaps;

	public Shuffler(Comparator<InterKey> sortComparator, Partitioner<InterKey, InterValue> partitioner,
			Comparator<InterKey> groupingComparator, int numReduceTasks) {
				this.sortComparator = sortComparator;
		this.partitioner = partitioner;
		this.numReduceTasks = numReduceTasks;
		keyValueList = Lists.newArrayList();
		keyValueMaps = Lists.newArrayList();
		for (int i = 0; i < numReduceTasks; i++) {
			keyValueMaps.add(new TreeMap<InterKey, List<InterValue>>(groupingComparator));
		}
	}

	public void add(InterKey key, InterValue value) {
		keyValueList.add(Pair.create(key, value));
	}

	public void shuffleAndSort() {
		// 1. 指定されたsortComparatorでKeyをソートする
		keyValueList.sort(new Comparator<Pair<InterKey, InterValue>>() {
			@Override
			public int compare(Pair<InterKey, InterValue> o1, Pair<InterKey, InterValue> o2) {
				return sortComparator.compare(o1.getKey(), o2.getKey());
			}
		});
		for (Pair<InterKey, InterValue> pair : keyValueList) {
			// 2. 指定されたPartitionerでキーバリューを処理するノードを決める
			InterKey key = pair.getKey();
			InterValue value = pair.getValue();
			int nodeIndex = partitioner.getPartition(key, value, numReduceTasks);
			
			// 3. 指定されたgroupingComparatorでReduceの処理単位を決める
			TreeMap<InterKey, List<InterValue>> keyValueMap = keyValueMaps.get(nodeIndex);
			if (!keyValueMap.containsKey(key)) {
				keyValueMap.put(key, new ArrayList<InterValue>());
			}
			keyValueMap.get(key).add(value);			
		}
	}

	public List<TreeMap<InterKey, List<InterValue>>> getKeyValueMaps() {
		return keyValueMaps;
	}
}