package jp.ac.nii.exercise6;

import java.nio.file.Paths;

import jp.ac.nii.mapreduceframework.FileInputFormat;
import jp.ac.nii.mapreduceframework.FileOutputFormat;
import jp.ac.nii.mapreduceframework.Job;
import jp.ac.nii.mapreduceframework.NullWritable;

/**
 * 以下の式の分母（denominator）を計算するジョブのJobです。
 *  関連度 = 商品Xと商品Yのペアの総数 / 商品Xを含むペアの総数
 * このファイルは完成しています。
 */
public class AllPairAggregationJob {

	public static Job<Long,String,String,Integer,NullWritable,String> create() {
		Job<Long, String, String, Integer, NullWritable, String> job = Job.getInstance();

		job.setMapperClass(AllPairAggregationMapper.class);
		job.setReducerClass(AllPairAggregationReducer.class);

		job.setInputFormatClass(FileInputFormat.class);
		job.setOutputFormatClass(FileOutputFormat.class);

		FileInputFormat.addInputPath(job, Paths.get(FileNameConstants.GOODS_PAIR));
		FileOutputFormat.setOutputPath(job, Paths.get(FileNameConstants.DENOMINATION));

		job.setNumReduceTasks(10);
		
		return job;
	}
}
