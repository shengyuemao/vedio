package maomo.vedio.launcher;

import maomo.vedio.ldrawer.ActionBarDrawerToggle;
import maomo.vedio.ldrawer.DrawerArrowDrawable;
import maomo.vedio.vedioplayer.R;
import maomo.vedio.vedioplayer.VedioPlayerActivity;
import maomo.vedio.vedioplayer.VedioPlayerWithVitmioActivity;
import maomo.vedio.vedioplayer.VideoViewBuffer;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("ResourceAsColor")
public class MainActivity extends BaseActivity
{

	    private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;
	    private DrawerArrowDrawable drawerArrow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar ab = this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
		   mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mDrawerList = (ListView) findViewById(R.id.navdrawer);


	        drawerArrow = new DrawerArrowDrawable(this) {
	            @Override
	            public boolean isLayoutRtl() {
	                return false;
	            }
	        };
	        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
	            drawerArrow, R.string.drawer_open,
	            R.string.drawer_close) {

	            public void onDrawerClosed(View view) {
	                super.onDrawerClosed(view);
	                invalidateOptionsMenu();
	            }

	            public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                invalidateOptionsMenu();
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	        mDrawerToggle.syncState();


	        String[] values = new String[]{
	            "播放网络视频",
	            "原生播放",
	            "vitmio播放"
	        };
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1, android.R.id.text1, values);
	        mDrawerList.setAdapter(adapter);
	        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @SuppressLint("ResourceAsColor")
				@Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                                    int position, long id) {
	                switch (position) {
	                    case 0:
	                        goToNextActivitys(new VedioPlayerActivity(), null);
	                        break;
	                    case 1:
	                        goToNextActivitys(new VedioPlayerWithVitmioActivity(), null);
	                        break;
	                    case 2:
	                        goToNextActivitys(new VideoViewBuffer(), null);
	                        break;
	                }

	            }
	        });
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		 if (item.getItemId() == android.R.id.home) {
	            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
	                mDrawerLayout.closeDrawer(mDrawerList);
	            } else {
	                mDrawerLayout.openDrawer(mDrawerList);
	            }
	        }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		 mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
}
