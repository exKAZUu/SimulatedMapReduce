package jp.ac.nii.mapreduceframework;

/**
 * Reducerがデータを出力するためのクラスです。 注意：このクラスが持つメソッドのシグネチャは本家Hadoopと異なります。
 */
public abstract class OutputFormat {
	/**
	 * 指定されたキーとバリューを出力します。 注意：本家Hadoopには存在しません。
	 * 
	 * @param key
	 *            キー
	 * @param value
	 *            バリュー
	 */
	public abstract void write(Object key, Object value);

	/**
	 * 開始時にリソース確保などの処理を行います。 注意：本家Hadoopには存在しません。
	 * 
	 * @param job
	 *            実行中のジョブ
	 * @param reduceTaskIndex
	 *            このOutputFormatを利用するReducerの番号
	 */
	protected abstract void setup(Job<?, ?, ?, ?, ?, ?> job, int reduceTaskIndex);

	/**
	 * 終了時にリソース解放などの処理を行います。 注意：本家Hadoopには存在しません。
	 */
	protected abstract void cleanup();
}
