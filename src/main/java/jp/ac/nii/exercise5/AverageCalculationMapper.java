package jp.ac.nii.exercise5;

import jp.ac.nii.mapreduceframework.Context;
import jp.ac.nii.mapreduceframework.Mapper;

/**
 * TODO: このファイルは未完成です！
 */
public class AverageCalculationMapper extends Mapper<Long, String, String, Integer> {
	@Override
	protected void map(Long key, String line, Context context) {
		// TODO: 科目と点数のキーバリューに変換しよう（Excercise4と同じ）
	}
}
