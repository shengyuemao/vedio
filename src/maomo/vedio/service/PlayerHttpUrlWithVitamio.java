package maomo.vedio.service;

import android.media.MediaPlayer.OnCompletionListener;
import android.view.SurfaceHolder;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;

public class PlayerHttpUrlWithVitamio implements OnBufferingUpdateListener,OnCompletionListener,OnPreparedListener,OnVideoSizeChangedListener,SurfaceHolder.Callback{

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrepared(MediaPlayer mp)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCompletion(android.media.MediaPlayer mp)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent)
	{
		// TODO Auto-generated method stub
		
	}
	
}
{

}
