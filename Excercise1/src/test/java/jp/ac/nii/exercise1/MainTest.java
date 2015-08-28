package jp.ac.nii.exercise1;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import org.junit.Test;

import jp.ac.nii.mapreduceframework.util.Util;
import jp.ac.nii.subexercise1.Main;

public class MainTest {

	@Test
	public void containsWords()
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		Main.main(null);
		String text = Util.readAll(Paths.get("exercise1.csv").toFile());
		assertThat(text, containsString("the,793"));
		assertThat(text, containsString("alice,103"));
		assertThat(text, containsString("apply,1"));
	}

}
