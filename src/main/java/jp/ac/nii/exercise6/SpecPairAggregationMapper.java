package jp.ac.nii.exercise6;

import jp.ac.nii.mapreduceframework.Context;
import jp.ac.nii.mapreduceframework.Mapper;

/**
 * 以下の式の分子（numerator）を計算するジョブのMapperです。
 *  関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 */
public class SpecPairAggregationMapper extends Mapper<Long, String, String, Integer> {
	@Override
	public void map(Long key, String value, Context context) {
		// TODO: キーを「商品X,商品Y」という文字列、バリューを1にしてペアの頻度を計算するMapperを作ろう
	}
}
