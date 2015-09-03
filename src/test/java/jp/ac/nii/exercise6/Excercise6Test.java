package jp.ac.nii.exercise6;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.Test;

import jp.ac.nii.mapreduceframework.util.Util;

public class Excercise6Test {

	@Test
	public void containsResults() throws FileNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedEncodingException, URISyntaxException {
		Excercise6Main.main(null);
		String text = Util.readAll(Paths.get("exercise6.csv").toFile());
		assertThat(text, containsString("生シュークリーム,あんドーナツ,0.0287"));
		assertThat(text, containsString("あんドーナツ,生シュークリーム,0.0294"));
		assertThat(text, containsString("ところてん,ミルクチョコレート,0.0367"));
	}

}
