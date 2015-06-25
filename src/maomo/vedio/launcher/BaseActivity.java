package maomo.vedio.launcher;

import maomo.vedio.vedioapplication.mBaseApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {

	protected mBaseApplication baseApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
	public void addActivitys(String key) {
		baseApplication.addActivity(key, this);
	}

	/**
	 * 跳转activity
	 * 
	 * @param nextActivty
	 * @param bundle
	 */
	public void goToNextActivitys(Activity nextActivty, Bundle bundle) {
		Intent intent = new Intent();
		if (bundle != null)
			intent.putExtras(bundle);
		intent.setClass(this, nextActivty.getClass());
		startActivity(intent);

	}

}
