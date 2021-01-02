package com.eventmanager.pachanga.pdfbox;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import be.quodlibet.boxable.line.LineStyle;

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

	public static void write(final PageContentStreamOptimized stream, final String text, final PDFont font,
			final float fontSize, final float x, final float y, final Color color) {
		try {
			stream.setFont(font, fontSize);
			// we want to position our text on his baseline
			stream.newLineAt(x, y - FontUtils.getDescent(font, fontSize) - FontUtils.getHeight(font, fontSize));
			stream.setNonStrokingColor(color);
			stream.showText(text);
		} catch (final IOException e) {
			throw new IllegalStateException("Unable to write text", e);
		}
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

	public static void rectFontMetrics(final PageContentStreamOptimized stream, final float x, final float y,
			final PDFont font, final float fontSize) {
		// height
		PDStreamUtils.rect(stream, x, y, 3, FontUtils.getHeight(font, fontSize), Color.BLUE);
		// ascent
		PDStreamUtils.rect(stream, x + 3, y, 3, FontUtils.getAscent(font, fontSize), Color.CYAN);
		// descent
		PDStreamUtils.rect(stream, x + 3, y - FontUtils.getHeight(font, fontSize), 3, FontUtils.getDescent(font, 14),
				Color.GREEN);
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
