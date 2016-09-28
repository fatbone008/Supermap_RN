package com.supermap.data;

import java.util.EventObject;

class ExportSteppedEvent extends EventObject {
	
	private int m_totalPercent;
	
	private int m_subPercent;
	
	private ExportSetting m_currentTask;
	
	private int m_count;
	
	private boolean m_cancel;
	
	public ExportSteppedEvent(Object source,int percent, int subPercent, 
			ExportSetting currentTask, int count, boolean cancel) {
		super(source);
		m_totalPercent = percent;
		m_subPercent = subPercent;
		m_currentTask = currentTask;
		m_count = count;
		m_cancel = cancel;
	}

	public int getTotalPercent() {
		return m_totalPercent;
	}
	
	public int getSubPercent() {
		return m_subPercent;
	}
	
	public ExportSetting getCurrentTask() {
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
