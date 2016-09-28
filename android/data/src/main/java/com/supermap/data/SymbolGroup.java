package com.supermap.data;

import java.util.ArrayList;

//符号库分组对象，用于管理符号库中的分组，
//不负责符号的导入导出，添加与删除，修改保持等。
//相对应这些功能在SymbolLib中提供

public class SymbolGroup extends InternalHandleDisposable {
	private SymbolLibrary m_symbolLibrary;

	private SymbolGroup m_parentSymbolGroup;

	private SymbolGroups m_symbolGroups;

	private ArrayList m_symbols;

	SymbolGroup(SymbolLibrary symbolLibrary, long handle) {
		this.m_symbolLibrary = symbolLibrary;
		this.setHandle(handle, false);
		m_symbols = new ArrayList();
		reset();

	}

	SymbolGroup(long handle, SymbolGroup symbolGroup) {
		this.m_symbolLibrary = symbolGroup.getLib();
		this.setHandle(handle, false);
		this.setParent(symbolGroup);
		m_symbols = new ArrayList();
		reset();
	}

	public SymbolGroup getParent() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getParent()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_parentSymbolGroup;

	}

	public SymbolGroups getChildGroups() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getChildGroups()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (m_symbolGroups == null) {
			m_symbolGroups = new SymbolGroups(this);
		}
		return m_symbolGroups;
	}

	public String getName() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getName()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return SymbolGroupNative.jni_GetName(this.getHandle());
	}

	// 获取当前分组下指定索引处的符号对象
	public Symbol get(int index) {
		Symbol result = null;
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("get(int index)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		
		if(index >= 0 && index < m_symbols.size()){
			result = (Symbol) m_symbols.get(index);
		}
		
		return result;
	}

	public SymbolLibrary getLibrary() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getLibrary()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_symbolLibrary;

	}

	// 获取当前分组下符号的个数
	public int getCount() {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("getCount()",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return SymbolGroupNative.jni_GetCount(this.getHandle());

	}

	// 测试指定ID符号在当前分组下的索引值(不包含其子分组)
	public int indexOf(int id) {
		if (this.getHandle() == 0) {
			String message = InternalResource.loadString("indexOf(int id)",
					InternalResource.HandleObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return SymbolGroupNative.jni_IndexOf(this.getHandle(), id);
	}

	protected boolean moveTo(int index, SymbolGroup group) {
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

		boolean result = SymbolGroupNative.jni_moveTo(this.getHandle(), index,
				groupHandle);
		if (result) {
			group.getSymbols().add(this.get(index));
			this.get(index).setGroup(group);
			this.getSymbols().remove(index);
		}
		return result;

	}

	public void dispose() {
		if (!this.getIsDisposable()) {
			String message = InternalResource.loadString("dispose()",
					InternalResource.HandleUndisposableObject,
					InternalResource.BundleName);
			throw new UnsupportedOperationException(message);
		}
	}

	protected void clearHandle() {

		m_symbolLibrary = null;
		m_symbolLibrary = null;
		m_parentSymbolGroup = null;
		if (m_symbolGroups != null) {
			m_symbolGroups.clearHandle();
			m_symbolGroups = null;
		}
		if (m_symbols != null) {
			for (int i = 0; i < m_symbols.size(); i++) {
				((Symbol) m_symbols.get(i)).clearHandle();
			}
			m_symbols.clear();
			m_symbols = null;
		}
		this.setHandle(0);
	}

	void setParent(SymbolGroup symbolGroup) {
		this.m_parentSymbolGroup = symbolGroup;
	}

	void reset() {
		for (int i = 0; i < m_symbols.size(); i++) {
			((Symbol) m_symbols.get(i)).clearHandle();
		}
		m_symbols.clear();
		int count = this.getCount();
		if (count > 0) {
			long[] handles = new long[count];
			SymbolGroupNative.jni_GetSymbols(getHandle(), handles);
			for (int i = 0; i < count; i++) {
				Symbol symbol = Symbol.createInstance(handles[i]);
				
				if(symbol != null){
					symbol.setIsDisposable(false);
					symbol.setLibrary(m_symbolLibrary);
					symbol.setGroup(this);
					m_symbols.add(symbol);
				}
			}
		}
		if (m_symbolGroups != null) {
			m_symbolGroups.reset();
		}
	}

	ArrayList getSymbols() {
		return m_symbols;
	}

	SymbolLibrary getLib() {
		return this.m_symbolLibrary;
	}

	boolean remove(int id) {
		int index = this.indexOf(id);
		if (index != -1) {
			this.getSymbols().remove(index);
			return SymbolGroupNative.jni_RemoveByID(this.getHandle(), id);
		} else {
			for (int i = 0; i < m_symbolGroups.getCount(); i++) {
				return m_symbolGroups.get(i).remove(id);
			}
		}
		return false;
	}

	SymbolGroup findGroup(int id) {
		SymbolGroup result = null;
		if (this.indexOf(id) != -1) {
			return this;
		} else {
			for (int i = 0; i < this.getChildGroups().getCount(); i++) {
				result = this.getChildGroups().get(i).findGroup(id);
				if (result != null) {
					return result;
				}
			}
		}
		return result;
	}

	Symbol findSymbol(int id) {
		Symbol result = null;
		for (int i = 0; i < this.getCount(); i++) {
			result = this.get(i);
			if (result != null && result.getID() == id) {
				return result;
			}
		}
		for (int i = 0; i < this.getChildGroups().getCount(); i++) {
			result = this.getChildGroups().get(i).findSymbol(id);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	Symbol findSymbol(String name) {
		Symbol result = null;
		for (int i = 0; i < this.getCount(); i++) {
			result = this.get(i);
			if (result != null && result.getName().equals(name)) {
				return result;
			}
		}
		for (int i = 0; i < this.getChildGroups().getCount(); i++) {
			result = this.getChildGroups().get(i).findSymbol(name);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
