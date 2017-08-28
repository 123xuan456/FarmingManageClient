package com.ruicheng.farmingmanageclient.bean;

import android.graphics.Bitmap;

import com.ruicheng.farmingmanageclient.util.BimpHandler;

import java.io.IOException;
import java.io.Serializable;


public class CameraImage implements Serializable {
	
	//
	public String imageId;
	
	public String thumbnailPath;
	//图片存储的路�?
	public String imagePath;
	//
	private Bitmap bitmap;
	
	public boolean isSelected = false;
	
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Bitmap getBitmap() {		
		if(bitmap == null){
			try {
				bitmap = BimpHandler.revitionImageSize(imagePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	
	
}
