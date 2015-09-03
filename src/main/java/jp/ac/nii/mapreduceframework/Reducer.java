package jp.ac.nii.mapreduceframework;

public abstract class Reducer<InterKey, InterValue, OutKey, OutValue> {
	protected void setup(Context context) {
	}

	protected abstract void reduce(InterKey key, Iterable<InterValue> values, Context context);
}