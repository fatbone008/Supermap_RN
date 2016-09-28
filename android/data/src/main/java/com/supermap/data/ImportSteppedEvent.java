package com.supermap.data;

import java.util.EventObject;

class ImportSteppedEvent extends EventObject {
	
	private int m_totalPercent;
	
	private int m_subPercent;
	
	private ImportSetting m_currentTask;
	
	private int m_count;
	
	private boolean m_cancel;
	
	public ImportSteppedEvent(Object source,int percent, int subPercent, 
			ImportSetting currentTask, int count, boolean cancel) {
		super(source);
		m_totalPercent = percent;
		m_subPercent = subPercent;
		m_currentTask = currentTask;
		m_count = count;
		m_cancel = cancel;
//		InternalHandleDisposable.makeSureNativeObjectLive(currentTask);
	}

	public int getTotalPercent() {
		return m_totalPercent;
	}
	
	public int getSubPercent() {
		return m_subPercent;
	}
	
	public ImportSetting getCurrentTask() {
		return m_currentTask;
	}
	
	public int getCount() {
		return m_count;
	}
	
	public boolean getCancel() {
		return m_cancel;
	}
	
	public void setCancel(boolean cancel) {
		m_cancel = cancel;
	}
}
