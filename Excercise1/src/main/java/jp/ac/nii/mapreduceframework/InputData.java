package jp.ac.nii.mapreduceframework;

import java.util.List;

import jp.ac.nii.mapreduceframework.util.Pair;

/**
 * 入力データを管理するクラスです。 注意：本家Hadoopには存在しません。
 */
public class InputData<Key, Value> {
	private final String filePath;
	private final List<Pair<Key, Value>> data;

	private InputData(String filePath, List<Pair<Key, Value>> data) {
		this.filePath = filePath;
		this.data = data;
	}

	public static <Key, Value> InputData<Key, Value> create(String filePath, List<Pair<Key, Value>> data) {
		return new InputData<Key, Value>(filePath, data);
	}

	public String getPath() {
		return filePath;
	}

	public List<Pair<Key, Value>> getData() {
		return data;
	}
}
