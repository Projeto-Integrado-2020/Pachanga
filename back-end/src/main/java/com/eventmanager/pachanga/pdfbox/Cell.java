package com.eventmanager.pachanga.pdfbox;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Cell<T extends PDPage> {

	private float width;
	private Float height;
	private String text;

	private PDFont font = PDType1Font.HELVETICA;
	private PDFont fontBold = PDType1Font.HELVETICA_BOLD;

	private float fontSize = 8;
	private Color fillColor;
	private Color textColor = Color.BLACK;
	private final Row<T> row;
	private WrappingFunction wrappingFunction;
	private boolean isHeaderCell = false;
	private boolean isColspanCell = false;

	// default padding
	private float leftPadding = 5f;
	private float rightPadding = 5f;
	private float topPadding = 5f;
	private float bottomPadding = 5f;

	// default border
	private LineStyle leftBorderStyle = new LineStyle(Color.BLACK, 1);
	private LineStyle rightBorderStyle = new LineStyle(Color.BLACK, 1);
	private LineStyle topBorderStyle = new LineStyle(Color.BLACK, 1);
	private LineStyle bottomBorderStyle = new LineStyle(Color.BLACK, 1);

	private Paragraph paragraph = null;
	private float lineSpacing = 1;
	private boolean textRotated = false;

	private HorizontalAlignment align;
	private VerticalAlignment valign;

	float horizontalFreeSpace = 0;
	float verticalFreeSpace = 0;

	Cell(Row<T> row, float width, String text, boolean isCalculated) {
		this(row, width, text, isCalculated, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
	}

	Cell(Row<T> row, float width, String text, boolean isCalculated, HorizontalAlignment align,
			VerticalAlignment valign) {
		this.row = row;
		if (isCalculated) {
			double calclulatedWidth = ((row.getWidth() * width) / 100);
			this.width = (float) calclulatedWidth;
		} else {
			this.width = width;
		}

		if (getWidth() > row.getWidth()) {
			throw new IllegalArgumentException(
					"Cell Width=" + getWidth() + " can't be bigger than row width=" + row.getWidth());
		}
		//check if we have new default font
		if(!FontUtils.getDefaultfonts().isEmpty()){
			font = FontUtils.getDefaultfonts().get("font");
			fontBold = FontUtils.getDefaultfonts().get("fontBold");
		}
		this.text = text == null ? "" : text;
		this.align = align;
		this.valign = valign;
		this.wrappingFunction = null;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public float getWidth() {
		return width;
	}
	
	public float getInnerWidth() {
		return getWidth() - getLeftPadding() - getRightPadding()
				- (leftBorderStyle == null ? 0 : leftBorderStyle.getWidth())
				- (rightBorderStyle == null ? 0 : rightBorderStyle.getWidth());
	}

	public float getInnerHeight() {
		return getHeight() - getBottomPadding() - getTopPadding()
				- (topBorderStyle == null ? 0 : topBorderStyle.getWidth())
				- (bottomBorderStyle == null ? 0 : bottomBorderStyle.getWidth());
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;

		// paragraph invalidated
		paragraph = null;
	}

	public PDFont getFont() {
		if (font == null) {
			throw new IllegalArgumentException("Font not set.");
		}
		if (isHeaderCell) {
			return fontBold;
		} else {
			return font;
		}
	}

	public void setFont(PDFont font) {
		this.font = font;

		// paragraph invalidated
		paragraph = null;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;

		// paragraph invalidated
		paragraph = null;
	}

	public Paragraph getParagraph() {
		if (paragraph == null) {
			// if it is header cell then use font bold
			if (isHeaderCell) {
				if (isTextRotated()) {
					paragraph = new Paragraph(text, fontBold, fontSize, getInnerHeight(), align, textColor, null,
							wrappingFunction, lineSpacing);
				} else {
					paragraph = new Paragraph(text, fontBold, fontSize, getInnerWidth(), align, textColor, null,
							wrappingFunction, lineSpacing);
				}
			} else {
				if (isTextRotated()) {
					paragraph = new Paragraph(text, font, fontSize, getInnerHeight(), align, textColor, null,
							wrappingFunction, lineSpacing);
				} else {
					paragraph = new Paragraph(text, font, fontSize, getInnerWidth(), align, textColor, null,
							wrappingFunction, lineSpacing);
				}
			}
		}
		return paragraph;
	}

	public float getExtraWidth() {
		return this.row.getLastCellExtraWidth() + getWidth();
	}

	public float getHeight() {
		return row.getHeight();
	}


	public float getCellHeight() {
		if (height != null) {
			return height;
		}

		if (isTextRotated()) {
			try {
				return getFont().getStringWidth(getText()) / 1000 * getFontSize() + getTopPadding()
						+ (getTopBorder() == null ? 0 : getTopBorder().getWidth()) + getBottomPadding()
						+ (getBottomBorder() == null ? 0 : getBottomBorder().getWidth());
			} catch (final IOException e) {
				throw new IllegalStateException("Font not set.", e);
			}
		} else {
			return getTextHeight() + getTopPadding() + getBottomPadding()
					+ (getTopBorder() == null ? 0 : getTopBorder().getWidth())
					+ (getBottomBorder() == null ? 0 : getBottomBorder().getWidth());
		}
	}
	public void setHeight(final Float height) {
		this.height = height;
	}

	public float getTextHeight() {
		return getParagraph().getHeight();
	}

	public float getTextWidth() {
		return getParagraph().getWidth();
	}
	public float getLeftPadding() {
		return leftPadding;
	}

	public void setLeftPadding(float cellLeftPadding) {
		this.leftPadding = cellLeftPadding;

		// paragraph invalidated
		paragraph = null;
	}

	public float getRightPadding() {
		return rightPadding;
	}

	public void setRightPadding(float cellRightPadding) {
		this.rightPadding = cellRightPadding;

		// paragraph invalidated
		paragraph = null;
	}
	
	public float getTopPadding() {
		return topPadding;
	}

	public void setTopPadding(float cellTopPadding) {
		this.topPadding = cellTopPadding;
	}
	
	public float getBottomPadding() {
		return bottomPadding;
	}

	public void setBottomPadding(float cellBottomPadding) {
		this.bottomPadding = cellBottomPadding;
	}

	public float getVerticalFreeSpace() {
		if (isTextRotated()) {
			// need to calculate max line width so we just iterating through
			// lines
			for (String line : getParagraph().getLines()) {
			}
			return getInnerHeight() - getParagraph().getMaxLineWidth();
		} else {
			return getInnerHeight() - getTextHeight();
		}
	}
	
	public float getHorizontalFreeSpace() {
		if (isTextRotated()) {
			return getInnerWidth() - getTextHeight();
		} else {
			return getInnerWidth() - getParagraph().getMaxLineWidth();
		}
	}

	public HorizontalAlignment getAlign() {
		return align;
	}

	public VerticalAlignment getValign() {
		return valign;
	}

	public boolean isHeaderCell() {
		return isHeaderCell;
	}

	public void setHeaderCell(boolean isHeaderCell) {
		this.isHeaderCell = isHeaderCell;
	}

	public WrappingFunction getWrappingFunction() {
		return getParagraph().getWrappingFunction();
	}

	public void setWrappingFunction(WrappingFunction wrappingFunction) {
		this.wrappingFunction = wrappingFunction;

		paragraph = null;
	}

	public LineStyle getLeftBorder() {
		return leftBorderStyle;
	}

	public LineStyle getRightBorder() {
		return rightBorderStyle;
	}

	public LineStyle getTopBorder() {
		return topBorderStyle;
	}

	public LineStyle getBottomBorder() {
		return bottomBorderStyle;
	}

	public void setLeftBorderStyle(LineStyle leftBorder) {
		this.leftBorderStyle = leftBorder;
	}

	public void setRightBorderStyle(LineStyle rightBorder) {
		this.rightBorderStyle = rightBorder;
	}

	public void setTopBorderStyle(LineStyle topBorder) {
		this.topBorderStyle = topBorder;
	}

	public void setBottomBorderStyle(LineStyle bottomBorder) {
		this.bottomBorderStyle = bottomBorder;
	}

	public void setBorderStyle(LineStyle border) {
		this.leftBorderStyle = border;
		this.rightBorderStyle = border;
		this.topBorderStyle = border;
		this.bottomBorderStyle = border;
	}

	public boolean isTextRotated() {
		return textRotated;
	}

	public void setTextRotated(boolean textRotated) {
		this.textRotated = textRotated;
	}

	public PDFont getFontBold() {
		return fontBold;
	}

	public void setFontBold(final PDFont fontBold) {
		this.fontBold = fontBold;
	}

	public boolean isColspanCell() {
		return isColspanCell;
	}

	public void setColspanCell(boolean isColspanCell) {
		this.isColspanCell = isColspanCell;
	}

	public void setAlign(HorizontalAlignment align) {
		this.align = align;
	}

	public void setValign(VerticalAlignment valign) {
		this.valign = valign;
	}

	public void copyCellStyle(Cell sourceCell) {
		Boolean leftBorder = this.leftBorderStyle == null;
		setBorderStyle(sourceCell.getTopBorder());
		if (leftBorder) {
			this.leftBorderStyle = null;// if left border wasn't set, don't set
										// it now
		}
		this.font = sourceCell.getFont();// otherwise paragraph gets invalidated
		this.fontBold = sourceCell.getFontBold();
		this.fontSize = sourceCell.getFontSize();
		setFillColor(sourceCell.getFillColor());
		setTextColor(sourceCell.getTextColor());
		setAlign(sourceCell.getAlign());
		setValign(sourceCell.getValign());
	}

	public Boolean hasSameStyle(Cell sourceCell) {
		if (!sourceCell.getTopBorder().equals(getTopBorder())) {
			return false;
		}
		if (!sourceCell.getFont().equals(getFont())) {
			return false;
		}
		if (!sourceCell.getFontBold().equals(getFontBold())) {
			return false;
		}
		if (!sourceCell.getFillColor().equals(getFillColor())) {
			return false;
		}
		if (!sourceCell.getTextColor().equals(getTextColor())) {
			return false;
		}
		if (!sourceCell.getAlign().equals(getAlign())) {
			return false;
		}
		if (!sourceCell.getValign().equals(getValign())) {
			return false;
		}
		return true;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getLineSpacing() {
		return lineSpacing;
	}

	public void setLineSpacing(float lineSpacing) {
		this.lineSpacing = lineSpacing;
	}

}

