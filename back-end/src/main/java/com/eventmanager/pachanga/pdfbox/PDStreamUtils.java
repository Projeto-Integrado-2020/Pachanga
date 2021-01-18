package com.eventmanager.pachanga.pdfbox;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

/**
 * <p>
 * Utility methods for {@link PDPageContentStream}
 * </p>
 * 
 * @author hstimac
 * @author mkuehne
 *
 */
public final class PDStreamUtils {

	private PDStreamUtils() {
	}

	public static void rect(final PageContentStreamOptimized stream, final float x, final float y, final float width,
			final float height, final Color color) {
		try {
			stream.setNonStrokingColor(color);
			// negative height because we want to draw down (not up!)
			stream.addRect(x, y, width, -height);
			stream.fill();
		} catch (final IOException e) {
			throw new IllegalStateException("Unable to draw rectangle", e);
		}
	}


	public static void setLineStyles(final PageContentStreamOptimized stream, final com.eventmanager.pachanga.pdfbox.LineStyle border) throws IOException {
		stream.setStrokingColor(border.getColor());
		stream.setLineWidth(border.getWidth());
		stream.setLineCapStyle(0);
		if (border.getDashArray() != null) {
			stream.setLineDashPattern(border.getDashArray(), border.getDashPhase());
		} else {
			stream.setLineDashPattern(new float[] {}, 0.0f);
		}
	}
}
