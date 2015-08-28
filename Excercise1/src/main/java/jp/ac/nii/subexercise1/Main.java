package jp.ac.nii.subexercise1;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import jp.ac.nii.mapreduceframework.FileInputFormat;
import jp.ac.nii.mapreduceframework.FileOutputFormat;
import jp.ac.nii.mapreduceframework.Job;
import jp.ac.nii.mapreduceframework.util.Util;

public class Main {
	public static void main(String[] args)
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		// 課題：このファイルを実行してexercise1/outputディレクトリと、exercise1.csvファイルが生成されることを確認してください。
		// また、jp.ac.nii.exercise1.MainTest のテストを実行して、テストが通ることを確認して下さい。
		Job<Integer, String, String, Integer, String, Integer> job = Job.getInstance();

		job.setInputFormatClass(FileInputFormat.class);
		FileInputFormat.addInputPath(job, Paths.get("alice.txt"));
		job.setOutputFormatClass(FileOutputFormat.class);
		FileOutputFormat.setOutputPath(job, Paths.get("exercise1"));
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		job.setNumReduceTasks(10);

		job.waitForCompletion();

		// exercise1/outputディレクトリの中身をexercise1.csvにマージします。
		Util.merge(Paths.get("exercise1", "output").toFile(), Paths.get("exercise1.csv").toFile());
	}
}