package com.supermap.plugin;

import android.util.Log;
import com.iflytek.tts.TtsService.Tts;
import com.iflytek.tts.TtsService.Tts.TTS_LANGUAGE;
import com.iflytek.tts.TtsService.Tts.TTS_PARAM;

public class SpeakPlugin {
	/**
	 * Ҫ����������
	 */
	private String mSpeekWords = "";
	
	//ȫ��ʹ�õ��߳���
	private Object mLock = new Object();
	
	//������Ĭ�Ϸ��������ļ�·���µ�/voice/resource.irf
	private static final String VoiceFile = com.supermap.data.Environment.getConfigFileDirectory()+"/voice/resource.irf";
	
	/**
	 * �����Ƿ�����
	 */
	private boolean mActive =  false;
	
	/**
	 * ���ʵ��
	 */
	private static SpeakPlugin mInstance = null;
		
	/**
	 * ��̨�Ĳ�������
	 */
	private Runnable mTask = new Runnable() {
		public void run() {
			while(mActive){
				//û�в�������������
				if(mSpeekWords == null){
					synchronized (mLock) {
						try {
							mLock.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				Tts.JniSpeak(mSpeekWords);
				mSpeekWords = null;
			}
		}
	};
	
	/**
	 * ����һ���������
	 * @param voiceFile �����ļ�
	 */
	private SpeakPlugin(String voiceFile) {	
		if(!InternalEnvironment.isSuportSpeeking()){
			return;
		}
		
		Tts.JniCreate(voiceFile);
		
		//�Զ�ʶ������
		Tts.JniSetParam(TTS_PARAM.PARAM_LANGUAGE, TTS_LANGUAGE.LANGUAGE_AUTO);
	}
	
	/**
	 * ���ò����ٶ�
	 * @param speed ȡֵΪ -32768 �� 32768 Ĭ��ֵΪ0
	 */
	public void setSpeakSpeed(int speed){
		if(!InternalEnvironment.isSuportSpeeking()){
			return;
		}
		Tts.JniSetParam(TTS_PARAM.PARAM_VOICE_SPEED, speed);
	}
	
	/**
	 * ��ȡ��ǰ�����ٶ�
	 * @return
	 */
	public int getSpeakSpeed(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return 0;
		}
		return Tts.JniGetParam(TTS_PARAM.PARAM_VOICE_SPEED);
	}
	
	/**
	 * ���ò�������
	 * @param speed ȡֵΪ -32768 �� 32768 Ĭ��ֵΪ32768
	 */
	public void setSpeakVolum(int volum){
		if(!InternalEnvironment.isSuportSpeeking()){
			return;
		}
		Tts.JniSetParam(TTS_PARAM.PARAM_VOLUME, volum);
	}
	
	/**
	 * ��ȡ��ǰ��������
	 * @return
	 */
	public int getSpeakVolum(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return 0;
		}
		return Tts.JniGetParam(TTS_PARAM.PARAM_VOLUME);
	}
	
	/**
	 * ���ò�����
	 */
	public void setSpeaker(Speaker speaker){
		if(!InternalEnvironment.isSuportSpeeking()){
			return;
		}
		Tts.JniSetParam(TTS_PARAM.PARAM_ROLE, speaker.getValue());
	}
	
	
	public static SpeakPlugin getInstance(){
		if(mInstance == null){
			mInstance = new SpeakPlugin(VoiceFile);
			if(!InternalEnvironment.isSuportSpeeking()){
				Log.e("SpeakPlugin",InternalResource.loadString("SpeakPlugin getInstance()", InternalResource.LakeOfSpeekingRes, InternalResource.BundleName));
			}
		}
		return mInstance;
	}
	/**
	 * �����������
	 * @return 
	 */
	public boolean laugchPlugin(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return false;
		}
		
		if(mActive){
			return mActive;
		}
		//�����̣߳�ִ�в�������
		new Thread(mTask).start();
		mActive = true;
		return true;
	}
	
	/**
	 * ��������
	 * @param words ��������
	 * @return
	 */
	public boolean playSound(String words)
	{
		if(!InternalEnvironment.isSuportSpeeking()){
			return false;
		}
		
		//�մ�������
		if(words.length() == 0){
			return false;
		}
		mSpeekWords = words;
		synchronized (mLock) {
			mLock.notifyAll();
		}
		return true;
	}
	
	/**
	 * ֹͣ���ŵ�ǰ����
	 * @return
	 */
	public boolean stopPlay(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return false;
		}
		
		//�����ǰ���ڲ��������ֹͣ
		if(mActive && mSpeekWords!= null){
			Tts.JniStop();
			mSpeekWords = null;
			return true;
		}
		return false;
	}
	
	/**
	 * �����ò��
	 */
	public boolean terminatePlugin(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return false;
		}
		
		if(mActive){
			mActive = false;
			Tts.JniStop();
			Tts.JniDestory();
			mInstance = null;
			return true;
		}
		return false;
	}
	
}
