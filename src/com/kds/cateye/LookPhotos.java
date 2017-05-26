package com.kds.cateye;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

public class LookPhotos extends Activity implements OnClickListener{
	private int window_width, window_height;// 控件宽度
	private int state_height;// 状态栏的高度
	private ViewTreeObserver viewTreeObserver;
	private String picpath;
	private ImageView image1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_photos);
        init_ui();
        
    }
    private final class TouchListener implements OnTouchListener {  
        
        /** 记录是拖拉照片模式还是放大缩小照片模式 */  
        private int mode = 0;// 初始状态    
        /** 拖拉照片模式 */  
        private static final int MODE_DRAG = 1;  
        /** 放大缩小照片模式 */  
        private static final int MODE_ZOOM = 2;  
          
        /** 用于记录开始时候的坐标位置 */  
        private PointF startPoint = new PointF();  
        /** 用于记录拖拉图片移动的坐标位置 */  
        private Matrix matrix = new Matrix();  
        /** 用于记录图片要进行拖拉时候的坐标位置 */  
        private Matrix currentMatrix = new Matrix();  
      
        /** 两个手指的开始距离 */  
        private float startDis;  
        /** 两个手指的中间点 */  
        private PointF midPoint;  
  
        @Override  
        public boolean onTouch(View v, MotionEvent event) {  
            /** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */  
            switch (event.getAction() & MotionEvent.ACTION_MASK) {  
            // 手指压下屏幕  
            case MotionEvent.ACTION_DOWN:  
                mode = MODE_DRAG;  
                // 记录ImageView当前的移动位置  
                currentMatrix.set(image1.getImageMatrix());  
                startPoint.set(event.getX(), event.getY());  
                break;  
            // 手指在屏幕上移动，改事件会被不断触发  
            case MotionEvent.ACTION_MOVE:  
                // 拖拉图片  
                if (mode == MODE_DRAG) {  
                    float dx = event.getX() - startPoint.x; // 得到x轴的移动距离  
                    float dy = event.getY() - startPoint.y; // 得到x轴的移动距离  
                    // 在没有移动之前的位置上进行移动  
                    matrix.set(currentMatrix);  
                    matrix.postTranslate(dx, dy);  
                }  
                // 放大缩小图片  
                else if (mode == MODE_ZOOM) {  
                    float endDis = distance(event);// 结束距离  
                    if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10  
                        float scale = endDis / startDis;// 得到缩放倍数  
                        matrix.set(currentMatrix);  
                        matrix.postScale(scale, scale,midPoint.x,midPoint.y);  
                    }  
                }  
                break;  
            // 手指离开屏幕  
            case MotionEvent.ACTION_UP:  
                // 当触点离开屏幕，但是屏幕上还有触点(手指)  
            case MotionEvent.ACTION_POINTER_UP:  
                mode = 0;  
                break;  
            // 当屏幕上已经有触点(手指)，再有一个触点压下屏幕  
            case MotionEvent.ACTION_POINTER_DOWN:  
                mode = MODE_ZOOM;  
                /** 计算两个手指间的距离 */  
                startDis = distance(event);  
                /** 计算两个手指间的中间点 */  
                if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10  
                    midPoint = mid(event);  
                    //记录当前ImageView的缩放倍数  
                    currentMatrix.set(image1.getImageMatrix());  
                }  
                break;  
            }  
            image1.setImageMatrix(matrix);  
            return true;  
        }  
  
        /** 计算两个手指间的距离 */  
        private float distance(MotionEvent event) {  
            float dx = event.getX(1) - event.getX(0);  
            float dy = event.getY(1) - event.getY(0);  
            /** 使用勾股定理返回两点之间的距离 */  
            return FloatMath.sqrt(dx * dx + dy * dy);  
        }  
  
        /** 计算两个手指间的中间点 */  
        private PointF mid(MotionEvent event) {  
            float midX = (event.getX(1) + event.getX(0)) / 2;  
            float midY = (event.getY(1) + event.getY(0)) / 2;  
            return new PointF(midX, midY);  
        }  
  
    }  
	
	private void init_ui(){ 
		picpath = getIntent().getStringExtra("menustring");
		File file = new File(picpath);
		if(file.exists()==false){
			return ;
		}
		image1= (ImageView) findViewById(R.id.imageLookId);
		image1.setOnTouchListener(new TouchListener());
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int heightPixels = dm.heightPixels;
		int widthPixels = dm.widthPixels;

		image1.setMaxWidth(widthPixels);
		image1.setMaxHeight(heightPixels);
		
	    Bitmap bitmap = getLoacalBitmap(picpath); //从本地取图片(在cdcard中获取)  //
	    image1 .setImageBitmap(bitmap); //设置Bitmap
	    match_picture(heightPixels,widthPixels);
	}
	
    /**
    * 加载本地图片
    * @param url
    * @return
    */
    public static Bitmap getLoacalBitmap(String url) {
         try {
              FileInputStream fis = new FileInputStream(url);
              return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

           } catch (FileNotFoundException e) {
              e.printStackTrace();
              return null;
         }
    }

	private void match_picture(int heightPixels,int widthPixels){
		float scaleWidth=1,scaleHeight=1; 
	    if(heightPixels==1080||widthPixels==1920){
	    	//获得Bitmap的高和宽  
	    	int bmpWidth=image1.getWidth();  
	    	int bmpHeight=image1.getHeight();  
	    	//设置缩小比例  
	    	double scale=2.0;  
	    	//计算出这次要缩小的比例  
	    	scaleWidth=(float)(scaleWidth*scale);  
	    	scaleHeight=(float)(scaleHeight*scale);  
	    	//产生resize后的Bitmap对象  
	    	Matrix matrix=new Matrix();  
	    	matrix.postScale(scaleWidth, scaleHeight);  
	    	image1.setImageMatrix(matrix);
	    }
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		default:
			break;
		}
	}
    private void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
