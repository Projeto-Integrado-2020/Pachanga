package com.eventmanager.pachanga.utils;

import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeManager {
	
	private QRCodeManager() {
	}

	public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws WriterException {
	    EAN13Writer barcodeWriter = new EAN13Writer();
	    BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, 300, 150);
	 
	    return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	public static BufferedImage generateQRCodeImage(String barcodeText) throws WriterException {
	    QRCodeWriter barcodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = 
	      barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
	 
	    return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
}
