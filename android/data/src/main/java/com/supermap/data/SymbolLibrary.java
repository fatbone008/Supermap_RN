package com.supermap.data;

public abstract class SymbolLibrary extends InternalHandleDisposable {

	private SymbolGroup m_SymbolGroup;

	protected SymbolLibrary(){
		
	}
	// 查找指定ID所属的分组
	public SymbolGroup findGroup(int id) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("findGroup(int id)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		SymbolGroup symbolGroup = null;
		if (this.getRootGroup() != null) {
			symbolGroup = m_SymbolGroup.findGroup(id);
		}
		return symbolGroup;
	}

	public boolean contains(int id) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("contains(int id)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return SymbolLibraryNative.jni_Contains(this.getHandle(), id);
	}

	public Symbol findSymbol(int id) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("findSymbol(int id)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		Symbol symbol = null;
		if (this.getRootGroup() != null) {
			symbol = m_SymbolGroup.findSymbol(id);
		}
		return symbol;

	}

	public Symbol findSymbol(String name) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"findSymbol(String name)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (name == null) {
			name = "";
		}
		Symbol symbol = null;
		if (this.getRootGroup() != null) {
			symbol = m_SymbolGroup.findSymbol(name);
		}
		return symbol;
	}


	public boolean fromFile(String fileName) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"fromFile(String filename, boolean overWrite)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean result = false;
		if (fileName != null && fileName.trim().length() != 0) {
			result = SymbolLibraryNative.jni_fromFile(this.getHandle(),
					fileName,false);
		}
		if (result && this.getRootGroup() != null) {
			m_SymbolGroup.reset();
		}
		return result;
	}

	public boolean remove(int id) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("remove(int id)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean result =false;
		if(this.getRootGroup() != null) {
			result =m_SymbolGroup.remove(id);
		}
//		boolean result = SymbolLibraryNative.jni_remove(this.getHandle(), id);
//		if (result && this.getRootGroup() != null) {
//			m_SymbolGroup.remove(id);
//		}
		return result;
	}

	public int add(Symbol symbol,SymbolGroup desGroup){
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"add(Symbol symbol, SymbolGroup desGroup)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (symbol == null) {
			String message = InternalResource.loadString("symbol",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long symbolHandle = InternalHandle.getHandle(symbol);
		if (symbolHandle == 0) {
			String message = InternalResource.loadString("symbol",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (desGroup == null) {
			String message = InternalResource.loadString("desGroup",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long desGroupHandle = InternalHandle.getHandle(desGroup);
		if (desGroupHandle == 0) {
			String message = InternalResource.loadString("desGroup",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if(!m_SymbolGroup.getChildGroups().contains(desGroup.getName())){
			String message = InternalResource.loadString("desGroup",
					InternalResource.SymbolGroupsNotContainTheSymbolGroup,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		int index = this.add(symbol);
		if(index!=-1){
			moveTo(index, desGroup);
		}
		return index;
	}
	
	public int add(Symbol symbol) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("Add(Symbol symbol)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (symbol == null) {
			String message = InternalResource.loadString("symbol",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		long symbolHandle = InternalHandle.getHandle(symbol);
		if (symbolHandle == 0) {
			String message = InternalResource.loadString("symbol",
					InternalResource.GlobalInvalidConstructorArgument,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		// 判断是否应该添加,,,,暂时先这么处理
		if (this instanceof SymbolFillLibrary
				&& !(symbol instanceof SymbolFill)) {
			String message = InternalResource.loadString("symbol",
					InternalResource.SymbolLibraryUnsupportedType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		} else if (this instanceof SymbolLineLibrary
				&& !(symbol instanceof SymbolLine)) {
			String message = InternalResource.loadString("symbol",
					InternalResource.SymbolLibraryUnsupportedType,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		} 
//		else if (this instanceof SymbolMarkerLibrary
//				&& !(symbol instanceof SymbolMarker||symbol instanceof SymbolMarker3D)) {
//			String message = InternalResource.loadString("symbol",
//					InternalResource.SymbolLibraryUnsupportedType,
//					InternalResource.BundleName);
//			throw new IllegalArgumentException(message);
//		}
		// Wrapj拷贝了symbol对象
		long handle = SymbolLibraryNative.jni_add(this.getHandle(),
				symbolHandle);
		int resultValue = -1;
		if (handle != 0) {
			Symbol sb = Symbol.createInstance(handle);
			resultValue = sb.getID();
			sb.setIsDisposable(false);
			if (m_SymbolGroup != null) {
				m_SymbolGroup.getSymbols().add(sb);
			}
			sb.setLibrary(this);
			sb.setGroup(m_SymbolGroup);
		}
//		symbol.makeSureNativeObjectLive();
		return resultValue;
	}

	public boolean moveTo(int id, SymbolGroup group) {
		// 底层对Index有判断
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString(
					"moveTo(int index, SymbolGroup group)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (group == null) {
			String message = InternalResource.loadString("group",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new NullPointerException(message);
		}
		long groupHandle = InternalHandle.getHandle(group);
		if (groupHandle == 0) {
			String message = InternalResource.loadString("group",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		boolean result = false;
		Symbol sysbol = this.findSymbol(id);
		SymbolGroup srcGroup = sysbol.getGroup();
		int index = srcGroup.indexOf(id);
		result =srcGroup.moveTo(index, group);
		return result;

	}
	public void clear() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("clear()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		SymbolLibraryNative.jni_clear(this.getHandle());
		if (m_SymbolGroup != null) {
			m_SymbolGroup.reset();
		}
	}

	public SymbolGroup getRootGroup() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getRootGroup()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (this.m_SymbolGroup == null) {
			long handle = SymbolLibraryNative
					.jni_GetRootGroup(this.getHandle());
			if (handle != 0) {
				this.m_SymbolGroup = new SymbolGroup(this, handle);
			}
		}
		return this.m_SymbolGroup;
	}
	
	String getLibPath()
	{
		return SymbolLibraryNative.jni_GetLibPath(this.getHandle());
	}

	protected void clearHandle() {
		if (m_SymbolGroup != null) {
			m_SymbolGroup.clearHandle();
			m_SymbolGroup = null;
		}
		setHandle(0);
	}

}
