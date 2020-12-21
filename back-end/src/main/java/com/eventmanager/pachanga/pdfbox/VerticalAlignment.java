package com.eventmanager.pachanga.pdfbox;

public enum VerticalAlignment {
	TOP, MIDDLE, BOTTOM;
	
	public static VerticalAlignment get(final String key) {
		switch (key == null ? "top" : key.toLowerCase().trim()) {
		case "top":
			return TOP;
		case "middle":
			return MIDDLE;
		case "bottom":
			return BOTTOM;
			default:
				return TOP;
		}
	}
}
