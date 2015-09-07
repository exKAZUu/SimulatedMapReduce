package jp.ac.nii.exercise6;

import jp.ac.nii.mapreduceframework.Context;
import jp.ac.nii.mapreduceframework.NullWritable;
import jp.ac.nii.mapreduceframework.Reducer;

/**
 * 以下の式の分母（denominator）を計算するジョブのReducerです。
 *  関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * TODO: このファイルは未完成です！
 */
public class AllPairAggregationReducer extends Reducer<String, Integer, NullWritable, String> {

	private static final NullWritable nullWritable = NullWritable.get();

	@Override
	public void reduce(String key, Iterable<Integer> values, Context context) {
		int sum = 0;
		// TODO: ワードカウントと同じ要領でvaluesの合計を計算して、keyの商品の出現回数を計算しよう
		
		// キーにNullWritableを使うことで、「キー[タブ]バリュー」という出力の代わりに、「バリュー」という出力を実現する
		context.write(nullWritable, key + "," + sum);
	}
}
