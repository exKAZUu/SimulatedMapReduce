package jp.ac.nii.mapreduceframework;

public abstract class Mapper<InKey, InValue, InterKey, InterValue> {
	protected abstract void map(InKey key, InValue value, Context context);
}