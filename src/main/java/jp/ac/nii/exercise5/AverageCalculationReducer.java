package jp.ac.nii.exercise5;

import jp.ac.nii.mapreduceframework.Context;
import jp.ac.nii.mapreduceframework.Reducer;

/**
 * TODO: このファイルは未完成です！
 */
public class AverageCalculationReducer extends Reducer<String, Integer, String, Double> {
	@Override
	protected void reduce(String key, Iterable<Integer> values, Context context) {
		// TODO: 平均値を計算しよう（Excercise4と同じ）
	}
}
