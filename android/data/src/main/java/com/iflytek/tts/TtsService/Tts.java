package com.iflytek.tts.TtsService;

public final class Tts{
//	static {
//		System.loadLibrary("Aisound");	
//	}
	public static native int JniGetVersion();
	public static native int JniCreate(String resFilename);	
	public static native int JniDestory();
	public static native int JniSpeak(String text);	
	public static native int JniStop(); 
	public static native int JniSetParam(int paramId,int value);
	public static native int JniGetParam(int paramId);
	
	//参数类型
	public  static class  TTS_PARAM {
		public final static int   PARAM_PARAMCH_CALLBACK	= 0x00000000;       // parameter change callback entry 
		public final static int   PARAM_LANGUAGE			= 0x00000100;       // language, e.g. Chinese 
		public final static int   PARAM_INPUT_CODEPAGE		= 0x00000101;       // input code page, e.g. GBK 
		public final static int   PARAM_TEXT_MARK			= 0x00000102;       // text mark, e.g. CSSML 
		public final static int   PARAM_USE_PROMPTS			= 0x00000104;       // whether use prompts 
		public final static int   PARAM_RECOGNIZE_PHONEME	= 0x00000105;       // how to recognize phoneme input 
		public final static int   PARAM_INPUT_MODE			= 0x00000200;       // input mode, e.g. from fixed buffer, from callback
		public final static int   PARAM_INPUT_TEXT_BUFFER	= 0x00000201;       // input text buffer 
		public final static int   PARAM_INPUT_TEXT_SIZE		= 0x00000202;       // input text size 
		public final static int   PARAM_INPUT_CALLBACK		= 0x00000203;       // input callback entry 
		public final static int   PARAM_PROGRESS_BEGIN		= 0x00000204;       // current processing position 
		public final static int   PARAM_PROGRESS_LENGTH		= 0x00000205;       // current processing length 
		public final static int   PARAM_PROGRESS_CALLBACK	= 0x00000206;       // progress callback entry 
		public final static int   PARAM_READ_AS_NAME		= 0x00000301;       // whether read as name 
		public final static int   PARAM_READ_DIGIT			= 0x00000302;       // how to read digit, e.g. read as number, read as value  
		public final static int   PARAM_CHINESE_NUMBER_1	= 0x00000303;       // how to read number "1" in Chinese 
		public final static int   PARAM_MANUAL_PROSODY		= 0x00000304;       // whether use manual prosody 
		public final static int   PARAM_ENGLISH_NUMBER_0	= 0x00000305;       // how to read number "0" in Englsih 
		public final static int   PARAM_READ_WORD           = 0x00000306;       // how to read word in Englsih,  e.g. read by word, read as alpha  
		public final static int   PARAM_OUTPUT_CALLBACK		= 0x00000401;       // output callback entry 
		public final static int   PARAM_ROLE				= 0x00000500;       // speaker role 
		public final static int   PARAM_SPEAK_STYLE			= 0x00000501;       // speak style 
		public final static int   PARAM_VOICE_SPEED			= 0x00000502;       // voice speed 
		public final static int   PARAM_VOICE_PITCH		    = 0x00000503;       // voice tone 
		public final static int   PARAM_VOLUME			    = 0x00000504;       // volume value 
		public final static int   PARAM_CHINESE_ROLE        = 0x00000510;       // Chinese speaker role 
		public final static int   PARAM_ENGLISH_ROLE        = 0x00000511;       // English speaker role 
		public final static int   PARAM_VEMODE				= 0x00000600;       // voice effect - predefined mode 
		public final static int   PARAM_USERMODE			= 0x00000701;       // user's mode 
		public final static int   PARAM_NAVIGATION_MODE		= 0x00000701;       // Navigation Version
	
