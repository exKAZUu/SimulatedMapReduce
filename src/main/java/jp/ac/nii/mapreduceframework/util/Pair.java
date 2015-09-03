package jp.ac.nii.mapreduceframework.util;

/**
 * キーバリューを持つクラスです。 注意：このクラスは本家Hadoopには存在しません。
 * 
 * @author exKAZUu
 *
 * @param <Key>
 *            キーの型
 * @param <Value>
 *            バリューの型
 */
public class Pair<Key, Value> {

	/**
	 * このペアが持つキー。
	 */
	private final Key key;

	/**
	 * このペアが持つバリュー。
	 */
	private final Value value;

	private Pair(Key key, Value value) {
		this.key = key;
		this.value = value;
	}

	public static <Key, Value> Pair<Key, Value> create(Key key, Value value) {
		return new Pair<Key, Value>(key, value);
	}

	/**
	 * このペアが持つキーを返す。
	 * 
	 * @return このペアが持つキー。
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * このペアが持つバリューを返す。
	 * 
	 * @return このペアが持つバリュー。
	 */
	public Value getValue() {
		return value;
	}
}
