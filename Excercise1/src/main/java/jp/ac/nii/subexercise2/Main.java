package jp.ac.nii.subexercise2;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import jp.ac.nii.mapreduceframework.util.Util;

public class Main {
	public static void main(String[] args)
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		// 課題：jp.ac.nii.exercise2.MainTest のテストが通るようにこのファイルを作成してください。

		// exercise2/outputディレクトリの中身をexercise1.csvにマージします。
		Util.merge(Paths.get("exercise2", "output").toFile(), Paths.get("exercise2.csv").toFile());
	}
}