		public final static int   PARAM_EVENT_CALLBACK		= 0x00001001;       // sleep callback entry 
		public final static int   PARAM_OUTPUT_BUF			= 0x00001002;       // output buffer 
		public final static int   PARAM_OUTPUT_BUFSIZE		= 0x00001003;       // output buffer size 
		public final static int   PARAM_DELAYTIME			= 0x00001004;       // delay time */
	}

	public  static class  TEXT_MARK {
		public final static int   TEXTMARK_NONE			= 0;			// none 
		public final static int   TEXTMARK_SIMPLE_TAGS		= 1;	    // simple tags (default) 
	}
	
	public  static class  INPUT_MODE {
		public final static int  INPUT_FIXED_BUFFER	      = 0;		    // from fixed buffer 
		public final static int  INPUT_CALLBACK		      = 1;		    // from callback 
	}
	
	public  static class   READ_DIGIT {
		public final static int     READDIGIT_AUTO		  = 0;			// decide automatically (default) 
		public final static int     READDIGIT_AS_NUMBER	  = 1;			// say digit as number 
		public final static int     READDIGIT_AS_VALUE	  = 2;			// say digit as value 
	}
	public  static class CHINESE_NUMBER{	
		public final static int   CHNUM1_READ_YAO  	      = 0;			// read number "1" [yao1] in chinese (default)  
		public final static int   CHNUM1_READ_YI		  = 1;			// read number "1" [yi1] in chinese  
	}
	public  static class   ENGLISH_NUMBER{
		public final static int   ENNUM0_READ_ZERO	      = 0;			// read number "0" [zero] in english (default) 
		public final static int   ENNUM0_READ_O		      = 1;			// read number "0" [o] in englsih 
	}
	
	 
	public  static class    SPEAK_STYLE {
		public final static int  STYLE_PLAIN			  =0;			// plain speak style 
		public final static int  STYLE_NORMAL			  =1;			// normal speak style (default) 
	}
	public  static class     VOICE_SPEED {
	// the range of voice speed value is from -32768 to +32767  
		public final static int SPEED_MIN				= -32768;		// slowest voice speed  
		public final static int SPEED_NORMAL			= 0	;		    // normal voice speed (default)  
		public final static int SPEED_MAX				= +32767;		// fastest voice speed 
	}
	public  static class VOICE_PITCH {
	// the range of voice tone value is from -32768 to +32767 
		public final static int PITCH_MIN				= -32768;		// lowest voice tone *
		public final static int PITCH_NORMAL			= 0;			// normal voice tone (default) */
		public final static int PITCH_MAX				= +32767;		// highest voice tone */
	}
	public  static class PARAM_VOLUME {
	    // the range of volume value is from -32768 to +32767 
		public final static int VOLUME_MIN				= -32768;		// minimized volume 
		public final static int VOLUME_NORMAL			= 0;		    // normal volume 
		public final static int VOLUME_MAX				= +32767;		// maximized volume (default) 
	}
	public  static class  PARAM_VEMODE {
		public final static int VEMODE_NONE				= 0;			// none 
		public final static int VEMODE_WANDER			= 1;			// wander 
		public final static int VEMODE_ECHO				= 2;			// echo 
		public final static int VEMODE_ROBERT			= 3;			// robert 
		public final static int VEMODE_CHROUS			= 4;			// chorus
		public final static int VEMODE_UNDERWATER		= 5;     		// underwater 
		public final static int VEMODE_REVERB			= 6;	    	// reverb 
		public final static int VEMODE_ECCENTRIC		= 7;			// eccentric 
	}
	public  static class  NAVIGATION_MODE{
		public final static int USE_NORMAL				=0;			    // synthesize in the Mode of Normal 
		public final static int SE_NAVIGATION			=1;	     	    // synthesize in the Mode of Navigation 
		public final static int USE_MOBILE				=2;			    // synthesize in the Mode of Mobile 
		public final static int USE_EDUCATION			=3;			    // synthesize in the Mode of Education 
	}
	public  static class  READ_WORD {
		public final static int READWORD_BY_WORD		=2;			    // say words by the way of word  
		public final static int READWORD_BY_ALPHA		=1;			    // say words by the way of alpha  
		public final static int READWORD_BY_AUTO		=0;			    // say words by the way of auto  
	}
	
