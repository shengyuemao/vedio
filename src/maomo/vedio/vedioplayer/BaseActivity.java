package maomo.vedio.vedioplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity
{

	protected mBaseApplication baseApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 初始化mBaseApplication
		baseApplication = (mBaseApplication) getApplicationContext();
	}

	/**
	 * 添加当前activity到activity列表中去
	 * 
	 * @param key
	 *            key值
	 */
	public void addActivitys(String key)
	{
		baseApplication.addActivity(key, this);
	}
	
	public void goToNextActivitys(Activity nextActivty,Bundle bundle){
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setClass(this, nextActivty.getClass());
		startActivity(intent);
		
	}

}
