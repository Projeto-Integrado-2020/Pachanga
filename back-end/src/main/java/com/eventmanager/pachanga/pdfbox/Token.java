package com.eventmanager.pachanga.pdfbox;

import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.Objects;

public class Token {

	private final TokenType type;
	
	private final String data;

	public Token(TokenType type, String data) {
		this.type = type;
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	public TokenType getType() {
		return type;
	}

	public float getWidth(PDFont font) throws IOException {
		return font.getStringWidth(getData());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + type + "/" + data + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Token token = (Token) o;
		return getType() == token.getType() &&
				Objects.equals(getData(), token.getData());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getType(), getData());
	}

	public static Token text(TokenType type, String data) {
		return new TextToken(type, data);
	}
}
class TextToken extends Token {
	private PDFont cachedWidthFont;
	private float cachedWidth;

	TextToken(TokenType type, String data) {
		super(type, data);
	}

	@Override
	public float getWidth(PDFont font) throws IOException {
		if (font == cachedWidthFont) {
			return cachedWidth;
		}
		cachedWidth = super.getWidth(font);
		// must come after super call, in case it throws
		cachedWidthFont = font;
		return cachedWidth;
	}
}