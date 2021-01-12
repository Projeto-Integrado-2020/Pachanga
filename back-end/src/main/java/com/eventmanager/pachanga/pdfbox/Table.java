package com.eventmanager.pachanga.pdfbox;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

public abstract class Table<T extends PDPage> {

	public final PDDocument document;
	private float margin;

	private T currentPage;
	private PageContentStreamOptimized tableContentStream;
	private List<PDOutlineItem> bookmarks;
	private List<Row<T>> header = new ArrayList<>();
	private List<Row<T>> rows = new ArrayList<>();

	private final float yStartNewPage;
	private float yStart;
	private final float width;
	private final boolean drawLines;
	private final boolean drawContent;
	private float headerBottomMargin = 4f;
	private float lineSpacing = 1f;

	private boolean tableIsBroken = false;
	private boolean tableStartedAtNewPage = false;
	private boolean removeTopBorders = false;
	private boolean removeAllBorders = false;

	private PageProvider<T> pageProvider;

	// page margins
	private final float pageTopMargin;
	private final float pageBottomMargin;

	//private boolean drawDebug;

	public Table(float yStart, float yStartNewPage, float pageTopMargin, float pageBottomMargin, float width,
			float margin, PDDocument document, T currentPage, boolean drawLines, boolean drawContent,
			PageProvider<T> pageProvider) throws IOException {
		this.pageTopMargin = pageTopMargin;
		this.document = document;
		this.drawLines = drawLines;
		this.drawContent = drawContent;
		// Initialize table
		this.yStartNewPage = yStartNewPage;
		this.margin = margin;
		this.width = width;
		this.yStart = yStart;
		this.pageBottomMargin = pageBottomMargin;
		this.currentPage = currentPage;
		this.pageProvider = pageProvider;
		loadFonts();
	}


	public void setPageProvider(PageProvider<T> pageProvider) {
		this.pageProvider = pageProvider;
	}

	protected abstract void loadFonts() throws IOException;



	protected PDDocument getDocument() {
		return document;
	}

	
	public float getWidth() {
		return width;
	}

	public Row<T> createRow(float height) {
		Row<T> row = new Row<T>(this, height);
		row.setLineSpacing(lineSpacing);
		this.rows.add(row);
		return row;
	}

	public float draw() throws IOException {
		ensureStreamIsOpen();

		for (Row<T> row : rows) {
			if (header.contains(row)) {
				if (isEndOfPage(getMinimumHeight())) {
					pageBreak();
					tableStartedAtNewPage = true;
				}
			}
			drawRow(row);
		}

		endTable();
		return yStart;
	}

	private void drawRow(Row<T> row) throws IOException {
		float rowHeight = row.getHeight();

		if (row != header 
				&& row != rows.get(0)) { //
			if (!isEndOfPage(rowHeight)) {
				row.removeTopBorders();
			}
		}

		if (row.getBookmark() != null) {
			PDPageXYZDestination bookmarkDestination = new PDPageXYZDestination();
			bookmarkDestination.setPage(currentPage);
			bookmarkDestination.setTop((int) yStart);
			row.getBookmark().setDestination(bookmarkDestination);
			this.addBookmark(row.getBookmark());
		}

		removeTopBorders = true;

		if (allBordersRemoved()) {
			row.removeAllBorders();
		}

            if (isEndOfPage(rowHeight) && !header.contains(row)) {

			endTable();

			pageBreak();

			if (!header.isEmpty()) {
				for (Row<T> headerRow : header) {
					drawRow(headerRow);
				}
		
				removeTopBorders = true;
			} else {
				removeTopBorders = false;
			}
		}
		if (row == rows.get(0)) {
			removeTopBorders = false;
		}

		if (removeTopBorders) {
			row.removeTopBorders();
		}

		if (row == rows.get(0)) {
			removeTopBorders = false;
		}

		if (removeTopBorders) {
			row.removeTopBorders();
		}

		if (drawLines) {
			drawVerticalLines(row, rowHeight);
		}

		if (drawContent) {
			drawCellContent(row, rowHeight);
		}
	}

	private T createNewPage() {
		if (pageProvider == null) {
			createPage(); //n sei como n consta que caiu aqui
		}
		return pageProvider.nextPage();
		
	}

