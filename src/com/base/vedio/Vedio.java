package com.base.vedio;

import cn.com.Tool.Canstact.Canstact;
import cn.com.Tool.Logger.Logger;
import cn.com.service.playerImpl.Player;
import android.support.v7.app.ActionBarActivity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

public class Vedio extends ActionBarActivity {
	private SurfaceView surfaceView;
	private Button btnPause, btnPlayUrl, btnStop;
	private SeekBar skbProgress;
	private Player player;

	@Override
	/**
	 * 當Activity第一次創建時被調用
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_vedio);

		// 初始化界面佈局
		initView();
		Logger.e("onCreate");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Logger.e("onSaveInstanceState");
	}
	
	@Override
	protected void onResume() {
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
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Logger.e("onConfigurationChanged");
		// 检测屏幕的方向：纵向或横向
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 当前为横屏， 在此处添加额外的处理代码
			Logger.e(""+progress_1);
			
			
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			Logger.e(""+progress_1);
			
		}
		
		// 检测实体键盘的状态：推出或者合上
		if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
			// 实体键盘处于推出状态，在此处添加额外的处理代码
		} else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
			// 实体键盘处于合上状态，在此处添加额外的处理代码
		}
	}

	private void initView() {
		surfaceView = (SurfaceView) findViewById(R.id.activity_vedio_surfaceView);

		btnPlayUrl = (Button) findViewById(R.id.activity_vedio_playurl);
		btnPlayUrl.setOnClickListener(new ClickEvent());

		btnPause = (Button) findViewById(R.id.activity_vedio_pause);
		btnPause.setOnClickListener(new ClickEvent());

		btnStop = (Button) findViewById(R.id.activity_vedio_stop);
		btnStop.setOnClickListener(new ClickEvent());

		skbProgress = (SeekBar) findViewById(R.id.activity_vedio_skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		player = new Player(surfaceView, skbProgress);

	}

	/**
	 * 初始化內部類 點擊事件
	 */
	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.activity_vedio_pause:
				player.pause();
				break;
			case R.id.activity_vedio_playurl:
				String url = Canstact.VEDIO_URL;
				player.playUrl(url);
				break;
			case R.id.activity_vedio_stop:
				player.stop();
				break;

			}

		}

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Logger.e("onPause");
	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Logger.e("onDestroy");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Logger.e("onStop");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Logger.e("onRestart");
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Logger.e("onRestoreInstanceState");
	}

	int progress_1;

	/**
	 * 滑動條改變時調用
	 */
	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {

		int progress;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (player.mediaPlayer != null) {
				this.progress = progress * player.mediaPlayer.getDuration()
						/ seekBar.getMax();
				progress_1 = progress;
			}

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			//
			player.mediaPlayer.seekTo(progress);

		}

	}

}
