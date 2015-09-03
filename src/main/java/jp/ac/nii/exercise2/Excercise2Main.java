package jp.ac.nii.exercise2;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import jp.ac.nii.mapreduceframework.util.Util;

/**
 * TODO: このファイルは未完成です！
 */
public class Excercise2Main {
	public static void main(String[] args)
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {

		// TODO: 課題2： alice.txt を対象として、単語の長さの頻度を算出してください。
		// また、jp.ac.nii.exercise2.Exercise2Test のテストが通ることを確認して下さい。
		
		// 例： I am a cat. => 1文字が2回、2文字が1回、3文字が1回
		// 参考資料： 「01_Hadoopの概要.pdf」のp.30-35あたり

		// exercise2/outputディレクトリの中身をexercise2.tsvにマージします。
		Util.merge(Paths.get("exercise2", "output").toFile(), Paths.get("exercise2.tsv").toFile());
	}
}