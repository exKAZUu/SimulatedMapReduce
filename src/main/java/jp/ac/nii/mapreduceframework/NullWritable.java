package jp.ac.nii.mapreduceframework;

public class NullWritable implements Comparable<NullWritable> {

	private static NullWritable nullWritable = new NullWritable();

	private NullWritable() {
	}

	public static NullWritable get() {
		return nullWritable;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public int compareTo(NullWritable obj) {
		return 0;
	}
}
