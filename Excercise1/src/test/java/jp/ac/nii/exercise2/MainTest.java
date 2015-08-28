package jp.ac.nii.exercise2;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import org.junit.Test;

import jp.ac.nii.mapreduceframework.util.Util;
import jp.ac.nii.subexercise2.Main;

public class MainTest {

	@Test
	public void containsLengths()
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		Main.main(null);
		String text = Util.readAll(Paths.get("exercise2.csv").toFile());
		assertThat(text, containsString("16,1"));
		assertThat(text, containsString("9,269"));
		assertThat(text, containsString("3,2742"));
	}

}
