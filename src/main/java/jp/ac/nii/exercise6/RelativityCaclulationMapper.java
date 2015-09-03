package jp.ac.nii.exercise6;

import jp.ac.nii.mapreduceframework.Context;
import jp.ac.nii.mapreduceframework.Mapper;

/**
 * 以下の式の関連度を計算するジョブのMapperです。 
 * 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * このファイルは完成しています。
 */
public class RelativityCaclulationMapper extends Mapper<Long, String, String, String> {

	private Writer writer;

	@Override
	public void setup(Context context) {
		String filePath = context.getPath();

		if (filePath.contains(FileNameConstants.DENOMINATION)) {
			writer = new DenominationWriter();
		} else if (filePath.contains(FileNameConstants.NUMERATOR)) {
			writer = new NumeratorWriter();
		} else {
			throw new RuntimeException("Invalid Input File : " + filePath);
		}
	}

	@Override
	public void map(Long key, String value, Context context) {
		writer.write(key, value, context);
	}

	private interface Writer {
		public void write(Long key, String valueI, Context context);
	}

	private class DenominationWriter implements Writer {
		@Override
		public void write(Long key, String value, Context context) {
			String[] goodsNameAndNum = value.split(",");

			// 分母と分子を区別するためにキーの末尾に"#d"を追加
			context.write(goodsNameAndNum[0] + "#d", goodsNameAndNum[1]);
		}
	}

	private class NumeratorWriter implements Writer {
		@Override
		public void write(Long key, String value, Context context) {
			String[] goodsPairAndNum = value.split(",");
			context.write(goodsPairAndNum[0], goodsPairAndNum[1] + "," + goodsPairAndNum[2]);
			context.write(goodsPairAndNum[1], goodsPairAndNum[0] + "," + goodsPairAndNum[2]);
		}
	}
}
