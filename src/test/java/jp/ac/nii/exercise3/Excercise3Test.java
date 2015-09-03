package jp.ac.nii.exercise3;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import org.junit.Test;

import jp.ac.nii.mapreduceframework.util.Util;

public class Excercise3Test {

	@Test
	public void containsLengths()
			throws FileNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		Excercise3Main.main(null);
		String text = Util.readAll(Paths.get("exercise3.tsv").toFile());
		assertThat(text, containsString("あいさつ	11"));
		assertThat(text, containsString("義理	5"));
		assertThat(text, containsString("大いに	26"));
	}

}
