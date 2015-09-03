package jp.ac.nii.mapreduceframework;

/**
 * ハッシュ値を用いて担当するReducerタスクを決めるクラスです。
 *
 * @param <Key>
 *            中間キーの型
 * @param <Value>
 *            中間バリューの型
 */
public class HashPartitioner<Key, Value> extends Partitioner<Key, Value> {
	/**
	 * 何番目のReducerにKeyとValueのペアを送るか決めます。
	 * 
	 * @param key
	 *            キー
	 * @param value
	 *            バリュー
	 * @param numReduceTasks
	 *            Reducerの個数
	 * @return Reducerのインデックス
	 */
	@Override
	public int getPartition(Key key, Value value, int numReduceTasks) {
		return Math.abs(key.hashCode()) % numReduceTasks;
	}
}
