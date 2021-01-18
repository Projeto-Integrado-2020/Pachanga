package com.eventmanager.pachanga.pdfbox;

import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.font.PDFont;

public final class FontUtils {


	private static final class FontMetrics {
		private final float ascent;

		private final float descent;

		private final float height;

		public FontMetrics(final float height, final float ascent, final float descent) {
			this.height = height;
			this.ascent = ascent;
			this.descent = descent;
		}
	}

	private static final Map<String, FontMetrics> fontMetrics = new HashMap<>();

	private static final Map<String, PDFont> defaultFonts = new HashMap<>();

	private FontUtils() {
	}

	public static float getDescent(final PDFont font, final float fontSize) {
		final String fontName = font.getName();
		if (!fontMetrics.containsKey(fontName)) {
			createFontMetrics(font);
		}

		return fontMetrics.get(fontName).descent * fontSize;
	}

	public static float getHeight(final PDFont font, final float fontSize) {
		final String fontName = font.getName();
		if (!fontMetrics.containsKey(fontName)) {
			createFontMetrics(font);
		}

		return fontMetrics.get(fontName).height * fontSize;
	}

	private static void createFontMetrics(final PDFont font) {
		final float base = font.getFontDescriptor().getXHeight() / 1000;
		final float ascent = font.getFontDescriptor().getAscent() / 1000 - base;
		final float descent = font.getFontDescriptor().getDescent() / 1000;
		fontMetrics.put(font.getName(), new FontMetrics(base + ascent - descent, ascent, descent));
	}


	public static Map<String, PDFont> getDefaultfonts() {
		return defaultFonts;
	}


}
