package maomo.vedio.vedioplayer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import maomo.vedio.gesture.GestureBuilderActivity;
import maomo.vedio.http.FileAsyncHttpResponseHandler;
import maomo.vedio.service.PlayerFilePath;
import maomo.vedio.service.PlayerHttpUrlWithVitamio;
import maomo.vedio.util.Canstact;
import maomo.vedio.util.FileUtil;
import maomo.vedio.util.Logger;

import org.apache.http.Header;

import android.content.Context;
import android.content.res.Configuration;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * 此处用于播放来自网络，本地，和资源文件夹中文件 目前只可以播放MP4文件
 * 
 * @author 盛月茂
 * @since 2015/5/11
 *
 */
public class VedioPlayerActivity extends BaseActivity
{

	// 控件申明
	private SurfaceView surfaceView;
	private Button btnPause, btnPlayUrl, btnStop, btnGesture;
	private SeekBar skbProgress;
	private GestureOverlayView gestureOverlayView;

	// 不同播放类的申明
	private PlayerHttpUrlWithVitamio player;
	private PlayerFilePath playerFromFile;

	// 手势库
	GestureLibrary mGestureLib;

	@Override
	/**
	 * 當Activity第一次創建時被調用
	 */
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_vedio);

		// 初始化界面佈局
		initView();

		addActivitys(VedioPlayerActivity.class.getName());// 添加到activity列表中
		Logger.e("onCreate");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		Logger.e("onSaveInstanceState");
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();

		Logger.e("onResume");
	}

	/**
	 * onConfigurationChanged the package:android.content.res.Configuration.
	 * 
	 * @param newConfig
	 *            , The new device configuration.
	 *            当设备配置信息有改动（比如屏幕方向的改变，实体键盘的推开或合上等）时，
	 *            并且如果此时有activity正在运行，系统会调用这个函数。
	 *            注意：onConfigurationChanged只会监测应用程序在AnroidMainifest.xml中通过
	 *            android:configChanges="xxxx"指定的配置类型的改动；
	 *            而对于其他配置的更改，则系统会onDestroy()当前Activity，然后重启一个新的Activity实例。
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		Logger.e("onConfigurationChanged");
		// 检测屏幕的方向：纵向或横向
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			// 当前为横屏， 在此处添加额外的处理代码

		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{

		}

	}

	private void initView()
	{

		surfaceView = (SurfaceView) findViewById(R.id.activity_vedio_surfaceView);

		btnPlayUrl = (Button) findViewById(R.id.activity_vedio_playurl);
		btnPlayUrl.setOnClickListener(new ClickEvent());

		btnPause = (Button) findViewById(R.id.activity_vedio_pause);
		btnPause.setOnClickListener(new ClickEvent());

		btnStop = (Button) findViewById(R.id.activity_vedio_stop);
		btnStop.setOnClickListener(new ClickEvent());

		btnGesture = (Button) findViewById(R.id.activity_vedio_change);
		btnGesture.setOnClickListener(new ClickEvent());

		skbProgress = (SeekBar) findViewById(R.id.activity_vedio_skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		gestureOverlayView = (GestureOverlayView) findViewById(R.id.activity_vedio_gesture);
		gestureOverlayView
				.addOnGesturePerformedListener(new GesturePerformed());

		initProptrey();

	}

	private void initProptrey()
	{
		player = new PlayerHttpUrlWithVitamio(surfaceView, skbProgress, this);
		playerFromFile = new PlayerFilePath(surfaceView, skbProgress, this);

		// 从raw中加载已经有的手势库
		mGestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures); // 注2
		if (!mGestureLib.load())
		{
			finish();
		}
	}

	/**
	 * 用于处理网络文件下载后 播放本地缓存下来的文件
	 * 
	 * @author 盛月茂
	 *
	 */
	public class FileResponse extends FileAsyncHttpResponseHandler
	{

		private FileResponse(Context context)
		{
			super(context);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, java.io.File file)
		{

		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, File file)
		{

			doFile(file);

		}

		/**
		 * 
		 * @param file
		 */
		private void doFile(File file)
		{

			if (file != null)
			{

			} else
			{

			}

			File file2 = getTargetFile();

			if (file2.exists())
			{
				if (FileUtil.hasSdcard())// 判断是否存在sd卡
				{
					String filePath = Environment.getExternalStorageDirectory()
							+ "/" + "wode.mp4";

					File file3 = new File(filePath);
					if (!file3.exists())
					{
						try
						{

							byte[] buffer = FileUtil.readFileFromSdcard(file2);
							FileUtil.writeFiletoSdcard(filePath, buffer);
							Logger.e(filePath);
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					playerFromFile.playFile(filePath);// 播放缓存文件
					deleteTargetFile();
				}

			}

		}
	};

	class GesturePerformed implements OnGesturePerformedListener
	{

		@Override
		public void onGesturePerformed(GestureOverlayView overlay,
				Gesture gesture)
		{
			// 从手势库中查询匹配的内容，匹配的结果可能包括多个相似的结果，匹配度高的结果放在最前面
			ArrayList<Prediction> predictions = mGestureLib.recognize(gesture); // 注3

			if (predictions.size() > 0)
			{
				Prediction prediction = (Prediction) predictions.get(0);
				// 匹配的手势
				if (prediction.score > 1.0)
				{

					if (prediction.name.equals("x"))
					{// 如果手势为x,则切换为安全模式
						player.playVideo(5,Canstact.VEDIO_URL_CARTOON);
						//player.playUrl(Canstact.VEDIO_URL);
					}
					if (prediction.name.equals("y"))
					{// 如果手势为y,则切换为正常模式
						player.playVideo(5, Canstact.VEDIO_URL_CARTOON);;
						//player.playUrl(Canstact.VEDIO_URL_CARTOON);;
					}

				}
			}

		}

	}

	/**
	 * 初始化內部類 點擊事件
	 */
	class ClickEvent implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.activity_vedio_pause:

				player.pause();// 暂停播放

				break;
			case R.id.activity_vedio_playurl:
				
				player.playVideo(5, Canstact.VEDIO_URL_CARTOON);// 实时播放网络视频

				//player.playUrl(Canstact.VEDIO_URL_CARTOON);
				// 缓存网络视频，当缓存成功后播放

				break;
			case R.id.activity_vedio_stop:

				player.stop();// 停止播放

				break;
			case R.id.activity_vedio_change:
				goToNextActivitys(new GestureBuilderActivity(), new Bundle());// 跳转到手势列表中去
				break;

			}

		}

	}

	@Override
	protected void onPause()
	{

		super.onPause();
		Logger.e("onPause");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		player.onActivityPauseOrDestroy();
		Logger.e("onDestroy");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Logger.e("onStop");
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		Logger.e("onRestart");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		Logger.e("onRestoreInstanceState");
	}

	/**
	 * 滑動條改變時調用
	 */
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener
	{

		long progress;// 记录滑动数值

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser)
		{
			if (player.mediaPlayer != null)
			{
				this.progress = progress * player.mediaPlayer.getDuration()
						/ seekBar.getMax();
			}

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			if (player.mediaPlayer != null)
			{
				player.mediaPlayer.seekTo(progress);
			}
			Logger.e("" + progress);

		}

	}

}
