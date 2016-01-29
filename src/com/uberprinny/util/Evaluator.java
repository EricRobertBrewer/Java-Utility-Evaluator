package com.uberprinny.util;

// TODO public double evalReal(String expression) throws ExpressionFormatException {}

// TODO public boolean evalBoolean(String expression) throws ExpressionFormatException {}

public interface Evaluator {

	/**
	 * Check for proper operator and operand syntax.
	 * @param expression
	 * @return true if the expression is syntactically correct in the given mode
	 */
	public boolean isProper(String expression);
}
