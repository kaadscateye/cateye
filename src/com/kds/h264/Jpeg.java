package com.kds.h264;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.util.Log;

public class Jpeg {
	private boolean photoState = false;

	public void settakePhoto(boolean photoState) {
		this.photoState = photoState;
	}

	public boolean gettakePhoto() {
		return this.photoState;
	}

	public String GetPicturePath() {
		String filepath = "";

		File sdRoot = Environment.getExternalStorageDirectory(); // 系统路径
		String dir = "/kdsCamera/"; // 文件夹名
		File mkDir = new File(sdRoot, dir);
		if (!mkDir.exists()) {
			Log.e("photo", "create kdsCamera ok" + sdRoot + dir);
			mkDir.mkdirs(); // 目录不存在，则创建
		} else {
//			Log.e("photo", "create kdsCamera failed" + sdRoot + dir);
		}
		filepath = sdRoot + dir ;
		return filepath;
	}

	public void NV21Tojpeg(byte[] data, Camera camera, int width, int height) {

		// 保存一张照片
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sDateFormat.format(new java.util.Date());
		String Name = "IMG" + date + ".jpg"; // jpeg文件名定义
		
		String filepath = GetPicturePath();
		File pictureFile = new File(filepath+Name);
		if (!pictureFile.exists()) {
			Log.e("photo", filepath);
			try {
				pictureFile.createNewFile();

				FileOutputStream filecon = new FileOutputStream(pictureFile);

				YuvImage image = new YuvImage(data, ImageFormat.NV21, width,
						height, null); // 将NV21 data保存成YuvImage
				// 图像压缩
				image.compressToJpeg(
						new Rect(0, 0, image.getWidth(), image.getHeight()),
						70, filecon); // 将NV21格式图片，以质量70压缩成Jpeg，并得到JPEG数据流
				Log.e("take photo", "is ok ");

				// YuvImage image = new YuvImage(bytes, ImageFormat.NV21, width,
				// height, null); //ImageFormat.NV21 640 480
				// ByteArrayOutputStream outputSteam = new
				// ByteArrayOutputStream();
				// image.compressToJpeg(new Rect(0, 0, image.getWidth(),
				// image.getHeight()), 70, outputSteam); //
				// 将NV21格式图片，以质量70压缩成Jpeg，并得到JPEG数据流
				// byte[] jpegData = outputSteam.toByteArray();

			} catch (IOException e) {
				Log.e("take photo", "failed  ");
				e.printStackTrace();
			}
		}
	}

	public void takephoto(byte[] mData, Camera camera) {
		// 保存一张照片
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sDateFormat.format(new java.util.Date());
		String Name = "IMG" + date + ".jpg"; // jpeg文件名定义
		
		String filepath = GetPicturePath();
		File pictureFile = new File(filepath+Name);
		if (!pictureFile.exists()) {

			try {
				pictureFile.createNewFile();
				FileOutputStream filecon = new FileOutputStream(pictureFile);
				
				Size size = camera.getParameters().getPreviewSize(); // 获取预览大小
				final int w = size.width; // 宽度
				final int h = size.height;
				final YuvImage image = new YuvImage(mData, ImageFormat.NV21, w, h,
						null);

				ByteArrayOutputStream os = new ByteArrayOutputStream(mData.length);
				if (!image.compressToJpeg(new Rect(0, 0, w, h), 80, os)) {
					return;
				}
				byte[] data = os.toByteArray();
				
				filecon.write(data);
				filecon.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
