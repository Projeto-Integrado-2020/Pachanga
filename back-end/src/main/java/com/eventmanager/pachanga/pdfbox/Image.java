package com.eventmanager.pachanga.pdfbox;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import be.quodlibet.boxable.utils.ImageUtils;

public class Image {

	private final BufferedImage image;

	private float width;

	private float height;

	private PDImageXObject imageXObject = null;

	// standard DPI
	private float[] dpi = { 72, 72 };

	public Image(final BufferedImage image) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}


	
    public void draw(final PDDocument doc, final PageContentStreamOptimized stream, float x, float y) throws IOException
    {
		if (imageXObject == null) {
			imageXObject = LosslessFactory.createFromImage(doc, image);
		}
		stream.drawImage(imageXObject, x, y - height, width, height);
	}

	public Image scale(float width) {
		return scaleByWidth(width);
	}

	public Image scaleByWidth(float width) {
		float factorWidth = width / this.width;
		return scale(width, this.height * factorWidth);
	}

	public Image scale(float boundWidth, float boundHeight) {
		float[] imageDimension = ImageUtils.getScaledDimension(this.width, this.height, boundWidth, boundHeight);
		this.width = imageDimension[0];
		this.height = imageDimension[1];
		return this;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}
}
