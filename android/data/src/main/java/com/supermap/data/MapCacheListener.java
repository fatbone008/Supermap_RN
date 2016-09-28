/**
 * 
 */
package com.supermap.data;

/**
 * @author 郑月玲
 *
 */
public interface MapCacheListener {

	// 预缓存结束时回调， 告诉用户任务完成，有nFailedCount张图片下载失败。
	public void onComplete(int nFailedCount);
	
	// 进度值从1-100， 进度跳动一次后通知用户一次
	public void onProgress(int nStep);
	
	// 触发时，表示累计十张图片下载失败，用户该检查网络了。可以不处理，每累计十张下载失败通知一次，用户可以在回调里定制多少个十次再真正停止预缓存或者弹框等。
	public void onChecked();
	
	// 详细进度信息，反馈给用户已下载缓存张数nDownLoadCout，总张数nTotalCount
	public void onCacheStatus(int nDownLoadCout, long nTotalCount);
}
