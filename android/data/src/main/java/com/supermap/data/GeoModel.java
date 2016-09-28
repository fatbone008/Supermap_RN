package com.supermap.data;

import java.io.File;

public class GeoModel extends Geometry3D {

	public GeoModel() {
		this.setHandle(GeoModelNative.jni_New(), true);
	}

	public GeoModel(GeoModel geoModel) {
		if (geoModel == null) {
			String message = InternalResource.loadString("geoModel",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long geoModelHandle = InternalHandle.getHandle(geoModel);
		if (geoModelHandle == 0) {
			String message = InternalResource.loadString("geoModel",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoModelNative.jni_Clone(geoModelHandle);
		super.setHandle(handle, true);
	}

	GeoModel(long handle) {
		this.setHandle(handle, false);
	}

	// protected static GeoModel createGeoModel(long handle) {
	// if (handle == 0) {
	// String message = InternalResource.loadString("handle",
	// InternalResource.GlobalInvalidConstructorArgument,
	// InternalResource.BundleName);
	// throw new IllegalArgumentException(message);
	// }
	// return new GeoModel(handle);
	// }

	 String getName() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getName()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoModelNative.jni_GetName(this.getHandle());
	}

	 void setName(String name) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"setName(String name)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (name == null) {
			String message = InternalResource.loadString("name",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoModelNative.jni_SetName(this.getHandle(), name);
	}

	 boolean fromFile(String file) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"fromFile(String file)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (file == null) {
			String message = InternalResource.loadString("file",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		File toolFile = new File(file);
		if (!toolFile.exists()) {
			String message = InternalResource.loadString("file",
					InternalResource.GeoModelTheFileIsNotExist,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeoModelNative.jni_FromFile(this.getHandle(), file);
	}

	 boolean toStreamFile(String strStreamFile){
		return GeoModelNative.jni_ToStreamFile(this.getHandle(),strStreamFile);
	}
	public boolean fromFile(String file, Point3D position) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"fromFile(String file, Point3D position)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (file == null) {
			String message = InternalResource.loadString("file",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		boolean result = this.fromFile(file);
		if (result) {
			this.setPosition(position);
		}
		return result;
	}

	 boolean toSGM(String fileSGM) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"toSGM(String fileSGM)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (fileSGM == null) {
			String message = InternalResource.loadString("fileSGM",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeoModelNative.jni_ToSGM(this.getHandle(), fileSGM);
	}
	
	 boolean toSGZ(String fileSGZ) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"toSGZ(String fileSGZ)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (fileSGZ == null) {
			String message = InternalResource.loadString("fileSGM",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		return GeoModelNative.jni_ToSGZ(this.getHandle(), fileSGZ);
	}

	@Override
	public GeoModel clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoModel(this);
	}

	@Override
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoModelNative.jni_Delete(getHandle());
			clearHandle();
		}

	}

	protected static GeoModel creatInstance(long handle) {
		if (handle == 0) {
			String message = InternalResource.loadString("handle",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return new GeoModel(handle);
	}

	
	protected void clearHandle() {
		setHandle(0);
	}
}
