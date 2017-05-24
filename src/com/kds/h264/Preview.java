package com.kds.h264;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Preview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private List<Size> mSupportedPreviewSizes;
	public Preview(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			if (mCamera != null)
				mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			mCamera.release();
			mCamera = null;
		}
	}
	 public void setCamera(Camera camera) {
	        mCamera = camera;
	        if (mCamera != null) {
	        	mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();//获取硬件所支持的尺寸
	            requestLayout();
	        }
	    }
	  public void switchCamera(Camera camera) {
		  setCamera(camera);
	       try {
	           camera.setPreviewDisplay(mHolder);
	       } catch (IOException exception) {
	    	    mCamera.release();
				mCamera = null;
	       }
	       Camera.Parameters parameters = camera.getParameters();
	       requestLayout();
//	       parameters.setPreviewSize(640, 480);
	       camera.setParameters(parameters);
	    }
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (mCamera != null){
			Camera.Parameters parameters = mCamera.getParameters();
//			parameters.setPreviewSize(640, 480);//根据硬件所支持的分辨率设置预览尺寸
			mCamera.setParameters(parameters);
			mCamera.startPreview();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null){
			mCamera.setPreviewCallback(null) ;
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	public void onPreviewFrame(byte[] arg0, Camera arg1) {
		// TODO Auto-generated method stub
		
	}

}
