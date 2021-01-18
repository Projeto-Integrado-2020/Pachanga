package com.eventmanager.pachanga.pdfbox;

import java.awt.Color;


public class LineStyle {

	private final Color color;

	private final float width;

	private float[] dashArray;

	private float dashPhase;

	public LineStyle(final Color color, final float width) {
		this.color = color;
		this.width = width;
	}

	public Color getColor() {
		return color;
	}

	public float getWidth() {
		return width;
	}

	public float[] getDashArray() {
		return dashArray;
	}

	public float getDashPhase() {
		return dashPhase;
    }




}
