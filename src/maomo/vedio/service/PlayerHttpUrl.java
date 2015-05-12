package maomo.vedio.service;

import java.io.IOException;

import maomo.vedio.util.Logger;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

@SuppressLint(
{ "HandlerLeak", "NewApi" })
public class PlayerHttpUrl extends BasePlayer
{

	public PlayerHttpUrl(SurfaceView surfaceView, SeekBar skbProgress,
			Activity activity)
	{
		super(surfaceView, skbProgress, activity);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param播放视频 有待完成
	 * @author 盛月茂
	 * 
	 */
	public void play()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.start();
		}

	}

	/**
	 * 提供網絡地址实现播放
	 * 
	 * @param videoUrl
	 *            网络地址
	 */
	public void playUrl(String videoUrl)
	{
		try
		{
			mediaPlayer.reset();
			mediaPlayer.setDataSource(videoUrl);
			mediaPlayer.prepare();// prepare之後自動播放
			mediaPlayer.start();
		} catch (IllegalArgumentException e)
		{
			Logger.e("error1");
			e.printStackTrace();
		} catch (IllegalStateException e)
		{
			Logger.e("error2");
			e.printStackTrace();
		} catch (IOException e)
		{
			Logger.e("error3");
			e.printStackTrace();
		}
	}

	/**
	 * 暂停播放
	 * 
	 * @param 有待改善
	 */
	public void pause()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.pause();
		}

	}

	public void stop()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			mTimerTask.cancel();
			mTimer.cancel();
			
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		try
		{
			mediaPlayer.setDisplay(holder);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e)
		{

			Logger.e("error");

		}

		Logger.e("surface created");

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		// 当surface改变时调用
		Logger.e("surfaceChanged");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// 当surface销毁时调用
		Logger.e("surfaceDestroyed");
	}

	@Override
	/**
	 * 通过onPrepared播放
	 * @param mp MediaPlayer 播放器
	 */
	public void onPrepared(MediaPlayer mp)
	{

		mp.start();

		Logger.e("onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp)
	{
		// 播放完成时调用
		Logger.e("onCompletion");

	}

	@Override
	/**
	 * 播放Buffer改变时调用
	 * @param mp
	 * @param percent
	 */
	public void onBufferingUpdate(MediaPlayer mp, int percent)
	{
		skbProgress.setSecondaryProgress(percent);
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
	{
		Logger.e("width = " + width + ";" + "height = " + height);
		changeVideoSize(width, height);
	}

}
