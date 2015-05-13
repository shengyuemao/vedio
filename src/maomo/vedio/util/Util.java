package maomo.vedio.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.storage.StorageManager;

public class Util
{

	public static ScreenBean getScreenPix(Activity activity)
	{

		ScreenBean screenBean = new ScreenBean();
		screenBean.setHeight(getScreenHeight(activity));
		screenBean.setWidth(getScreenWidth(activity));
		return screenBean;
	}

	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Activity activity)
	{
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Activity activity)
	{
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	
	/**
	 * 获取所有存储路径
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getDirs(Context context) {
		List<String> dirs = new ArrayList<String>();
		StorageManager storageManager = (StorageManager) context
				.getSystemService(Context.STORAGE_SERVICE);
		try {
			Class[] paramClasses = {};
			Method getVolumePathsMethod = StorageManager.class.getMethod(
					"getVolumePaths", paramClasses);
			getVolumePathsMethod.setAccessible(true);
			Object[] params = {};
			Object invoke = getVolumePathsMethod.invoke(storageManager, params);
			for (int i = 0; i < ((String[]) invoke).length; i++) {
				// System.out.println(((String[])invoke)[i]);
				dirs.add(((String[]) invoke)[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dirs;
	}

	/**
	 * 获取最大音量
	 * 
	 * @param context
	 * @return
	 */
	public static int getMaxVolume(Context context) {
		return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE))
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	/**
	 * 获取当前音量
	 * 
	 * @param context
	 * @return
	 */
	public static int getCurVolume(Context context) {
		return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE))
				.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	/**
	 * 设置当前音量
	 * 
	 * @param context
	 * @param index
	 */
	public static void setCurVolume(Context context, int index) {
		((AudioManager) context.getSystemService(Context.AUDIO_SERVICE))
				.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
	}

	
	
}
