package com.eventmanager.pachanga.pdfbox;

import org.apache.pdfbox.pdmodel.PDPage;

public class ImageCell<T extends PDPage> extends Cell<T> {

	private Image img;
	
	private final HorizontalAlignment align;
	
	private final VerticalAlignment valign;

	ImageCell(Row<T> row, float width, Image image, boolean isCalculated) {
		super(row, width, null, isCalculated);
		this.img = image;
		if(image.getWidth() > getInnerWidth()){
			scaleToFit();
		}
		this.align = HorizontalAlignment.LEFT;
		this.valign = VerticalAlignment.TOP;
	}

	public void scaleToFit() {
		img = img.scale(getInnerWidth());
	}

	ImageCell(Row<T> row, float width, Image image, boolean isCalculated, HorizontalAlignment align,
			VerticalAlignment valign) {
		super(row, width, null, isCalculated, align, valign);
		this.img = image;
		if(image.getWidth() > getInnerWidth()){
			scaleToFit();
		}
		this.align = align;
		this.valign = valign;
	}

	@Override
	public float getTextHeight() {
		return img.getHeight();
	}

	@Override
	public float getHorizontalFreeSpace() {
		return getInnerWidth() - img.getWidth();
	}
	
	@Override
	public float getVerticalFreeSpace() {
		return getInnerHeight() - img.getHeight();
	}

	public Image getImage() {
		return img;
	}
}
