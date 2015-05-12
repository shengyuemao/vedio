package maomo.vedio.service;

import java.io.File;
import java.nio.Buffer;
import java.util.Timer;
import java.util.TimerTask;

import maomo.vedio.util.Logger;
import maomo.vedio.util.Util;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

@SuppressLint("HandlerLeak")
public abstract class BasePlayer implements OnBufferingUpdateListener,
		OnCompletionListener, MediaPlayer.OnPreparedListener,
		SurfaceHolder.Callback, OnVideoSizeChangedListener
{
	protected int videoWidth;
	protected int videoHeight;
	public MediaPlayer mediaPlayer ;
	public SurfaceHolder surfaceHolder;
	public SeekBar skbProgress;
	protected Timer mTimer = new Timer();
	public SurfaceView surfaceView;

	protected Activity activity;

	/**
	 * 通过定时器和Handler来更新进度条
	 */
	protected TimerTask mTimerTask = new TimerTask()
	{

		@Override
		public void run()
		{
			if (mediaPlayer == null)
				return;
			try
			{
				if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false)
				{
					handleProgress.sendEmptyMessage(0);
				}
			} catch (IllegalStateException e)
			{
				Logger.e("error");
				e.printStackTrace();
			}

		}

	};

	protected Handler handleProgress = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			int position = mediaPlayer.getCurrentPosition();
			int duration = mediaPlayer.getDuration();

			if (duration > 0)
			{
				long pos = skbProgress.getMax() * position / duration;
				skbProgress.setProgress((int) pos);
			}
		}

	};

	@SuppressWarnings("deprecation")
	public BasePlayer(SurfaceView surfaceView, SeekBar skbProgress,
			Activity activity)
	{
		
		this.activity = activity;
		this.skbProgress = skbProgress;
		this.surfaceView = surfaceView;
		this.mediaPlayer = new MediaPlayer();
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTimer.schedule(mTimerTask, 0, 1000);
	}

	/**
	 * @param播放视频 有待完成
	 * @author 盛月茂
	 * 
	 */
	public abstract void play();

	/**
	 * 提供網絡地址实现播放
	 * 
	 * @param videoUrl
	 *            网络地址
	 */
	public void playUrl(String videoUrl)
	{
	};

	/**
	 * 提供文件地址实现播放
	 */
	public void playFile(String videoFile)
	{
	};

	/**
	 * 提供资源文件实现播放
	 */
	public void playRaw(int videoRaw)
	{
	};

	/**
	 * 提供File缓存文件实现播放
	 */
	public void playFile(File vedioFile)
	{
	};

	/**
	 * 提供Buffer实现播放
	 */
	public void playBuffer(Buffer vedioBuffer)
	{
	};

	/**
	 * 暂停播放
	 * 
	 * @param 有待改善
	 */
	public abstract void pause();

	/**
	 * 停止播放
	 * 
	 * @param 有待改善
	 */
	public abstract void stop();

	public void changeVideoSize( int width, int height)
	{

		videoWidth = width;
		videoHeight = height;
		if (videoHeight != 0 && videoWidth != 0)
		{

			int screenWidth = Util.getScreenWidth(activity);
			int screenHeight = Util.getScreenHeight(activity);
			float ar = videoWidth / videoHeight;
			if (screenWidth > screenHeight)
			{
				Logger.i("screenWidth > screenHeight");
				if (videoHeight >= screenHeight)
				{
					videoHeight = screenHeight;
				}

				videoWidth = (int) (videoHeight * ar);

			}

			if (screenHeight > screenWidth)
			{
				if (videoWidth >= screenWidth)
				{
					videoWidth = screenWidth;
				}

				videoHeight = (int) (videoWidth * ar);
			}			
		}
		surfaceHolder.setFixedSize(videoWidth, videoHeight);
	}
}
