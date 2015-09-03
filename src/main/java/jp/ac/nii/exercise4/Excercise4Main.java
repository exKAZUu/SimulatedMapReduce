package jp.ac.nii.exercise4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Scanner;

import jp.ac.nii.mapreduceframework.Configuration;
import jp.ac.nii.mapreduceframework.FileInputFormat;
import jp.ac.nii.mapreduceframework.FileOutputFormat;
import jp.ac.nii.mapreduceframework.Job;
import jp.ac.nii.mapreduceframework.util.Util;

/**
 * このファイルは完成しています。
 */
public class Excercise4Main {
	public static void main(String[] args)
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {

		// TODO: 課題4： 科目名と点数のペアが記載されている score.csv を対象として、各科目の標準偏差を計算してください。
		// また、jp.ac.nii.exercise4.Exercise4Test のテストが通ることを確認して下さい。

		// 参考資料: Googleで「Hadoop 標準偏差」などのキーワードで検索すると良いでしょう。
		// 注意: このファイルは完成しています。

		Job<Long, String, String, Integer, String, Double> averageJob = createAverageJob();
		averageJob.waitForCompletion();

		// exercise4_average/outputディレクトリの中身をexercise4_average.tsvにマージします。
		Util.merge(Paths.get("exercise4_average", "output").toFile(), Paths.get("exercise4_average.tsv").toFile());

		Job<Long, String, String, Integer, String, Double> sdJob = createStandardDeviationJob();
		sdJob.waitForCompletion();

		// exercise4/outputディレクトリの中身をexercise4.tsvにマージします。
		Util.merge(Paths.get("exercise4", "output").toFile(), Paths.get("exercise4.tsv").toFile());
	}

	private static Job<Long, String, String, Integer, String, Double> createAverageJob() {
		Job<Long, String, String, Integer, String, Double> averageJob = Job.getInstance();

		averageJob.setInputFormatClass(FileInputFormat.class);
		FileInputFormat.addInputPath(averageJob, Paths.get("score.csv"));
		averageJob.setOutputFormatClass(FileOutputFormat.class);
		FileOutputFormat.setOutputPath(averageJob, Paths.get("exercise4_average"));

		averageJob.setMapperClass(AverageCalculationMapper.class);
		averageJob.setReducerClass(AverageCalculationReducer.class);

		averageJob.setNumReduceTasks(10);
		return averageJob;
	}

	private static Job<Long, String, String, Integer, String, Double> createStandardDeviationJob()
			throws FileNotFoundException {
		Configuration conf = createConfiguration();
		Job<Long, String, String, Integer, String, Double> sdJob = Job.getInstance(conf);

		sdJob.setInputFormatClass(FileInputFormat.class);
		FileInputFormat.addInputPath(sdJob, Paths.get("score.csv"));
		sdJob.setOutputFormatClass(FileOutputFormat.class);
		FileOutputFormat.setOutputPath(sdJob, Paths.get("exercise4"));

		sdJob.setMapperClass(StandardDeviationCalculationMapper.class);
		sdJob.setReducerClass(StandardDeviationCalculationReducer.class);

		sdJob.setNumReduceTasks(10);
		return sdJob;
	}

	/**
	 * 平均値の計算結果を読み取って、計算結果を設定したConfigurationオブジェクトを返します。
	 * 
	 * @return 計算結果を設定したConfigurationオブジェクト
	 * @throws FileNotFoundException
	 */
	private static Configuration createConfiguration() throws FileNotFoundException {
		Configuration conf = new Configuration();
		// 平均値の計算結果を読み取る
		FileInputStream intput = new FileInputStream("exercise4_average.tsv");
		Scanner scanner = new Scanner(intput, "UTF-8");
		while (scanner.hasNextLine()) {
			String[] keyValue = scanner.nextLine().split("\t");
			// 平均値データの設定
			conf.set(keyValue[0], keyValue[1]);
		}
		scanner.close();
		return conf;
	}
}