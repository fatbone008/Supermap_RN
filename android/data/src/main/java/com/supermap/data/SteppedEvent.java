package com.supermap.data;

import java.util.EventObject;

public class SteppedEvent extends EventObject {
	
	private int m_percent;
	
	private long m_remainTime;
	
	private String m_title;
	
	private String m_message;
	
	private String m_senderMethodName;
	
	private boolean m_cancel;
	
	public SteppedEvent(Object source, int percent, long remainTime,
			String title, String message, String senderMethodName) {
		super(source);
		m_percent = percent;
		m_remainTime = remainTime;
		m_title = title;
		m_message = message;
		m_senderMethodName = senderMethodName;
	}
	
	public SteppedEvent(Object source, int percent, long remainTime,
			String title, String message, String senderMethodName,boolean cancel) {
		super(source);
		m_percent = percent;
		m_remainTime = remainTime;
		m_title = title;
		m_message = message;
		m_senderMethodName = senderMethodName;
		m_cancel = cancel;
	}

	public int getPercent() {
		return m_percent;
	}
	
	public long getRemainTime() {
		return m_remainTime;
	}
	
	public String getTitle() {
		return m_title;
	}
	
	public String getMessage() {
		return m_message;
	}
	
	public String getSenderMethodName() {
		return m_senderMethodName;
	}
	
	public void setCancel(boolean value) {
		m_cancel = value;
	}

	public boolean getCancel() {
		return m_cancel;
	}
}
