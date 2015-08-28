package jp.ac.nii.mapreduceframework;

/**
 * 担当するReducerタスクを決めるクラスです。
 * @author exKAZUu
 *
 * @param <Key> 中間キーの型
 * @param <Value> 中間バリューの型
 */
public abstract class Partitioner<Key, Value> {
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
    public abstract int getPartition(Key key, Value value, int numReduceTasks);
}
