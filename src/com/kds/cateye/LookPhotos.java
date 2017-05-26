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
	private int window_width, window_height;// �ؼ����
	private int state_height;// ״̬���ĸ߶�
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
        
        /** ��¼��������Ƭģʽ���ǷŴ���С��Ƭģʽ */  
        private int mode = 0;// ��ʼ״̬    
        /** ������Ƭģʽ */  
        private static final int MODE_DRAG = 1;  
        /** �Ŵ���С��Ƭģʽ */  
        private static final int MODE_ZOOM = 2;  
          
        /** ���ڼ�¼��ʼʱ�������λ�� */  
        private PointF startPoint = new PointF();  
        /** ���ڼ�¼����ͼƬ�ƶ�������λ�� */  
        private Matrix matrix = new Matrix();  
        /** ���ڼ�¼ͼƬҪ��������ʱ�������λ�� */  
        private Matrix currentMatrix = new Matrix();  
      
        /** ������ָ�Ŀ�ʼ���� */  
        private float startDis;  
        /** ������ָ���м�� */  
        private PointF midPoint;  
  
        @Override  
        public boolean onTouch(View v, MotionEvent event) {  
            /** ͨ�������㱣������λ MotionEvent.ACTION_MASK = 255 */  
            switch (event.getAction() & MotionEvent.ACTION_MASK) {  
            // ��ָѹ����Ļ  
            case MotionEvent.ACTION_DOWN:  
                mode = MODE_DRAG;  
                // ��¼ImageView��ǰ���ƶ�λ��  
                currentMatrix.set(image1.getImageMatrix());  
                startPoint.set(event.getX(), event.getY());  
                break;  
            // ��ָ����Ļ���ƶ������¼��ᱻ���ϴ���  
            case MotionEvent.ACTION_MOVE:  
                // ����ͼƬ  
                if (mode == MODE_DRAG) {  
                    float dx = event.getX() - startPoint.x; // �õ�x����ƶ�����  
                    float dy = event.getY() - startPoint.y; // �õ�x����ƶ�����  
                    // ��û���ƶ�֮ǰ��λ���Ͻ����ƶ�  
                    matrix.set(currentMatrix);  
                    matrix.postTranslate(dx, dy);  
                }  
                // �Ŵ���СͼƬ  
                else if (mode == MODE_ZOOM) {  
                    float endDis = distance(event);// ��������  
                    if (endDis > 10f) { // ������ָ��£��һ���ʱ�����ش���10  
                        float scale = endDis / startDis;// �õ����ű���  
                        matrix.set(currentMatrix);  
                        matrix.postScale(scale, scale,midPoint.x,midPoint.y);  
                    }  
                }  
                break;  
            // ��ָ�뿪��Ļ  
            case MotionEvent.ACTION_UP:  
                // �������뿪��Ļ��������Ļ�ϻ��д���(��ָ)  
            case MotionEvent.ACTION_POINTER_UP:  
                mode = 0;  
                break;  
            // ����Ļ���Ѿ��д���(��ָ)������һ������ѹ����Ļ  
            case MotionEvent.ACTION_POINTER_DOWN:  
                mode = MODE_ZOOM;  
                /** ����������ָ��ľ��� */  
                startDis = distance(event);  
                /** ����������ָ����м�� */  
                if (startDis > 10f) { // ������ָ��£��һ���ʱ�����ش���10  
                    midPoint = mid(event);  
                    //��¼��ǰImageView�����ű���  
                    currentMatrix.set(image1.getImageMatrix());  
                }  
                break;  
            }  
            image1.setImageMatrix(matrix);  
            return true;  
        }  
  
        /** ����������ָ��ľ��� */  
        private float distance(MotionEvent event) {  
            float dx = event.getX(1) - event.getX(0);  
            float dy = event.getY(1) - event.getY(0);  
            /** ʹ�ù��ɶ���������֮��ľ��� */  
            return FloatMath.sqrt(dx * dx + dy * dy);  
        }  
  
        /** ����������ָ����м�� */  
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
		
	    Bitmap bitmap = getLoacalBitmap(picpath); //�ӱ���ȡͼƬ(��cdcard�л�ȡ)  //
	    image1 .setImageBitmap(bitmap); //����Bitmap
	    match_picture(heightPixels,widthPixels);
	}
	
    /**
    * ���ر���ͼƬ
    * @param url
    * @return
    */
    public static Bitmap getLoacalBitmap(String url) {
         try {
              FileInputStream fis = new FileInputStream(url);
              return BitmapFactory.decodeStream(fis);  ///����ת��ΪBitmapͼƬ        

           } catch (FileNotFoundException e) {
              e.printStackTrace();
              return null;
         }
    }

	private void match_picture(int heightPixels,int widthPixels){
		float scaleWidth=1,scaleHeight=1; 
	    if(heightPixels==1080||widthPixels==1920){
	    	//���Bitmap�ĸߺͿ�  
	    	int bmpWidth=image1.getWidth();  
	    	int bmpHeight=image1.getHeight();  
	    	//������С����  
	    	double scale=2.0;  
	    	//��������Ҫ��С�ı���  
	    	scaleWidth=(float)(scaleWidth*scale);  
	    	scaleHeight=(float)(scaleHeight*scale);  
	    	//����resize���Bitmap����  
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
