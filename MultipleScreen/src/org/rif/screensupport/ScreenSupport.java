package org.rif.screensupport;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class ScreenSupport extends Activity {
  
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView lbldevice = (TextView) findViewById(R.id.device);
        
        String device_name = android.os.Build.MANUFACTURER;
        String device_model = android.os.Build.MODEL;
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        
        int width = 0;
        int height = 0;
        
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        if (android.os.Build.VERSION.SDK_INT < 13){		
			width = display.getWidth();  // deprecated
			height = display.getHeight();  // deprecated
		}else{
			Point size = new Point();
			display.getSize(size);		
			width = size.x;
			height = size.y;
		}
        lbldevice.setText("Device Name: " + device_name 
        		+ "\nDevice Model: " + device_model
        		+ "\nDevice Width: " + width
        		+ "\nDevice Height: " + height
        		+ "\nScreen size: " + screenInches  + " (inches)"        		
        		+ "\nScreen density: " + getDensity()
        		+ "\nScreen resoultion: " +  getScreenResolution());
    }
    
    @SuppressLint("NewApi")
	private String getScreenResolution(){
    	DisplayMetrics metrics = getResources().getDisplayMetrics();		
		float ldpi = 0.75f;
		float mdpi = 1.0f;
		float hdpi = 1.5f;
		float xhdpi = 2.0f;
		float xxdpi = 3.0f;
		
		int width = 0;
		int height = 0;		
		Display display = getWindowManager().getDefaultDisplay();		
		int api = android.os.Build.VERSION.SDK_INT;
		int orient = getResources().getConfiguration().orientation;
		if (api < 13){		
			width = display.getWidth();  // deprecated
			height = display.getHeight();  // deprecated
		}else{
			Point size = new Point();
			display.getSize(size);		
			width = size.x;
			height = size.y;
		}
		
		if (orient == Configuration.ORIENTATION_LANDSCAPE){
			int temp_width = width;
			width = height;
			height = temp_width;
		}
		
		Log.v("SCREEN RESOLUTION", "width: "+width+ " height: "+height);
		
		String screen = "";
		
		if (metrics.density == ldpi){
			if (width == 240 && height == 320)
				screen = "small";
			else if (width == 240 && height == 400
					|| width == 240 && height == 432)
				screen = "normal";
			else if (width == 480 && height == 800
					|| width == 480 && height == 854)
				screen = "large";
			else if (width == 1024 && height == 600)
				screen = "xlarge";
		} else if (metrics.density == mdpi){			
			if (width == 320 && height == 480)
				screen = "normal";
			else if (width == 480 && height == 800
					|| width == 480 && height == 854
					|| width == 600 && height == 1024)
				screen = "large";
			else if ((width > 1200 || width < 1300 && height > 700 || height < 800))
				screen = "xlarge";
		} else if (metrics.density == hdpi){	
			if (width == 480 && height == 640)
				screen = "small";
			else if ((width < 500 && height > 700 || height < 800)
					|| width == 480 && height == 854)
//					|| width == 600 && height == 1024)
				screen = "normal";
			else if (width == 1536 && height == 1152
					|| width == 1920 && height == 1152
					|| width == 1920 && height == 1200)
				screen = "xlarge";
			else
				screen = "large";
		} else if (metrics.density == xhdpi){			
			if (width == 640 && height == 960
					|| width == 720 && height == 1280)
				screen = "normal";
			else if (width == 2084 && height == 1536
					|| width == 2560 && height == 1536
					|| width == 2560 && height == 1600)
				screen = "xlarge";
			else
				screen = "xlarge";
		}
		return screen;
    }
    
    private String getDensity(){
    	DisplayMetrics metrics = getResources().getDisplayMetrics();
		float ldpi = 0.75f;
		float mdpi = 1.0f;
		float hdpi = 1.5f;
		float xhdpi = 2.0f;
		float xxdpi = 3.0f;
		String density = "";
		if (metrics.density == ldpi){
			density = "ldpi";
		} else if (metrics.density == mdpi){			
			density = "mdpi";
		} else if (metrics.density == hdpi){	
			density = "hdpi";
		} else if (metrics.density == xhdpi){			
			density = "xhdpi";
		} else if (metrics.density == xxdpi){
			density = "xxdpi";
		}
		return density;
    }
}