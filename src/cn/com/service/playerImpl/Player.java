package cn.com.service.playerImpl;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.Tool.Logger.Logger;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

@SuppressLint({ "HandlerLeak", "NewApi" })
public class Player implements OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, SurfaceHolder.Callback,
		OnVideoSizeChangedListener {

	private int videoWidth;
	private int videoHeight;
	public MediaPlayer mediaPlayer = new MediaPlayer();
	private SurfaceHolder surfaceHolder;
	private SeekBar skbProgress;
	private Timer mTimer = new Timer();
	private SurfaceView surfaceView;

	@SuppressWarnings("deprecation")
	public Player(SurfaceView surfaceView, SeekBar skbProgress) {
		this.skbProgress = skbProgress;
		this.surfaceView = surfaceView;
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTimer.schedule(mTimerTask, 0, 1000);
	}

	/**
	 * 通过定时器和Handler来更新进度条
	 */
	TimerTask mTimerTask = new TimerTask() {

		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
				handleProgress.sendEmptyMessage(0);
			}

		}

	};

	Handler handleProgress = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int position = mediaPlayer.getCurrentPosition();
			int duration = mediaPlayer.getDuration();

			if (duration > 0) {
				long pos = skbProgress.getMax() * position / duration;
				skbProgress.setProgress((int) pos);
			}
		}

	};

	/**
	 * @param播放视频 有待完成
	 * @author 盛月茂
	 * 
	 */
	public void play() {
		mediaPlayer.start();
	}

	/**
	 * 提供網絡地址实现播放
	 * 
	 * @param videoUrl
	 *            网络地址
	 */
	public void playUrl(String videoUrl) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(videoUrl);
			mediaPlayer.prepare();// prepare之後自動播放
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 暂停播放
	 * 
	 * @param 有待改善
	 */
	public void pause() {
		mediaPlayer.pause();
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		try {
			mediaPlayer.setDisplay(holder);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {

			Logger.e("error");

		}

		Logger.e("surface created");

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// 当surface改变时调用
		Logger.e("surfaceChanged");
		videoWidth = mediaPlayer.getVideoHeight();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight != 0 && videoWidth != 0) {

			int width1 = width;
			int height1 = height;
			float boxWidth = width1;
			float boxHeight = height1;

			float wr = boxWidth / videoWidth;
			float hr = boxHeight / videoHeight;
			float ar = videoWidth / videoHeight;
           
			if (wr > hr) {
				width1 = (int) (boxWidth * ar);
			} else {
				height1 = (int) (boxHeight / ar);
			}

			surfaceHolder.setFixedSize(width1, height1);
			
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// 当surface销毁时调用
		Logger.e("surfaceDestroyed");
	}

	@Override
	/**
	 * 通过onPrepared播放
	 * @param mp MediaPlayer 播放器
	 */
	public void onPrepared(MediaPlayer mp) {
		videoWidth = mediaPlayer.getVideoHeight();
		videoHeight = mediaPlayer.getVideoHeight();
		if (videoHeight != 0 && videoWidth != 0) {

			int width1 = surfaceView.getWidth();
			int height1 = surfaceView.getHeight();
			float boxWidth = width1;
			float boxHeight = height1;

			float wr = boxWidth / videoWidth;
			float hr = boxHeight / videoHeight;
			float ar = videoWidth / videoHeight;
           
			if (wr > hr) {
				width1 = (int) (boxWidth * ar);
			} else {
				height1 = (int) (boxHeight / ar);
			}

			surfaceHolder.setFixedSize(width1, height1);

			mp.start();

		}
		Logger.e("onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// 播放完成时调用
		Logger.e("onCompletion");

	}

	@Override
	/**
	 * 播放Buffer改变时调用
	 * @param mp
	 * @param percent
	 */
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		skbProgress.setSecondaryProgress(percent);
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		/*int width1 = surfaceView.getWidth();
		int height1 = surfaceView.getHeight();
		float boxWidth = width1;
		float boxHeight = height1;

		float wr = boxWidth / width;
		float hr = boxHeight / height;
		float ar = width / height;

		if (wr > hr) {
			width1 = (int) (boxHeight * ar);
		} else {
			height1 = (int) (boxWidth / ar);
		}

		surfaceHolder.setFixedSize(width1, height1);
		Logger.e("onVideoSizeChanged");*/
	}

}
