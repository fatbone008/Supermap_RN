package com.supermap.data;

public class GeoPlacemark extends Geometry3D {
	private Geometry m_geometry;
	private TextStyle m_textStyle = null;

	public GeoPlacemark() {
		long handle = GeoPlacemarkNative.jni_New();
		this.setHandle(handle, true);
	}

	public GeoPlacemark(GeoPlacemark geoPlacemark) {
		if (geoPlacemark == null) {
			String message = InternalResource.loadString("geoPlacemark",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long geoPlacemarkHandle = InternalHandle.getHandle(geoPlacemark);
		if (geoPlacemarkHandle == 0) {
			String message = InternalResource.loadString("geoPlacemark",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoPlacemarkNative.jni_Clone(geoPlacemarkHandle);
		this.setHandle(handle, true);
		//InternalHandleDisposable.makeSureNativeObjectLive(geoPlacemark);
	}

	GeoPlacemark(long handle) {
		this.setHandle(handle, false);
	}

	public GeoPlacemark(String name, Geometry geometry) {
		if (geometry == null) {
			String message = InternalResource.loadString("value",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long valueHandle = InternalHandle.getHandle(geometry);
		if (valueHandle == 0) {
			String message = InternalResource.loadString("value",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long handle = GeoPlacemarkNative.jni_New1(name, valueHandle);
//		long handle = GeoPlacemarkNative.jni_New();
		this.setHandle(handle, true);
		this.getNameStyle().setForeColor(new Color(255,255,255));
		//InternalHandleDisposable.makeSureNativeObjectLive(geometry);
//		this.setName(name);
//		this.setGeometry(geometry);
	}

	public String getName() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetName()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return GeoPlacemarkNative.jni_getName(this.getHandle());
	}

	public void setName(String value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetName(String value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if(value==null){
			String message = InternalResource.loadString(
					"value",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		GeoPlacemarkNative.jni_setName(this.getHandle(), value);
	}

	public Geometry getGeometry() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetGeometry()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = GeoPlacemarkNative.jni_getGeometry(this.getHandle());
		if (this.m_geometry == null) {
			if (handle != 0) {
				m_geometry = Geometry.createInstance(handle);
				m_geometry.setIsDisposable(false);
			}
		}
		else{
			if(handle!=0){
				m_geometry.clearHandle();
				this.m_geometry.setHandle(handle, false);
			}
		}
		return m_geometry;
	}

	public void setGeometry(Geometry value) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetGeometry(Geometry value)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if(value instanceof GeoPlacemark){
			String message = InternalResource.loadString("value",
					InternalResource.GeoPlacemarkSetGeometryShouldNotBeGeoPlacemark,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (value == null) {
			String message = InternalResource.loadString("value",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long valueHandle = InternalHandle.getHandle(value);
		if (valueHandle == 0) {
			String message = InternalResource.loadString("value",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}

		if(this.m_geometry==null){
			m_geometry = (Geometry) value.clone();
			
			InternalHandleDisposable.setIsDisposable(m_geometry, false);
			GeoPlacemarkNative.jni_setGeometry(this.getHandle(), m_geometry
					.getHandle());
		}
		//同一种类型
		else if(value.getClass().equals(this.m_geometry.getClass())){
			m_geometry.clearHandle();
			Geometry geo  = (Geometry) value.clone();
			m_geometry.setHandle(geo.getHandle(),false); 
			GeoPlacemarkNative.jni_setGeometry(this.getHandle(), m_geometry
					.getHandle());
		}
		else{
			m_geometry.clearHandle();
			Geometry geo  = (Geometry) value.clone();
//			m_geometry.setHandle(geo.getHandle(),false); 
			m_geometry = geo;
			InternalHandleDisposable.setIsDisposable(geo, false);
			GeoPlacemarkNative.jni_setGeometry(this.getHandle(), m_geometry
					.getHandle());
		}
		
		long handle = GeoPlacemarkNative.jni_getNameStyle(this.getHandle());
		m_textStyle = getNameStyle();
		if(handle != 0 && handle != m_textStyle.getHandle()){
			m_textStyle.refreshHandle(m_textStyle, handle);
		}
		//InternalHandleDisposable.makeSureNativeObjectLive(value);
//		System.out.println(m_textStyle.getHandle());
	}

	public TextStyle  getNameStyle(){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("GetNameStyle()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if(m_textStyle==null){
			long handle = GeoPlacemarkNative.jni_getNameStyle(this.getHandle());
			if(handle!=0){
				m_textStyle = TextStyle.createInstance(handle);
			}
			else{
				TextStyle textStyle = new TextStyle();
				GeoPlacemarkNative.jni_setNameStyle(this.getHandle(),textStyle.getHandle());
				long handle1 = GeoPlacemarkNative.jni_getNameStyle(this.getHandle());
				m_textStyle = TextStyle.createInstance(handle1);
			}
		}
		return m_textStyle;
	}
	public void setNameStyle(TextStyle textStyle){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"SetNameStyle(TextStyle textStyle)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (textStyle == null) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long textStyleHandle = InternalHandle.getHandle(textStyle);
		if (textStyleHandle == 0) {
			String message = InternalResource.loadString("textStyle",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		GeoPlacemarkNative.jni_setNameStyle(this.getHandle(),textStyleHandle);
		//InternalHandleDisposable.makeSureNativeObjectLive(textStyle);
	}
	@Override
	public GeoPlacemark clone() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("Clone()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return new GeoPlacemark(this);
	}

	@Override
	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		} else if (this.getHandle() != 0) {

			GeoPlacemarkNative.jni_Delete(getHandle());
			setHandle(0);
			clearHandle();
		}
	}

	protected void clearHandle() {
		super.clearHandle();
		if (this.m_geometry != null) {
			m_geometry.clearHandle();
			m_geometry = null;
		}
	}
}
