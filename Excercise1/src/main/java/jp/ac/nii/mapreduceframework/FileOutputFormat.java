package jp.ac.nii.mapreduceframework;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * CSV形式でキーバリューをファイルに出力するクラスです。
 */
public class FileOutputFormat extends OutputFormat {
	private static Map<Job<?, ?, ?, ?, ?, ?>, Path> outputPath = Maps.newHashMap();

	private PrintStream out;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jp.ac.nii.mapreduceframework.OutputFormat#setup(jp.ac.nii.
	 * mapreduceframework.Job, int)
	 */
	@Override
	protected void setup(Job<?, ?, ?, ?, ?, ?> job, int reduceTaskIndex) {
		Path path = outputPath.get(job);
		path = path.resolve("output").resolve("part-" + String.format("%1$05d", reduceTaskIndex));
		path.toFile().getParentFile().mkdirs();
		try {
			out = new PrintStream(new FileOutputStream(path.toFile()), false, "UTF-8");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jp.ac.nii.mapreduceframework.OutputFormat#write(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void write(Object key, Object value) {
		out.println(key + "," + value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jp.ac.nii.mapreduceframework.OutputFormat#cleanup()
	 */
	@Override
	protected void cleanup() {
		out.close();
	}

	/**
	 * 指定したジョブの実行時に書き込むファイルのパスを指定します。
	 * 
	 * @param job
	 *            ファイルを書き込む際のジョブ
	 * @param inputFilePath
	 *            書き込むファイルのパス
	 */
	public static void setOutputPath(Job<?, ?, ?, ?, ?, ?> job, Path path) {
		outputPath.put(job, path);
	}
}
