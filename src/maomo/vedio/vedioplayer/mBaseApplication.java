package maomo.vedio.vedioplayer;

import java.util.HashMap;
import java.util.Iterator;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class mBaseApplication extends Application {
	
	public HashMap<String, BaseActivity> activitys;

	// ---------------------------

	private static mBaseApplication mInstance = null;
	
	public  NotificationManager nm;

	// BMapManager mBMapManager = null;

	public mBaseApplication() {

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mInstance = this;
		
		nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		activitys = new HashMap<String, BaseActivity>();

		

	}

	public static mBaseApplication getInstance() {
		return mInstance;
	}

	public HashMap<String, BaseActivity> getActivitys() {
		return activitys;
	}

	public void addActivity(String tag, BaseActivity activity) {
		if (activitys != null)
			activitys.put(tag, activity);

	}

	public BaseActivity getActivity(String activityname) {
		if (activitys != null)
			return activitys.get(activityname);

		return null;
	}

	public void finishActivity(String activityname) {
		if (activitys != null && activitys.size() >= 1) {

			activitys.get(activityname).finish();

		}
	}

	@SuppressWarnings("rawtypes")
	public void finishAll() {
		if (activitys != null && activitys.size() >= 1) {
			Iterator iter = activitys.keySet().iterator();
			while (iter.hasNext()) {
				Object key = iter.next();
				activitys.get(key).finish();
			}

		}
	}

	/**
	 *  显示状态栏
	 * @param icon
	 * @param tickertext
	 * @param title
	 * @param content
	 */
	@SuppressWarnings("deprecation")
	public void showNotification(int icon,String tickertext,String title,String content){
        //设置一个唯一的ID，随便设置
 
        //Notification管理器
        Notification notification=new Notification(icon,tickertext,System.currentTimeMillis());
        //后面的参数分别是显示在顶部通知栏的小图标，小图标旁的文字（短暂显示，自动消失）系统当前时间（不明白这个有什么用）
        notification.defaults=Notification.DEFAULT_ALL; 
        //这是设置通知是否同时播放声音或振动，声音为Notification.DEFAULT_SOUND
        //振动为Notification.DEFAULT_VIBRATE;
        //Light为Notification.DEFAULT_LIGHTS，在我的Milestone上好像没什么反应
        //全部为Notification.DEFAULT_ALL
        //如果是振动或者全部，必须在AndroidManifest.xml加入振动权限
        PendingIntent pt=PendingIntent.getActivity(this, 0, new Intent(this,BaseActivity.class), 0);
        //点击通知后的动作，这里是转回main 这个Acticity
        notification.setLatestEventInfo(this,title,content,pt);
        nm.notify(R.string.hello_world, notification);
 
    }

	
}
