package jp.ac.nii.exercise2;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import org.junit.Test;

import jp.ac.nii.exercise2.Excercise2Main;
import jp.ac.nii.mapreduceframework.util.Util;

public class Excercise2Test {

	@Test
	public void containsLengths()
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		Excercise2Main.main(null);
		String text = Util.readAll(Paths.get("exercise2.tsv").toFile());
		assertThat(text, containsString("16	1"));
		assertThat(text, containsString("9	269"));
		assertThat(text, containsString("3	2742"));
	}

}
