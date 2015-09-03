package jp.ac.nii.mapreduceframework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import com.google.common.io.Files;

public class Util {
	/**
	 * 指定したディレクトリ内のファイルを、指定したファイルにマージする。
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void merge(File inputFile, File outputFile)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintStream out = new PrintStream(new FileOutputStream(outputFile), false, "UTF-8");
		for (File file : Files.fileTreeTraverser().preOrderTraversal(inputFile)) {
			if (file.isDirectory()) {
				continue;
			}

			FileInputStream intput = new FileInputStream(file);
			Scanner scanner = new Scanner(intput, "UTF-8");
			while (scanner.hasNextLine()) {
				out.println(scanner.nextLine());
			}
			scanner.close();
		}
		out.close();
	}

	public static String readAll(File file) throws FileNotFoundException {
		StringBuilder builder = new StringBuilder();
		FileInputStream intput = new FileInputStream(file);
		Scanner scanner = new Scanner(intput, "UTF-8");
		while (scanner.hasNextLine()) {
			builder.append(scanner.nextLine());
			builder.append("\n");
		}
		scanner.close();
		return builder.toString();
	}
}
