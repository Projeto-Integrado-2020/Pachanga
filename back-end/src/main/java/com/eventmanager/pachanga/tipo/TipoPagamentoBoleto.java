package com.eventmanager.pachanga.tipo;

public enum TipoPagamentoBoleto {
	
	PAGO(3),
	DEVOLVIDO(6),
	CANCELADO(7),
	DEBITADO(8);
	
	
	private int valorStatus;

	private TipoPagamentoBoleto(int valorStatus) {
		this.valorStatus = valorStatus;
	}

	public int getStatus() {
		return this.valorStatus;
	}

}
