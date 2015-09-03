package jp.ac.nii.exercise5;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Test;

import jp.ac.nii.mapreduceframework.util.Util;

public class Excercise5Test {

	@Test
	public void containsResults() throws FileNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedEncodingException, URISyntaxException {
		Excercise5Main.main(null);
		String text = Util.readAll(Paths.get("exercise5.tsv").toFile());
		assertThat(text, containsString("体育	14.85"));
		assertThat(text, containsString("生活	15.08"));
		assertThat(text, containsString("社会	14.71"));
	}

}
