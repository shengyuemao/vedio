package maomo.vedio.vedioplayer;

import java.util.ArrayList;

import maomo.vedio.launcher.BaseActivity;
import maomo.vedio.service.PlayerHttpUrl;
import maomo.vedio.util.Canstact;
import maomo.vedio.util.Logger;
import maomo.vedio.vediobean.VedioModel;
import maomo.vedio.vediolist.VedioList;
import maomo.vedio.vediolist.VedioListIterator;
import android.content.res.Configuration;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

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
	private ImageButton btnNext, btnPlayUrl;
	private SeekBar skbProgress;
	private GestureOverlayView gestureOverlayView;

	// 不同播放类的申明
	private PlayerHttpUrl player;

	// 手势库
	GestureLibrary mGestureLib;
	
	//數據
	private VedioListIterator vedioListIterator;
	private VedioList vedioList;

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

		btnPlayUrl = (ImageButton) findViewById(R.id.activity_vedio_play_pause);
		btnPlayUrl.setOnClickListener(new ClickEvent());

		btnNext = (ImageButton) findViewById(R.id.activity_vedio_play_next);
		btnNext.setOnClickListener(new ClickEvent());

		skbProgress = (SeekBar) findViewById(R.id.activity_vedio_skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		gestureOverlayView = (GestureOverlayView) findViewById(R.id.activity_vedio_gesture);
		gestureOverlayView
				.addOnGesturePerformedListener(new GesturePerformed());

		initProptrey();
		initData();
	}

	private void initProptrey()
	{
		player = new PlayerHttpUrl(surfaceView, skbProgress, this);
		
		// 从raw中加载已经有的手势库
		mGestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures); // 注2
		if (!mGestureLib.load())
		{
			finish();
		}
	}
	
	private void initData(){
		VedioModel vedioModel = new VedioModel();
		VedioModel vedioModel1 = new VedioModel();
		VedioModel vedioModel2 = new VedioModel();
		VedioModel vedioModel3 = new VedioModel();
		vedioList = new VedioList();
		vedioListIterator = new VedioListIterator(vedioList);
		
		vedioModel.setVedioUrl(Canstact.VEDIO_URL);
		vedioModel1.setVedioUrl(Canstact.VEDIO_URL_CARTOON);
		vedioModel2.setVedioUrl(Canstact.VEDIO_URL);
		vedioModel3.setVedioUrl(Canstact.VEDIO_URL_CARTOON);
		
		vedioListIterator.insertBefore(vedioModel3);
		vedioListIterator.insertBefore(vedioModel2);
		vedioListIterator.insertBefore(vedioModel1);
		vedioListIterator.insertBefore(vedioModel);
		
		play(vedioListIterator.getCurrent().getVedioUrl().getVedioUrl());
	}

	/**
	 * 處理手勢事件
	 * @author 盛月茂
	 *
	 */
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
						player.playUrl(Canstact.VEDIO_URL);
					}
					if (prediction.name.equals("y"))
					{// 如果手势为y,则切换为正常模式
						player.playUrl(Canstact.VEDIO_URL_CARTOON);;
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
		
			case R.id.activity_vedio_play_pause:
				Toast.makeText(v.getContext(), vedioListIterator.getCurrent().getVedioUrl().getVedioUrl(), Toast.LENGTH_LONG).show();
				play(vedioListIterator.getCurrent().getVedioUrl().getVedioUrl());
				
				break;
			case R.id.activity_vedio_play_next:
				
				vedioListIterator.nextLink();
				Toast.makeText(v.getContext(), vedioListIterator.getCurrent().getVedioUrl().getVedioUrl(), Toast.LENGTH_LONG).show();
				player.playUrl(vedioListIterator.getCurrent().getVedioUrl().getVedioUrl());

				break;

			}

		}
		

	}
	
	/**
	 * 播放视频
	 * @param path
	 */
	private void play(String path) {
		player.playUrl(path);
		if(!player.isPlaying()){				
			btnPlayUrl.setImageResource(R.drawable.mediacontroller_pause);
		}else{
			player.pause();
			btnPlayUrl.setImageResource(R.drawable.mediacontroller_play);
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
		//player.onActivityPauseOrDestroy();
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
				player.mediaPlayer.seekTo((int)progress);
			}
			Logger.e("" + progress);

		}

	}

}
