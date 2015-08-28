package jp.ac.nii.mapreduceframework;

import java.util.List;

/**
 * Mapperにデータを渡すためにデータを入力するクラスです。 注意：このクラスが持つメソッドのシグネチャは本家Hadoopと異なります。
 */
public abstract class InputFormat<Key, Value> {
	/**
	 * データをすべて読み込み、Mapper単位で分割します。注意：このメソッドは本家Hadoopには存在しません。
	 * 
	 * @param job
	 * @return Mapper単位で分割した入力データ。
	 */
	public abstract List<InputData<Key, Value>> readAll(Job<?, ?, ?, ?, ?, ?> job);
}
