package com.supermap.data;

public class StatisticsResult extends InternalHandle {
	StatisticsResult(long handle){
		this.setHandle(handle);
	}

	public boolean isDirty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getDirty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return StatisticsResultNative.jni_GetIsDirty(this.getHandle());
	}

	public double getMaxValue() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMaxValue()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return StatisticsResultNative.jni_getMaxValue(this.getHandle());
	}

	public double getMinValue() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMinValue()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return StatisticsResultNative.jni_getMinValue(this.getHandle());
	}

	public double getAverage() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getAverage()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return StatisticsResultNative.jni_getAverage(this.getHandle());
	}

	public double getVariance() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getVariance()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return StatisticsResultNative.jni_getVariance(this.getHandle());
	}

	public double getStdDeviation() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getStdDeviation()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return StatisticsResultNative.jni_getStdDeviation(this.getHandle());
	}

	public double getMedianValue() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"getMedianValue()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return StatisticsResultNative.jni_getMedianValue(this.getHandle());
	}

	public double[] getMinority() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMinority()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return StatisticsResultNative.jni_getMinority(this.getHandle());
	}

	public double[] getMajority() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getMajority()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return  StatisticsResultNative.jni_getMajority(this.getHandle());
	}

	protected void finalize() {
		if(this.getHandle()!=0){
			StatisticsResultNative.jni_Delete(this.getHandle());
			this.setHandle(0);
		}
    }
}
