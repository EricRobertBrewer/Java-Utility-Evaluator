package com.uberprinny.util;

public class ExpressionFormatException extends Exception {

	public ExpressionFormatException() {
		this(null);
	}
	
	public ExpressionFormatException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 8415592564599109188L;
}

