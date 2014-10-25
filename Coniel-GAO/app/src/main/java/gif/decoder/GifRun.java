// Created by JeffMeJones@gmail.com
package gif.decoder;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;


public class GifRun implements Runnable, Callback {

	
	public Bitmap bmb;
	public GIFDecode decode;
	public int ind;
	public int gifCount;
	public SurfaceHolder mSurfaceHolder ;
	boolean surfaceExists;
    Thread t;

    public GifRun(){
        t = new Thread(this);
    }
	
	public void LoadGiff(SurfaceView v, android.content.Context theTHIS, int R_drawable)
	{
        //InputStream Raw= context.getResources().openRawResource(R.drawable.image001);
        mSurfaceHolder = v.getHolder();
        mSurfaceHolder.addCallback(this);
        decode = new GIFDecode();
        decode.read(theTHIS.getResources().openRawResource(R_drawable));
        ind = 0;
        // decode.
        gifCount = decode.getFrameCount();
        bmb = decode.getFrame(0);
        surfaceExists=true;
        t.start();
        v.setVisibility(View.VISIBLE);
	}
    public void DestroyGiff(SurfaceView v)
    {
        surfaceExists=false;
        t.interrupt();
        bmb = decode.next();
        Canvas rCanvas = mSurfaceHolder.lockCanvas();
        rCanvas.drawBitmap(bmb, 0, 0, new Paint());
        mSurfaceHolder.unlockCanvasAndPost(rCanvas);
        v.setVisibility(View.INVISIBLE);
    }

	public void run()
	{
		while (surfaceExists) {
			try {
				
					Canvas rCanvas = mSurfaceHolder.lockCanvas();
					rCanvas.drawBitmap(bmb, 0, 0, new Paint());
					mSurfaceHolder.unlockCanvasAndPost(rCanvas);
					bmb = decode.next();
					
				Thread.sleep(100);
			} catch (Exception ignored) {

			}
		}
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
		
		
		
	}

	public void surfaceCreated(SurfaceHolder holder) 
	{
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		surfaceExists=false;
	}
	
}