	@Deprecated
	protected T createPage() {
		throw new IllegalStateException(
				"You either have to provide a " + PageProvider.class.getCanonicalName() + " or override this method");
	}

	private PageContentStreamOptimized createPdPageContentStream() throws IOException {
		return new PageContentStreamOptimized(
				new PDPageContentStream(getDocument(), getCurrentPage(),
						PDPageContentStream.AppendMode.APPEND, true));
	}

	private void drawCellContent(Row<T> row, float rowHeight) throws IOException {

		float cursorX = margin;
		float cursorY;

		for (Cell<T> cell : row.getCells()) {
			float cellStartX = cursorX;
			if (cell instanceof ImageCell) {
				final ImageCell<T> imageCell = (ImageCell<T>) cell;

				cursorY = yStart - cell.getTopPadding()
						- (cell.getTopBorder() == null ? 0 : cell.getTopBorder().getWidth());

				switch (cell.getValign()) {
				case TOP:
					break;
				case MIDDLE:
					cursorY -= cell.getVerticalFreeSpace() / 2;
					break;
				case BOTTOM:
					cursorY -= cell.getVerticalFreeSpace();
					break;
				}

				cursorX += cell.getLeftPadding() + (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth());

				switch (cell.getAlign()) {
				case CENTER:
					cursorX += cell.getHorizontalFreeSpace() / 2;
					break;
				case LEFT:
					break;
				case RIGHT:
					cursorX += cell.getHorizontalFreeSpace();
					break;
				}
				imageCell.getImage().draw(document, tableContentStream, cursorX, cursorY);

			} else if (cell instanceof TableCell) {
				final TableCell<T> tableCell = (TableCell<T>) cell;

				cursorY = yStart - cell.getTopPadding()
						- (cell.getTopBorder() != null ? cell.getTopBorder().getWidth() : 0);

				switch (cell.getValign()) {
				case TOP:
					break;
				case MIDDLE:
					cursorY -= cell.getVerticalFreeSpace() / 2;
					break;
				case BOTTOM:
					cursorY -= cell.getVerticalFreeSpace();
					break;
				}

				cursorX += cell.getLeftPadding() + (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth());
				tableCell.setXPosition(cursorX);
				tableCell.setYPosition(cursorY);
				this.tableContentStream.endText();
				tableCell.draw(currentPage);
			} else {
				
				if (cell.getFont() == null) {
					throw new IllegalArgumentException("Font is null on Cell=" + cell.getText());
				}

				if (cell.isTextRotated()) {
			
					cursorY = yStart - cell.getInnerHeight() - cell.getTopPadding()
							- (cell.getTopBorder() != null ? cell.getTopBorder().getWidth() : 0);

					switch (cell.getAlign()) {
					case CENTER:
						cursorY += cell.getVerticalFreeSpace() / 2;
						break;
					case LEFT:
						break;
					case RIGHT:
						cursorY += cell.getVerticalFreeSpace();
						break;
					}
					// respect left padding and descend by font height to get
					// position of the base line
					cursorX += cell.getLeftPadding()
							+ (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth())
							+ FontUtils.getHeight(cell.getFont(), cell.getFontSize())
							+ FontUtils.getDescent(cell.getFont(), cell.getFontSize());

					switch (cell.getValign()) {
					case TOP:
						break;
					case MIDDLE:
						cursorX += cell.getHorizontalFreeSpace() / 2;
						break;
					case BOTTOM:
						cursorX += cell.getHorizontalFreeSpace();
						break;
					}
					// make tokenize method just in case
					cell.getParagraph().getLines();
				} else {
				
					cursorY = yStart - cell.getTopPadding() - FontUtils.getHeight(cell.getFont(), cell.getFontSize())
							- FontUtils.getDescent(cell.getFont(), cell.getFontSize())
							- (cell.getTopBorder() == null ? 0 : cell.getTopBorder().getWidth());
/*
					if (drawDebug) {
					
						PDStreamUtils.rect(tableContentStream, cursorX + (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth()), yStart - (cell.getTopBorder() == null ? 0 : cell.getTopBorder().getWidth()), cell.getWidth() - (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth()) - (cell.getRightBorder() == null ? 0 : cell.getRightBorder().getWidth()), cell.getTopPadding(), Color.RED);
						
						PDStreamUtils.rect(tableContentStream, cursorX + (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth()), yStart - cell.getHeight() +  (cell.getBottomBorder() == null ? 0 : cell.getBottomBorder().getWidth()) + cell.getBottomPadding(), cell.getWidth() - (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth()) - (cell.getRightBorder() == null ? 0 : cell.getRightBorder().getWidth()), cell.getBottomPadding(), Color.RED);
						
						PDStreamUtils.rect(tableContentStream, cursorX + (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth()), yStart - (cell.getTopBorder() == null ? 0 : cell.getTopBorder().getWidth()), cell.getLeftPadding(), cell.getHeight() - (cell.getTopBorder() == null ? 0 : cell.getTopBorder().getWidth()) - (cell.getBottomBorder() == null ? 0 : cell.getBottomBorder().getWidth()), Color.RED);
						
						PDStreamUtils.rect(tableContentStream, cursorX + cell.getWidth() - (cell.getRightBorder() == null ? 0 : cell.getRightBorder().getWidth()) , yStart - (cell.getTopBorder() == null ? 0 : cell.getTopBorder().getWidth()), -cell.getRightPadding(), cell.getHeight() - (cell.getTopBorder() == null ? 0 : cell.getTopBorder().getWidth()) - (cell.getBottomBorder() == null ? 0 : cell.getBottomBorder().getWidth()), Color.RED);
					
					}
*/
					// respect left padding
					cursorX += cell.getLeftPadding()
							+ (cell.getLeftBorder() == null ? 0 : cell.getLeftBorder().getWidth());

					switch (cell.getAlign()) {
					case CENTER:
						cursorX += cell.getHorizontalFreeSpace() / 2;
						break;
					case LEFT:
						break;
					case RIGHT:
						cursorX += cell.getHorizontalFreeSpace();
						break;
					}

					switch (cell.getValign()) {
					case TOP:
						break;
					case MIDDLE:
						cursorY -= cell.getVerticalFreeSpace() / 2;
						break;
					case BOTTOM:
						cursorY -= cell.getVerticalFreeSpace();
						break;
					}

				}

				// remember this horizontal position, as it is the anchor for
				// each
				// new line
				float lineStartX = cursorX;
				float lineStartY = cursorY;

				this.tableContentStream.setNonStrokingColor(cell.getTextColor());

				int italicCounter = 0;
				int boldCounter = 0;

				this.tableContentStream.setRotated(cell.isTextRotated());

				// print all lines of the cell
				for (Map.Entry<Integer, List<Token>> entry : cell.getParagraph().getMapLineTokens().entrySet()) {

					// calculate the width of this line
					float freeSpaceWithinLine = cell.getParagraph().getMaxLineWidth()
							- cell.getParagraph().getLineWidth(entry.getKey());
					// TODO: need to implemented rotated text yo!
					if (cell.isTextRotated()) {
						cursorY = lineStartY;
						switch (cell.getAlign()) {
						case CENTER:
							cursorY += freeSpaceWithinLine / 2;
							break;
						case LEFT:
							break;
						case RIGHT:
							cursorY += freeSpaceWithinLine;
							break;
						}
					} else {
						cursorX = lineStartX;
						switch (cell.getAlign()) {
						case CENTER:
							cursorX += freeSpaceWithinLine / 2;
							break;
						case LEFT:
							// it doesn't matter because X position is always
							// the same
							// as row above
							break;
						case RIGHT:
							cursorX += freeSpaceWithinLine;
							break;
						}
					}

					// iterate through tokens in current line
					PDFont currentFont = cell.getParagraph().getFont(false, false);
					for (Token token : entry.getValue()) {
						switch (token.getType()) {
						case OPEN_TAG:
							if ("b".equals(token.getData())) {
								boldCounter++;
							} else if ("i".equals(token.getData())) {
								italicCounter++;
							}
							break;
						case CLOSE_TAG:
							if ("b".equals(token.getData())) {
								boldCounter = Math.max(boldCounter - 1, 0);
							} else if ("i".equals(token.getData())) {
								italicCounter = Math.max(italicCounter - 1, 0);
							}
							break;
						case PADDING:
							cursorX += Float.parseFloat(token.getData());
							break;
						case ORDERING:
							currentFont = cell.getParagraph().getFont(boldCounter > 0, italicCounter > 0);
							this.tableContentStream.setFont(currentFont, cell.getFontSize());
							if (cell.isTextRotated()) {
								tableContentStream.newLineAt(cursorX, cursorY);
								this.tableContentStream.showText(token.getData());
								cursorY += token.getWidth(currentFont) / 1000 * cell.getFontSize();
							} else {
								this.tableContentStream.newLineAt(cursorX, cursorY);
								this.tableContentStream.showText(token.getData());
								cursorX += token.getWidth(currentFont) / 1000 * cell.getFontSize();
							}
							break;
						case BULLET:
                            float widthOfSpace = currentFont.getSpaceWidth();
                            float halfHeight = FontUtils.getHeight(currentFont, cell.getFontSize()) / 2;
							if (cell.isTextRotated()) {
								PDStreamUtils.rect(tableContentStream, cursorX + halfHeight, cursorY,
										token.getWidth(currentFont) / 1000 * cell.getFontSize(),
                                        widthOfSpace / 1000 * cell.getFontSize(),
										cell.getTextColor());
								// move cursorY for two characters (one for
								// bullet, one for space after bullet)
								cursorY += 2 * widthOfSpace / 1000 * cell.getFontSize();
							} else {
								PDStreamUtils.rect(tableContentStream, cursorX, cursorY + halfHeight,
										token.getWidth(currentFont) / 1000 * cell.getFontSize(),
                                        widthOfSpace / 1000 * cell.getFontSize(),
										cell.getTextColor());
								// move cursorX for two characters (one for
								// bullet, one for space after bullet)
								cursorX += 2 * widthOfSpace / 1000 * cell.getFontSize();
							}
							break;
						case TEXT:
							currentFont = cell.getParagraph().getFont(boldCounter > 0, italicCounter > 0);
							this.tableContentStream.setFont(currentFont, cell.getFontSize());
							if (cell.isTextRotated()) {
								tableContentStream.newLineAt(cursorX, cursorY);
								this.tableContentStream.showText(token.getData());
								cursorY += token.getWidth(currentFont) / 1000 * cell.getFontSize();
							} else {
								try {
									this.tableContentStream.newLineAt(cursorX, cursorY);
									this.tableContentStream.showText(token.getData());
									cursorX += token.getWidth(currentFont) / 1000 * cell.getFontSize();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							break;
						}
					}
					if (cell.isTextRotated()) {
						cursorX = cursorX + cell.getParagraph().getFontHeight() * cell.getLineSpacing();
					} else {
						cursorY = cursorY - cell.getParagraph().getFontHeight() * cell.getLineSpacing();
					}
				}
			}
			// set cursor to the start of this cell plus its width to advance to
			// the next cell
			cursorX = cellStartX + cell.getWidth();
		}
		// Set Y position for next row
		yStart = yStart - rowHeight;

	}

	private void drawVerticalLines(Row<T> row, float rowHeight) throws IOException {
		float xStart = margin;

		Iterator<Cell<T>> cellIterator = row.getCells().iterator();
		while (cellIterator.hasNext()) {
			Cell<T> cell = cellIterator.next();

			float cellWidth = cellIterator.hasNext()
                    ? cell.getWidth()
                    : this.width - (xStart - margin);
			fillCellColor(cell, yStart, xStart, rowHeight, cellWidth);

			drawCellBorders(rowHeight, cell, xStart);

			xStart += cellWidth;
		}

	}

	private void drawCellBorders(float rowHeight, Cell<T> cell, float xStart) throws IOException {

		float yEnd = yStart - rowHeight;

		// top
		LineStyle topBorder = cell.getTopBorder();
		if (topBorder != null) {
			float y = yStart - topBorder.getWidth() / 2;
			drawLine(xStart, y, xStart + cell.getWidth(), y, topBorder);
		}

		// right
		LineStyle rightBorder = cell.getRightBorder();
		if (rightBorder != null) {
			float x = xStart + cell.getWidth() - rightBorder.getWidth() / 2;
			drawLine(x, yStart - (topBorder == null ? 0 : topBorder.getWidth()), x, yEnd, rightBorder);
		}

		// bottom
		LineStyle bottomBorder = cell.getBottomBorder();
		if (bottomBorder != null) {
			float y = yEnd + bottomBorder.getWidth() / 2;
			drawLine(xStart, y, xStart + cell.getWidth() - (rightBorder == null 
					? 0 : 
						rightBorder.getWidth()), y,
					bottomBorder);
		}

		// left
		LineStyle leftBorder = cell.getLeftBorder();
		if (leftBorder != null) {
			float x = xStart + leftBorder.getWidth() / 2;
			drawLine(x, yStart, x, yEnd + (bottomBorder == null ? 0 : bottomBorder.getWidth()), leftBorder);
		}

	}

	private void drawLine(float xStart, float yStart, float xEnd, float yEnd, LineStyle border) throws IOException {
		PDStreamUtils.setLineStyles(tableContentStream, border);
		tableContentStream.moveTo(xStart, yStart);
		tableContentStream.lineTo(xEnd, yEnd);
		tableContentStream.stroke();
	}

	private void fillCellColor(Cell<T> cell, float yStart, float xStart, float rowHeight, float cellWidth)
			throws IOException {

		if (cell.getFillColor() != null) {
			this.tableContentStream.setNonStrokingColor(cell.getFillColor());

			// y start is bottom pos
			yStart = yStart - rowHeight;
			float height = rowHeight - (cell.getTopBorder() == null ? 0 : cell.getTopBorder().getWidth());

			this.tableContentStream.addRect(xStart, yStart, cellWidth, height);
			this.tableContentStream.fill();
		}
	}

	private void ensureStreamIsOpen() throws IOException {
		if (tableContentStream == null) {
			tableContentStream = createPdPageContentStream();
		}
	}

	private void endTable() throws IOException {
		this.tableContentStream.close();
	}

	public T getCurrentPage() {
		return this.currentPage;
	}

	private boolean isEndOfPage(float freeSpaceForPageBreak) {
		float currentY = yStart - freeSpaceForPageBreak;
		boolean isEndOfPage = currentY <= pageBottomMargin;
		if (isEndOfPage) {
			setTableIsBroken(true);
		}
		return isEndOfPage;
	}

	private void pageBreak() throws IOException {
		tableContentStream.close();
		this.yStart = yStartNewPage - pageTopMargin;
		this.currentPage = createNewPage();
		this.tableContentStream = createPdPageContentStream();
	}

	private void addBookmark(PDOutlineItem bookmark) {
		if (bookmarks == null)
			bookmarks = new ArrayList<>();
		bookmarks.add(bookmark);
	}

	public List<PDOutlineItem> getBookmarks() {
		return bookmarks;
	}

	public float getHeaderAndDataHeight() {
		float height = 0;
		for (Row<T> row : rows) {
			height += row.getHeight();
		}
		return height;
	}

	public float getMinimumHeight() {
		float height = 0.0f;
		int firstDataRowIndex = 0;
		if (!header.isEmpty()) {
			for (Row<T> headerRow : header) {
				// count all header rows height
				height += headerRow.getHeight();
				firstDataRowIndex++;
			}
		}

		if (rows.size() > firstDataRowIndex) {
			height += rows.get(firstDataRowIndex).getHeight();
		}

		return height;
	}

	public void addHeaderRow(Row<T> row) {
		this.header.add(row);
		row.setHeaderRow(true);
	}

	public Row<T> getHeader() {
		if (header == null) {
			throw new IllegalArgumentException("Header Row not set on table");
		}

		return header.get(header.size() - 1);
	}

	public float getMargin() {
		return margin;
	}

/*
	public boolean isDrawDebug() {
		return drawDebug;
	}

	public void setDrawDebug(boolean drawDebug) {
		this.drawDebug = drawDebug;
	}
*/
	public boolean tableIsBroken() {
		return tableIsBroken;
	}

	public void setTableIsBroken(boolean tableIsBroken) {
		this.tableIsBroken = tableIsBroken;
	}


	public void setLineSpacing(float lineSpacing) {
		this.lineSpacing = lineSpacing;
	}

	public boolean allBordersRemoved() {
		return removeAllBorders;
	}

	public void removeAllBorders(boolean removeAllBorders) {
		this.removeAllBorders = removeAllBorders;
	}

}
