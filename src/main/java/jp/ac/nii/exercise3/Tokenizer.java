package jp.ac.nii.exercise3;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.ja.JapaneseBaseFormFilter;
import org.apache.lucene.analysis.ja.JapaneseKatakanaStemFilter;
import org.apache.lucene.analysis.ja.JapanesePartOfSpeechStopFilter;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 日本語の文章から単語を抽出するためのクラスです。
 * このファイルは完成しています。
 */
public class Tokenizer {
	public static List<String> tokenize(String japaneseText) {
		JapaneseTokenizer tokenizer = new JapaneseTokenizer(null, true,
				JapaneseTokenizer.Mode.NORMAL);
		TokenStream stream = tokenizer;

		// 参考サイト
		// http://www.mwsoft.jp/programming/hadoop/mapreduce_with_lucene_filter.html
		
		// 小文字に統一
		stream = new LowerCaseFilter(stream);
		// 「こと」「これ」「できる」などの頻出単語を除外
		stream = new StopFilter(stream, JapaneseAnalyzer.getDefaultStopSet());
		// 16文字以上の単語は除外(あまり長い文字列はいらないよね)
		stream = new LengthFilter(stream, 1, 16);
		// 動詞の活用を揃える(疲れた => 疲れる)
		stream = new JapaneseBaseFormFilter(stream);
		// 助詞、助動詞、接続詞などを除外する
		stream = new JapanesePartOfSpeechStopFilter(stream,
				JapaneseAnalyzer.getDefaultStopTags());
		// カタカナ長音の表記揺れを吸収
		stream = new JapaneseKatakanaStemFilter(stream);

		ArrayList<String> result = new ArrayList<String>();

		try {
			tokenizer.setReader(new StringReader(japaneseText));
			stream.reset();
			while (stream.incrementToken()) {
				CharTermAttribute term = stream
						.getAttribute(CharTermAttribute.class);
				result.add(term.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				tokenizer.close();
			} catch (IOException e) {
			}
		}

		return result;
	}

}
