/**
 * 
 */
package com.supermap.data;

import com.supermap.imb.jsonlib.SiJsonObject;

/**
 * @author konglingliang
 * 
 */
public class GeoPoint3D extends Geometry3D {
	public GeoPoint3D() {
		long handle = GeoPoint3DNative.jni_New();
		this.setHandle(handle, true);
		reset();
	}

	public GeoPoint3D(double x, double y, double z) {
		long handle = GeoPoint3DNative.jni_New();
		this.setHandle(handle, true);
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public GeoPoint3D(GeoPoint3D geoPoint3D) {
		if (geoPoint3D.getHandle() == 0) {
			String message = InternalResource.loadString("geoPoint3D",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoPoint3DNative.jni_Clone(geoPoint3D.getHandle());
		this.setHandle(handle, true);
	}
	
	public GeoPoint3D(Point3D point) {
		this();
		this.setX(point.getX());
		this.setY(point.getY());
		this.setZ(point.getZ());
	}

	GeoPoint3D(long handle) {
		setHandle(handle, false);
	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {
			GeoPoint3DNative.jni_Delete(getHandle());
			this.setHandle(0);
			super.clearHandle();
		}
	}

	public double getX() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getX",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPoint3DNative.jni_GetX(this.getHandle());
	}

	public void setX(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setX(value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPoint3DNative.jni_SetX(getHandle(), value);
	}

	public double getY() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getY()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPoint3DNative.jni_GetY(getHandle());
	}

	public void setY(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setY(value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPoint3DNative.jni_SetY(getHandle(), value);

	}

	public double getZ() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getZ()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPoint3DNative.jni_GetZ(getHandle());
	}

	public void setZ(double value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setZ(value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPoint3DNative.jni_SetZ(getHandle(), value);
	}

	private void reset() {
		this.setX(Toolkit.DBL_MIN_VALUE);
		this.setY(Toolkit.DBL_MIN_VALUE);
		this.setZ(Toolkit.DBL_MIN_VALUE);
	}

	public GeoPoint3D clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoPoint3D(this);
	}

	 void setEmpty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("setEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPoint3DNative.jni_SetX(getHandle(), Toolkit.DBL_MIN_VALUE);
		GeoPoint3DNative.jni_SetY(getHandle(), Toolkit.DBL_MIN_VALUE);
		GeoPoint3DNative.jni_SetZ(getHandle(), Toolkit.DBL_MIN_VALUE);
	}

	/**
	 * 返回点是否为空
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getIsEmpty()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean isEmpty = Toolkit.isZero(this.getX() - Toolkit.DBL_MIN_VALUE,
				Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
				Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)
				&& Toolkit.isZero(this.getY() - Toolkit.DBL_MIN_VALUE,
						Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
						Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION)
				&& Toolkit.isZero(this.getZ() - Toolkit.DBL_MIN_VALUE,
						Environment.DEFAULT_MIN_EQUAL_ZERO_PRECISION,
						Environment.DEFAULT_MAX_EQUAL_ZERO_PRECISION);
		return isEmpty;
	}
	
	@Override
	public boolean fromJson(String json) {
		SiJsonObject obj = new SiJsonObject(json);
		boolean ret = fromJson(obj);
		obj.dispose();
		return ret;
	}
	
	@Override
	public boolean fromJson(SiJsonObject json) {
		if(super.fromJson(json)){
			double x = json.getJsonArray("points").getJsonObject(0).getDouble("x");
			double y = json.getJsonArray("points").getJsonObject(0).getDouble("y");
			double z = json.getJsonArray("points").getJsonObject(0).getDouble("z");
			this.setX(x);
			this.setY(y);
			this.setZ(z);
			return true;
		}
		
		return false;
	}

	@Override
	public String toJson() {
		StringBuilder sb = new StringBuilder(super.toJson());
		sb.deleteCharAt(sb.length()-1);
		sb.append(",");
		sb.append(" \"parts\" :" + "[1]" + ",");
		sb.append(" \"type\" :" + "\"POINT3D\"" + ",");
		sb.append(" \"points\" : " + "[" + new Point3D(this.getX(), this.getY(), this.getZ()).toJson()+ "]");
		sb.append("}");
		return sb.toString();
	}
}