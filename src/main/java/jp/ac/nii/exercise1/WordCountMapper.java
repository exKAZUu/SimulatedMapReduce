package jp.ac.nii.exercise1;

import jp.ac.nii.mapreduceframework.Context;
import jp.ac.nii.mapreduceframework.Mapper;

/**
 * このファイルは完成しています。
 */
public class WordCountMapper extends Mapper<Long, String, String, Integer> {

	@Override
	protected void map(Long key, String line, Context context) {
		String[] words = line.split(" ");
		for (String word : words) {
			if (isWord(word)) {
				context.write(word.toLowerCase(), 1);
			}
		}
	}

	private boolean isWord(String word) {
		for (int i = 0; i < word.length(); i++) {
			if (!Character.isAlphabetic(word.charAt(i))) {
				return false;
			}
		}
		return word.length() > 0;
	}

}
