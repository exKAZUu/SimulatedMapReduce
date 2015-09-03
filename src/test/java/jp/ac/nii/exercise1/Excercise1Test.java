package jp.ac.nii.exercise1;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import org.junit.Test;

import jp.ac.nii.exercise1.Excercise1Main;
import jp.ac.nii.mapreduceframework.util.Util;

public class Excercise1Test {

	@Test
	public void containsWords()
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		Excercise1Main.main(null);
		String text = Util.readAll(Paths.get("exercise1.tsv").toFile());
		assertThat(text, containsString("the	793"));
		assertThat(text, containsString("alice	103"));
		assertThat(text, containsString("apply	1"));
	}

}
