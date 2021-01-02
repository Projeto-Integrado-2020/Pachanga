package com.eventmanager.pachanga.pdfbox;

import java.awt.Color;
import java.util.Objects;


public class LineStyle {

	private final Color color;

	private final float width;

	private float[] dashArray;

	private float dashPhase;

	public LineStyle(final Color color, final float width) {
		this.color = color;
		this.width = width;
	}

	public static LineStyle produceDotted(final Color color, final int width) {
		final LineStyle line = new LineStyle(color, width);
		line.dashArray = new float[] { 1.0f };
		line.dashPhase = 0.0f;

		return line;
	}


	public static LineStyle produceDashed(final Color color, final int width) {
		return produceDashed(color, width, new float[] { 5.0f }, 0.0f);
	}

	public static LineStyle produceDashed(final Color color, final int width, final float[] dashArray,
			final float dashPhase) {
		final LineStyle line = new LineStyle(color, width);
		line.dashArray = dashArray;
		line.dashPhase = dashPhase;

		return line;
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

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.color);
        hash = 89 * hash + Float.floatToIntBits(this.width);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LineStyle other = (LineStyle) obj;
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        if (Float.floatToIntBits(this.width) != Float.floatToIntBits(other.width)) {
            return false;
        }
        return true;
    }


}
