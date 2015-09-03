package jp.ac.nii.mapreduceframework;

import java.util.Map;

import com.google.common.collect.Maps;

public class Configuration {
	private final Map<String, String> keyValueStore = Maps.newHashMap();

	/**
	 * 全Mapper / Reducer で共通して参照可能なキーバリューのデータを設定します。
	 * @param name 設定するキー
	 * @param value 設定するバリュー
	 */
	public void set(String name, String value) {
		keyValueStore.put(name, value);
	}

	/**
	 * 設定済みのキーバリューのデータに対して、指定したキーに対するバリューを取得します。
	 * @param name 取得するバリューに対応するキー
	 * @return 指定したキーに対するバリュー
	 */
	public String get(String name) {
		return keyValueStore.get(name);
	}
}
