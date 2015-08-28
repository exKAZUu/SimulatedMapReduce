package jp.ac.nii.mapreduceframework;

/**
 * MapperやReducerで計算した値を出力するためのクラスです。
 */
public abstract class Context {
	/**
	 * MapperやReducerで計算した値を出力します。
	 * @param key キー
	 * @param value バリュー
	 */
	public abstract void write(Object key, Object value);

	/**
	 * Mapperで処理しているキーバリューの入力元がファイルの場合にファイルパスを、それ以外はnullを返します。
	 * 注意：本家Hadoopでも同様の処理をContextを通じてできますが、細かい手順が異なります。
	 * 
	 * @return Mapperで処理しているキーバリューの入力元がファイルの場合にファイルパスを、それ以外はnull。
	 */
	public abstract String getPath();
}
