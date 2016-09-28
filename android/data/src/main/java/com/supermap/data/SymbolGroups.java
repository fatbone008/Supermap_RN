package com.supermap.data;

import java.util.ArrayList;

public class SymbolGroups {
	private SymbolGroup m_parentSymbolGroup;

	private ArrayList m_symbolGroups;

	private SymbolLibrary m_symbolLibrary;

	SymbolGroups(SymbolGroup symbolGroup) {
		this.m_parentSymbolGroup = symbolGroup;
		this.m_symbolGroups = new ArrayList();
		this.m_symbolLibrary = symbolGroup.getLib();
		reset();
	}

	public SymbolGroup get(int index) {
		if (m_parentSymbolGroup == null || m_parentSymbolGroup.getHandle() == 0
				|| m_symbolGroups == null) {
			String message = InternalResource.loadString("index",
					InternalResource.GlobalArgumentObjectHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return (SymbolGroup) m_symbolGroups.get(index);
	}

	public SymbolGroup get(String name) {
		if (m_parentSymbolGroup == null || m_parentSymbolGroup.getHandle() == 0
				|| m_symbolGroups == null) {
			String message = InternalResource.loadString("get(String name)",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}

		SymbolGroup symbolGroup = null;

		int index = indexOf(name);
		if (index != -1) {
			symbolGroup = (SymbolGroup) m_symbolGroups.get(index);
		}
		return symbolGroup;
	}

	public int getCount() {
		if (m_parentSymbolGroup == null || m_parentSymbolGroup.getHandle() == 0
				|| m_symbolGroups == null) {
			String message = InternalResource.loadString("getCount()",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		return m_symbolGroups.size();
	}

	// 只在当前组找
	public int indexOf(String name) {
		if (m_parentSymbolGroup == null || m_parentSymbolGroup.getHandle() == 0
				|| m_symbolGroups == null) {
			String message = InternalResource.loadString(
					"indexOf(String name)",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		int index = -1;
		if (name != null && name.trim().length() != 0) {
			index = SymbolGroupsNative.jni_IndexOf(m_parentSymbolGroup
					.getHandle(), name);
		}
		return index;
	}

	// 新分组的名称，如果名称已存在则抛出异常
	public SymbolGroup create(String name) {
		if (m_parentSymbolGroup == null || m_parentSymbolGroup.getHandle() == 0
				|| m_symbolGroups == null) {
			String message = InternalResource.loadString(
					"create(String name) ",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (name == null || name.trim().length() == 0) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		if (contains(name)) {
			String message = InternalResource.loadString("value",
					InternalResource.GlobalSpecifiedNameAlreadyExist,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		long handle = SymbolGroupsNative.jni_Create(m_parentSymbolGroup
				.getHandle(), name);

		SymbolGroup symbolGroup = null;
		if (handle != 0) {
			symbolGroup = new SymbolGroup(handle, m_parentSymbolGroup);
			m_symbolGroups.add(symbolGroup);

		}
		return symbolGroup;
	}

	public boolean contains(String name) {
		if (m_parentSymbolGroup == null || m_parentSymbolGroup.getHandle() == 0
				|| m_symbolGroups == null) {
			String message = InternalResource.loadString(
					"contains(String name)",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		if (name == null || name.trim().length() == 0) {
			String message = InternalResource.loadString("name",
					InternalResource.GlobalArgumentNull,
					InternalResource.BundleName);
			throw new IllegalArgumentException(message);
		}
		return (indexOf(name) != -1);
	}

	// 这里的移除只是修改分组的情况，而不会删除符号。
	// 返回结果为true时，标示当前分组的符号会移到上一层中
	public boolean remove(String name) {
		if (m_parentSymbolGroup == null || m_parentSymbolGroup.getHandle() == 0
				|| m_symbolGroups == null) {
			String message = InternalResource.loadString("remove(String name)",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		boolean result = false;
		if (name == null || name.trim().length() == 0) {
			result = false;
		} else {
			int index = indexOf(name);
			if (index == -1) {
				result = false;
			} else {
				result = SymbolGroupsNative.jni_Remove(m_parentSymbolGroup
						.getHandle(), index);
				if (result) {
					SymbolGroup symbolGroup = this.get(index);
					int countGroup = symbolGroup.getChildGroups().getCount();
					for (int i = 0; i < countGroup; i++) {
						this.m_symbolGroups.add(symbolGroup.getChildGroups()
								.get(i));
					}
					int countSymbol = symbolGroup.getCount();
					for (int j = 0; j < countSymbol; j++) {
						this.m_parentSymbolGroup.getSymbols().add(
								symbolGroup.get(j));
						symbolGroup.get(j).setGroup(this.m_parentSymbolGroup);
					}
					this.m_symbolGroups.remove(symbolGroup);
					symbolGroup.clearHandle();
				}
			}
		}

		return result;

	}

	void reset() {
		if (m_parentSymbolGroup == null || m_parentSymbolGroup.getHandle() == 0
				|| m_symbolGroups == null) {
			String message = InternalResource.loadString("",
					InternalResource.GlobalOwnerHasBeenDisposed,
					InternalResource.BundleName);
			throw new IllegalStateException(message);
		}
		for (int i = 0; i < m_symbolGroups.size(); i++) {
			((SymbolGroup) m_symbolGroups.get(i)).clearHandle();
		}
		m_symbolGroups.clear();
		int count = SymbolGroupsNative.jni_GetCount(m_parentSymbolGroup
				.getHandle());
		if (count > 0) {
			long[] handles = new long[count];
			SymbolGroupsNative.jni_GetSymbolGroups(m_parentSymbolGroup
					.getHandle(), handles);
			for (int i = 0; i < count; i++) {
				SymbolGroup symbolGroup = new SymbolGroup(handles[i],
						m_parentSymbolGroup);
				m_symbolGroups.add(symbolGroup);
			}
		}
	}

	protected void clearHandle() {
		m_parentSymbolGroup = null;
		if (m_symbolGroups != null) {
			for (int i = 0; i < m_symbolGroups.size(); i++) {
				((SymbolGroup) m_symbolGroups.get(i)).clearHandle();
			}
			m_symbolGroups.clear();
			m_symbolGroups = null;
		}
		m_symbolLibrary = null;

	}

}
