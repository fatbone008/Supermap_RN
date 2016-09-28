package com.supermap.plugin;

import android.util.Log;
import com.iflytek.tts.TtsService.Tts;
import com.iflytek.tts.TtsService.Tts.TTS_LANGUAGE;
import com.iflytek.tts.TtsService.Tts.TTS_PARAM;

public class SpeakPlugin {
	/**
	 * 要播报的内容
	 */
	private String mSpeekWords = "";
	
	//全局使用的线程锁
	private Object mLock = new Object();
	
	//语音库默认放在配置文件路径下的/voice/resource.irf
	private static final String VoiceFile = com.supermap.data.Environment.getConfigFileDirectory()+"/voice/resource.irf";
	
	/**
	 * 任务是否启用
	 */
	private boolean mActive =  false;
	
	/**
	 * 插件实例
	 */
	private static SpeakPlugin mInstance = null;
		
	/**
	 * 后台的播放任务
	 */
	private Runnable mTask = new Runnable() {
		public void run() {
			while(mActive){
				//没有播放内容则休眠
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
	 * 创建一个语音插件
	 * @param voiceFile 语音文件
	 */
	private SpeakPlugin(String voiceFile) {	
		if(!InternalEnvironment.isSuportSpeeking()){
			return;
		}
		
		Tts.JniCreate(voiceFile);
		
		//自动识别语言
		Tts.JniSetParam(TTS_PARAM.PARAM_LANGUAGE, TTS_LANGUAGE.LANGUAGE_AUTO);
	}
	
	/**
	 * 设置播报速度
	 * @param speed 取值为 -32768 到 32768 默认值为0
	 */
	public void setSpeakSpeed(int speed){
		if(!InternalEnvironment.isSuportSpeeking()){
			return;
		}
		Tts.JniSetParam(TTS_PARAM.PARAM_VOICE_SPEED, speed);
	}
	
	/**
	 * 获取当前播报速度
	 * @return
	 */
	public int getSpeakSpeed(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return 0;
		}
		return Tts.JniGetParam(TTS_PARAM.PARAM_VOICE_SPEED);
	}
	
	/**
	 * 设置播报音量
	 * @param speed 取值为 -32768 到 32768 默认值为32768
	 */
	public void setSpeakVolum(int volum){
		if(!InternalEnvironment.isSuportSpeeking()){
			return;
		}
		Tts.JniSetParam(TTS_PARAM.PARAM_VOLUME, volum);
	}
	
	/**
	 * 获取当前播报音量
	 * @return
	 */
	public int getSpeakVolum(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return 0;
		}
		return Tts.JniGetParam(TTS_PARAM.PARAM_VOLUME);
	}
	
	/**
	 * 设置播报人
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
	 * 启动语音插件
	 * @return 
	 */
	public boolean laugchPlugin(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return false;
		}
		
		if(mActive){
			return mActive;
		}
		//启动线程，执行播放任务
		new Thread(mTask).start();
		mActive = true;
		return true;
	}
	
	/**
	 * 播报语音
	 * @param words 语音内容
	 * @return
	 */
	public boolean playSound(String words)
	{
		if(!InternalEnvironment.isSuportSpeeking()){
			return false;
		}
		
		//空串不播放
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
	 * 停止播放当前内容
	 * @return
	 */
	public boolean stopPlay(){
		if(!InternalEnvironment.isSuportSpeeking()){
			return false;
		}
		
		//如果当前正在播放则可以停止
		if(mActive && mSpeekWords!= null){
			Tts.JniStop();
			mSpeekWords = null;
			return true;
		}
		return false;
	}
	
	/**
	 * 结束该插件
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
