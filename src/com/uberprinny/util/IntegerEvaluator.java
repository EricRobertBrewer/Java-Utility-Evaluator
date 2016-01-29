package com.uberprinny.util;

public class IntegerEvaluator implements Evaluator {

	public IntegerEvaluator() {
	}
	
	public static final String NEGATIVE = "-";
	
	public static final String LEFT_PAREN = "(";
	public static final String RIGHT_PAREN = ")";
	
	public static final String ADD = "+";
	
	public static final String SUBTRACT = "-";
	
	public static final String MULTIPLY = "*";
	
	/**
	 * Warning!: performs integer division and drops remainder
	 */
	public static final String DIVIDE = "/";
	
	public static final String MODULO = "%";
	
	// TODO public static final String EXPONENT = "^";

	@Override
	public boolean isProper(String expression) {
		try {
			eval(expression);
		} catch (ExpressionFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Evaluate an integer-based mathematical expression.
	 * @param expression
	 * @return mathematical value of expression
	 * @throws ExpressionFormatException when syntax errors occur
	 */
	public int eval(String expression) throws ExpressionFormatException {
		if (expression == null) {
			throw new ExpressionFormatException("Expression is null.");
		}
		if (expression.length() == 0) {
			throw new ExpressionFormatException("Expression is empty.");
		}
		return rEval(expression.replaceAll(" ", ""));
	}
	
	private int rEval(String exp) throws ExpressionFormatException {
		if (exp.length() == 0) {
			throw new ExpressionFormatException("Missing operand.");
		}
		
		if (exp.contains(LEFT_PAREN)) {
			int leftParen = exp.lastIndexOf(LEFT_PAREN);
			int rightParen = exp.substring(leftParen).indexOf(RIGHT_PAREN);
			if (rightParen == -1) { // no right parenthesis after last left parenthesis
				throw new ExpressionFormatException("Missing right parenthesis to match (.");
			}
			
			rightParen += leftParen; // we want the index in entire expression, not in the substring
			
			String outerLeft = exp.substring(0, leftParen);
			int inner = rEval(exp.substring(leftParen + 1, rightParen));
			String outerRight = exp.substring(rightParen + 1, exp.length());
			return rEval(outerLeft + inner + outerRight);
		}
		
		if (exp.contains(RIGHT_PAREN)) {
			throw new ExpressionFormatException("Missing left parenthesis to match ).");
		}
		
		// + and - are associative; the order we compute them is irrelevant
		if (exp.contains(ADD)) {
			int add = exp.lastIndexOf(ADD);
			return rEval(exp.substring(0, add)) + rEval(exp.substring(add + 1, exp.length()));
		}
		if (exp.contains(SUBTRACT)) {
			// attempt to find any legitimate subtraction operator (not a negative sign), starting from the end
			String front = exp;
			int subtract = exp.lastIndexOf(SUBTRACT);
			while (subtract > 0 && isOperator(exp.charAt(subtract-1))) { // it's a negative sign; look again
				front = exp.substring(0, subtract);
				subtract = front.lastIndexOf(SUBTRACT);
			}
			if (subtract > 0) { // this is a legitimate subtraction ie. not following another operator
				return rEval(exp.substring(0, subtract)) - rEval(exp.substring(subtract + 1, exp.length()));
			}
		}
		
		// Must compute left to right
		if (exp.contains(MULTIPLY) || exp.contains(MODULO) || exp.contains(DIVIDE)) {
			int multiply = exp.lastIndexOf(MULTIPLY);
			int divide = exp.lastIndexOf(DIVIDE); // lastIndexOf() returns -1 if string is not found
			int modulo = exp.lastIndexOf(MODULO);
			int rightMost = Math.max(multiply, Math.max(divide, modulo)); // put right-most operator on stack first
			if (rightMost == multiply) {
				return rEval(exp.substring(0, multiply)) * rEval(exp.substring(multiply + 1, exp.length()));
			}
			if (rightMost == divide) {
				// TODO divide by 0 check? positive infinity?
				return rEval(exp.substring(0, divide)) / rEval(exp.substring(divide + 1, exp.length()));
			}
			if (rightMost == modulo) {
				// TODO mod by 0 check
				return rEval(exp.substring(0, modulo)) % rEval(exp.substring(modulo + 1, exp.length()));
			}
		}
		
		if (exp.contains(NEGATIVE)) { // NumberFormat can't read a double negative, but we can!
			int negative = exp.lastIndexOf(NEGATIVE);
			if (negative != 0 && NEGATIVE.equals(String.valueOf(exp.charAt(negative-1)))) { // another negative before this negative
				String left = exp.substring(0, negative-1);
				int positive = rEval(exp.substring(negative + 1, exp.length()));
				return rEval(left + positive); // eliminate both negative signs
			}
		}
		
		return Integer.valueOf(exp);
	}
	
	private boolean isOperator(char c) {
		String s = String.valueOf(c);
		return ADD.equals(s) || SUBTRACT.equals(s) || MULTIPLY.equals(s) || DIVIDE.equals(s) || MODULO.equals(s);
	}
}
