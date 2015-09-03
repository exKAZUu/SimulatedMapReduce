package jp.ac.nii.exercise6;

import java.nio.file.Paths;
import java.util.Comparator;

import jp.ac.nii.mapreduceframework.FileInputFormat;
import jp.ac.nii.mapreduceframework.FileOutputFormat;
import jp.ac.nii.mapreduceframework.HashPartitioner;
import jp.ac.nii.mapreduceframework.Job;
import jp.ac.nii.mapreduceframework.NullWritable;

/**
 * 以下の式の関連度を計算するジョブのJobです。
 *  関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 *  TODO: このファイルは未完成です！
 */
public class RelativityCalculationJob {

	/**
	 * このメソッドは完成しています。
	 */
	public static Job<Long, String, String, String, NullWritable, String> create() {
		Job<Long, String, String, String, NullWritable, String> job = Job.getInstance();

		job.setMapperClass(RelativityCaclulationMapper.class);
		job.setReducerClass(RelativityCalculationReducer.class);

		job.setInputFormatClass(FileInputFormat.class);
		job.setOutputFormatClass(FileOutputFormat.class);
		FileInputFormat.addInputPath(job, Paths.get(FileNameConstants.DENOMINATION));
		FileInputFormat.addInputPath(job, Paths.get(FileNameConstants.NUMERATOR));
		FileOutputFormat.setOutputPath(job, Paths.get(FileNameConstants.RELATED_GOODS));

		// TODO: ずっと下にある RelativityCalculationSortComparator, RelativityCalculationPartitioner, 
		// RelativityCalculationGroupComparator クラスを修正しよう！
		
		// ヒント: RelativityCaclulationMapperクラスをよく読もう
		
		// キーの並び順をどうするか（Reduceタスクの割り当て前のキーのソート処理の制御）
		job.setSortComparatorClass(RelativityCalculationSortComparator.class);
		// どのReduceタスクでキー（と対応するバリュー）を処理するか（Reduceタスクの割り当て制御）
		job.setPartitionerClass(RelativityCalculationPartitioner.class);
		// どのキーとどのキーを同一とみなして、Reducerのバリューリストに集約するか（Reduceの処理単位の制御）
		job.setGroupingComparatorClass(RelativityCalculationGroupComparator.class);

		job.setNumReduceTasks(10);
		
		return job;
	}

	public static String removeSharpD(String key) {
		String keyStr = key.toString();
		if (keyStr.endsWith("#d")) {
			return keyStr.substring(0, keyStr.length() - 2);
		}
		return key;
	}

	/**
	 * 以下のように分母データと分子データが入り乱れているので、
	 * <あんドーナツ, ところてん,1200(注：分子データ)>, <あんドーナツ#d, 5400(注：分母データ)>, <あんドーナツ, 生シュークリーム,2000(注：分子データ)>
	 * 「あんドーナツ」と「あんドーナツ#d」が同じキーと見なして、同じReduceタスクで処理されるようにハッシュ計算処理を制御する。
	 */
	public static class RelativityCalculationPartitioner extends HashPartitioner<String, String> {
		@Override
		public int getPartition(String key, String value, int numReduceTasks) {
			// TODO: removeSharp()とsuper.getPartition()メソッドを活用しよう
			return 0;	// 注意: return 0; は誤りです
		}
	}

	/**
	 * 以下のように分母データと分子データが入り乱れているので、
	 * <あんドーナツ, ところてん,1200(注：分子データ)>, <あんドーナツ#d, 5400(注：分母データ)>, <あんドーナツ, 生シュークリーム,2000(注：分子データ)>
	 * 「あんドーナツ」と「あんドーナツ#d」を同じキーと見なして、バリューリストにまとめられるように比較処理を制御する。
	 */
	public static class RelativityCalculationGroupComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			// TODO: removeSharp()とString.compareTo()メソッドを活用しよう
			return 0;	// 注意: return 0; は誤りです
		}
	}

	/**
	 * 以下のように分母データと分子データが入り乱れているので、
	 * <あんドーナツ, ところてん,1200(注：分子データ)>, <あんドーナツ#d, 5400(注：分母データ)>, <あんドーナツ, 生シュークリーム,2000(注：分子データ)>
	 * キーに対するソート時の比較処理を制御することで、以下のように分母データが先頭に来るようにする。
	 * <あんドーナツ#d, 5400(注：分母データ)>, <あんドーナツ, ところてん,1200(注：分子データ)>, <あんドーナツ, 生シュークリーム,2000(注：分子データ)>
	 */
	public static class RelativityCalculationSortComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			// TODO: String.compareTo()メソッドを活用しよう
			return 0;	// 注意: return 0; は誤りです
		}
	}
}
