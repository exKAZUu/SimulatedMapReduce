package jp.ac.nii.exercise6;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import jp.ac.nii.mapreduceframework.Job;
import jp.ac.nii.mapreduceframework.NullWritable;
import jp.ac.nii.mapreduceframework.util.Util;

/**
 *　このファイルは完成しています。
 */
public class Excercise6Main {
	public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedEncodingException, URISyntaxException {

		// TODO: 課題6： 商品名一覧（goods_list.csv）と同時に購入された商品ペアデータ（goods_pair.csv）を対象として、
		// 商品Xに対する商品Yの関連度を計算してください。
		// 関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
		// また、jp.ac.nii.exercise6.Exercise6Test のテストが通ることを確認して下さい。
		
		// 参考資料1: 05_MapReduceによるレコメンデーションエンジンの実装.pdf
		// 参考資料2: 05_[付属資料]レコメンデーションエンジンシステム仕様.pdf
		// 参考資料3: 05_[付属資料]レコメンデーションエンジンデータ仕様.pdf

		Job<Long, String, String, Integer, NullWritable, String> denominatorJob = AllPairAggregationJob.create();
		denominatorJob.waitForCompletion();

		Job<Long, String, String, Integer, NullWritable, String> numeratorJob = SpecPairAggregationJob.create();
		numeratorJob.waitForCompletion();

		Job<Long, String, String, String, NullWritable, String> relativityJob = RelativityCalculationJob.create();
		relativityJob.waitForCompletion();

		// exercise6_average/outputディレクトリの中身をexercise6_average.csvにマージします。
		Util.merge(Paths.get(FileNameConstants.RELATED_GOODS, "output").toFile(), Paths.get("exercise6.csv").toFile());
	}
}