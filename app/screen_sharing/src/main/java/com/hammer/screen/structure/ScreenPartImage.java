package com.hammer.screen.structure;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ScreenPartImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient BufferedImage img;

	private int imgHash;
	
	private byte[] imgBytes;

	public ScreenPartImage(BufferedImage img, int hash) {
		this.img=img;
		this.imgHash = hash;
	}
	
	
	void setImg(BufferedImage img) {
		this.img = img;
	}
	
	public BufferedImage getImg() {
		return img;
	}
	
	void setImgBytes(byte[] imgBytes) {
		this.imgBytes = imgBytes;
	}
	
	public byte[] getImgBytes() {
		return imgBytes;
	}

	@Override
	public int hashCode() {
		return imgHash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScreenPartImage other = (ScreenPartImage) obj;
		if (imgHash != other.imgHash)
			return false;
		return true;
	}

	
}
