package com.eventmanager.pachanga.pdfbox;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

public class Row<T extends PDPage> {

	private final Table<T> table;
	PDOutlineItem bookmark;
	List<Cell<T>> cells;
	private boolean headerRow = false;
	float height;
	private float lineSpacing = 1;

	Row(Table<T> table, List<Cell<T>> cells, float height) {
		this.table = table;
		this.cells = cells;
		this.height = height;
	}

	Row(Table<T> table, float height) {
		this.table = table;
		this.cells = new ArrayList<>();
		this.height = height;
	}

	public Cell<T> createCell(float width, String value) {
		Cell<T> cell = new Cell<T>(this, width, value, true);
		if (headerRow) {
			// set all cell as header cell
			cell.setHeaderCell(true);
		}
		setBorders(cell, cells.isEmpty());
		cell.setLineSpacing(lineSpacing);
		cells.add(cell);
		return cell;
	}
	
	public Cell<T> createCell(float width, String value, float fontSize) {
		Cell<T> cell = new Cell<T>(this, width, value, true);
		if (headerRow) {
			// set all cell as header cell
			cell.setHeaderCell(true);
		}
		setBorders(cell, cells.isEmpty());
		cell.setLineSpacing(lineSpacing);
		cell.setFontSize(fontSize);
		cells.add(cell);
		return cell;
	}
	
	public Cell<T> createCell(float width, String value, float fontSize, boolean top, boolean bottom) {
		Cell<T> cell = new Cell<T>(this, width, value, true);
		if (headerRow) {
			// set all cell as header cell
			cell.setHeaderCell(true);
		}
		setBorders(cell, cells.isEmpty());
		cell.setLineSpacing(lineSpacing);
		cell.setFontSize(fontSize);
		if(top != true)cell.setTopBorderStyle(new LineStyle(Color.WHITE, 1));
		if(bottom != true)cell.setBottomBorderStyle(new LineStyle(Color.WHITE, 1));
		cells.add(cell);
		return cell;
	}

	public ImageCell<T> createImageCell(float width, Image img) {
		ImageCell<T> cell = new ImageCell<>(this, width, img, true);
		setBorders(cell, cells.isEmpty());
		cells.add(cell);
		return cell;
	}

	public Cell<T> createImageCell(float width, Image img, HorizontalAlignment align, VerticalAlignment valign) {
		Cell<T> cell = new ImageCell<T>(this, width, img, true, align, valign);
		setBorders(cell, cells.isEmpty());
		cells.add(cell);
		return cell;
	}

	public TableCell<T> createTableCell(float width, String tableData, PDDocument doc, PDPage page, float yStart,
			float pageTopMargin, float pageBottomMargin) {
		TableCell<T> cell = new TableCell<T>(this, width, tableData, true, doc, page, yStart, pageTopMargin,
				pageBottomMargin);
		setBorders(cell, cells.isEmpty());
		cells.add(cell);
		return cell;
	}

	public Cell<T> createCell(float width, String value, HorizontalAlignment align, VerticalAlignment valign) {
		Cell<T> cell = new Cell<T>(this, width, value, true, align, valign);
		if (headerRow) {
			// set all cell as header cell
			cell.setHeaderCell(true);
		}
		setBorders(cell, cells.isEmpty());
		cell.setLineSpacing(lineSpacing);
		cells.add(cell);
		return cell;
	}


	public Cell<T> createCell(String value) {
		float headerCellWidth = table.getHeader().getCells().get(cells.size()).getWidth();
		Cell<T> cell = new Cell<T>(this, headerCellWidth, value, false);
		setBorders(cell, cells.isEmpty());
		cells.add(cell);
		return cell;
	}

	private void setBorders(final Cell<T> cell, final boolean leftBorder) {
		if (!leftBorder) {
			cell.setLeftBorderStyle(null);
		}
	}

	void removeTopBorders() {
		for (final Cell<T> cell : cells) {
			cell.setTopBorderStyle(null);
		}
	}

	void removeAllBorders() {
		for (final Cell<T> cell : cells) {
			cell.setBorderStyle(null);
			;
		}
	}

	public float getHeight() {
		float maxheight = 0.0f;
		for (Cell<T> cell : this.cells) {
			float cellHeight = cell.getCellHeight();

			if (cellHeight > maxheight) {
				maxheight = cellHeight;
			}
		}

		if (maxheight > height) {
			this.height = maxheight;
		}
		return height;
	}

	public float getLineHeight() throws IOException {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public List<Cell<T>> getCells() {
		return cells;
	}

	public int getColCount() {
		return cells.size();
	}

	public void setCells(List<Cell<T>> cells) {
		this.cells = cells;
	}

	public float getWidth() {
		return table.getWidth();
	}

	public PDOutlineItem getBookmark() {
		return bookmark;
	}

	public void setBookmark(PDOutlineItem bookmark) {
		this.bookmark = bookmark;
	}

	protected float getLastCellExtraWidth() {
		float cellWidth = 0;
		for (Cell<T> cell : cells) {
			cellWidth += cell.getWidth();
		}

		float lastCellExtraWidth = this.getWidth() - cellWidth;
		return lastCellExtraWidth;
	}

	public float xEnd() {
		return table.getMargin() + getWidth();
	}

	public boolean isHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(boolean headerRow) {
		this.headerRow = headerRow;
	}

	public float getLineSpacing() {
		return lineSpacing;
	}

	public void setLineSpacing(float lineSpacing) {
		this.lineSpacing = lineSpacing;
	}
}