	//语言参数
	public  static class  TTS_LANGUAGE{
	 	public final static int LANGUAGE_AUTO           = 0;       // Detect language automatically 
	 	public final static int LANGUAGE_CHINESE		= 1;	   // Chinese (with English) 
	  	public final static int LANGUAGE_ENGLISH		= 2;	   // English 
	}
	
	//角色参数
	public  static class TTS_ROLE{
		public final static int   ROLE_TIANCHANG			= 1	;		// Tianchang (female, Chinese)  
		public final static int   ROLE_WENJING				= 2	;		// Wenjing (female, Chinese)  
		public final static int   ROLE_XIAOYAN				= 3	;		// Xiaoyan (female, Chinese)  
		public final static int   ROLE_YANPING				= 3	;		// Xiaoyan (female, Chinese)  
		public final static int   ROLE_XIAOFENG				= 4	;		// Xiaofeng (male, Chinese)  
		public final static int   ROLE_YUFENG				= 4	;		// Xiaofeng (male, Chinese)  
		public final static int   ROLE_SHERRI				= 5	;		// Sherri (female, US English) 
		public final static int   ROLE_XIAOJIN				= 6	;		// Xiaojin (female, Chinese) 
		public final static int   ROLE_NANNAN				= 7	;		// Nannan (child, Chinese)  
		public final static int   ROLE_JINGER				= 8	;		// Jinger (female, Chinese) 
		public final static int   ROLE_JIAJIA				= 9	;		// Jiajia (girl, Chinese)  
		public final static int   ROLE_YUER					= 10;		// Yuer (female, Chinese) 
		public final static int   ROLE_XIAOQIAN				= 11;		// Xiaoqian (female, Chinese Northeast) 
		public final static int   ROLE_LAOMA				= 12;		// Laoma (male, Chinese) 
		public final static int   ROLE_BUSH					= 13; 		// Bush (male, US English) 
		public final static int   ROLE_XIAORONG				= 14;		// Xiaorong (female, Chinese Szechwan) 
		public final static int   ROLE_XIAOMEI				= 15;		// Xiaomei (female, Cantonese) 
		public final static int   ROLE_ANNI					= 16;	 	// Anni (female, Chinese) 
		public final static int   ROLE_JOHN					= 17;		// John (male, US English) 
		public final static int   ROLE_ANITA				= 18;		// Anita (female, British English) 
		public final static int   ROLE_TERRY				= 19;		// Terry (female, US English) 
		public final static int   ROLE_CATHERINE			= 20;		// Catherine (female, US English) 
		public final static int   ROLE_TERRYW				= 21;		// Terry (female, US English Word) 
		public final static int   ROLE_XIAOLIN				= 22;		// Xiaolin (female, Chinese) 
		public final static int   ROLE_XIAOMENG				= 23;		// Xiaomeng (female, Chinese) 
		public final static int   ROLE_XIAOQIANG			= 24;		// Xiaoqiang (male, Chinese) 
		public final static int   ROLE_XIAOKUN				= 25;		// XiaoKun (male, Chinese) 
		public final static int   ROLE_JIUXU				= 51;		// Jiu Xu (male, Chinese) 
		public final static int   ROLE_DUOXU				= 52;		// Duo Xu (male, Chinese) 
		public final static int   ROLE_XIAOPING				= 53;		// Xiaoping (female, Chinese) 
		public final static int   ROLE_DONALDDUCK			= 54;		// Donald Duck (male, Chinese) 
		public final static int   ROLE_BABYXU				= 55;		// Baby Xu (child, Chinese) 
		public final static int   ROLE_DALONG				= 56;		// Dalong (male, Cantonese) 
		public final static int   ROLE_USER					= 99;		// user defined 
	}
}
