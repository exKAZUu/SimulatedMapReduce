package jp.ac.nii.mapreduceframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import jp.ac.nii.mapreduceframework.util.Pair;

/**
 * テキストファイルを行単位で読み込み、<行の開始位置, 行の文字列>というキーバリューの入力データを生成するクラスです。
 * 注意：本クラスは行の開始位置を文字数で表現していますが、本家Hadoopはバイト数で表現します。
 */
public class FileInputFormat extends InputFormat<Long, String> {
	private static int numLinesPerMapper = 100;
	private static Map<Job<?, ?, ?, ?, ?, ?>, List<Path>> inputPaths = Maps.newHashMap();

	/*
	 * (non-Javadoc)
	 * 
	 * @see jp.ac.nii.mapreduceframework.InputFormat#readAll(jp.ac.nii.
	 * mapreduceframework.Job)
	 */
	public List<InputData<Long, String>> readAll(Job<?, ?, ?, ?, ?, ?> job) {
		List<InputData<Long, String>> result = Lists.newArrayList();
		List<Pair<Long, String>> lines = Lists.newArrayList();
		long charCount = 0; // 注意：Hadoop本家はバイト数
		for (Path filePath : inputPaths.get(job)) {
			File rootFile = filePath.toFile();
			if (!rootFile.exists()) {
				continue;
			}

			for (File file : Files.fileTreeTraverser().preOrderTraversal(rootFile)) {
				if (file.isDirectory()) {
					continue;
				}
				FileInputStream inputStream;
				try {
					inputStream = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
				Scanner scanner = new Scanner(inputStream, "UTF-8");
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					lines.add(Pair.create(charCount, line));
					charCount += line.length();
					// 規定値に達したらデータを分割して、担当Mapperを変える
					if (lines.size() == numLinesPerMapper) {
						result.add(InputData.create(file.getPath(), lines));
						lines = Lists.newArrayList();
					}
				}
				// ファイルが変わる場合は、問答無用で分割して、担当Mapperを変える
				if (lines.size() > 0) {
					result.add(InputData.create(file.getPath(), lines));
					lines = Lists.newArrayList();
				}
				scanner.close();
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
		return result;
	}

	/**
	 * 1つのMapperタスク（ノード）が扱う行数を指定します。この数値によってMapperタスクの数が決まります。
	 * 注意：このメソッドは本家Hadoopには存在しません。
	 * 
	 * @param numLinesPerMapper
	 *            1つのMapperタスク（ノード）が扱う行数
	 */
	public static void setInputSplitNumLines(int numLinesPerMapper) {
		FileInputFormat.numLinesPerMapper = numLinesPerMapper;
	}

	/**
	 * 指定したジョブの実行時に読み込むファイルのパスを指定します。
	 * 
	 * @param job
	 *            ファイルを読み込む際のジョブ
	 * @param path
	 *            読み込むファイルのパス
	 */
	public static void addInputPath(Job<?, ?, ?, ?, ?, ?> job, Path path) {
		List<Path> list = inputPaths.get(job);
		if (list == null) {
			inputPaths.put(job, new ArrayList<Path>());
		}
		inputPaths.get(job).add(path);
	}
}
