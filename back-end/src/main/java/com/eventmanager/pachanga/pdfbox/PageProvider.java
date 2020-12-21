package com.eventmanager.pachanga.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public interface PageProvider<T extends PDPage> {

	T createPage();

	T nextPage();

	T previousPage();

	PDDocument getDocument();
}
