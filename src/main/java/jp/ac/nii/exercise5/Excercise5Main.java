package jp.ac.nii.exercise5;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import jp.ac.nii.mapreduceframework.FileInputFormat;
import jp.ac.nii.mapreduceframework.FileOutputFormat;
import jp.ac.nii.mapreduceframework.Job;
import jp.ac.nii.mapreduceframework.util.Util;

/**
 *　このファイルは完成しています。
 */
public class Excercise5Main {
	public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedEncodingException, URISyntaxException {

		// TODO: 課題5： 科目名と点数のペアが記載されている score.csv を対象として、各科目の標準偏差を計算してください。
		// また、jp.ac.nii.exercise5.Exercise5Test のテストが通ることを確認して下さい。

		// 参考資料: Googleで「Hadoop 分散キャッシュ」などのキーワードで検索すると理解が深まります。（課題には不要だと思いますが。）

		Job<Long, String, String, Integer, String, Double> averageJob = createAverageJob();
		averageJob.waitForCompletion();

		// exercise5_average/outputディレクトリの中身をexercise5_average.tsvにマージします。
		Util.merge(Paths.get("exercise5_average", "output").toFile(), Paths.get("exercise5_average.tsv").toFile());

		Job<Long, String, String, Integer, String, Double> sdJob = createStandardDeviationJob();
		sdJob.waitForCompletion();

		// exercise5/outputディレクトリの中身をexercise5.tsvにマージします。
		Util.merge(Paths.get("exercise5", "output").toFile(), Paths.get("exercise5.tsv").toFile());
	}

	private static Job<Long, String, String, Integer, String, Double> createAverageJob() {
		Job<Long, String, String, Integer, String, Double> averageJob = Job.getInstance();

		averageJob.setInputFormatClass(FileInputFormat.class);
		FileInputFormat.addInputPath(averageJob, Paths.get("score.csv"));
		averageJob.setOutputFormatClass(FileOutputFormat.class);
		FileOutputFormat.setOutputPath(averageJob, Paths.get("exercise5_average"));
		
		averageJob.setMapperClass(AverageCalculationMapper.class);
		averageJob.setReducerClass(AverageCalculationReducer.class);
		
		averageJob.setNumReduceTasks(10);
		return averageJob;
	}

	private static Job<Long, String, String, Integer, String, Double> createStandardDeviationJob()
			throws URISyntaxException {
		Job<Long, String, String, Integer, String, Double> sdJob = Job.getInstance();
		// 全Mapper / Reducer からファイルを参照できるように設定します。
		sdJob.addCacheFile(new URI("exercise5_average.tsv"));

		sdJob.setInputFormatClass(FileInputFormat.class);
		FileInputFormat.addInputPath(sdJob, Paths.get("score.csv"));
		sdJob.setOutputFormatClass(FileOutputFormat.class);
		FileOutputFormat.setOutputPath(sdJob, Paths.get("exercise5"));
		
		sdJob.setMapperClass(StandardDeviationCalculationMapper.class);
		sdJob.setReducerClass(StandardDeviationCalculationReducer.class);
		
		sdJob.setNumReduceTasks(10);
		return sdJob;
	}
}