package com.sunwell.stock.exception;

public class OperationException extends RuntimeException {
	
	private int errCode = -1;
	
	public OperationException() {
	}
	
	public OperationException(int _code, String _message) {
		super(_message);
		errCode = _code;
	}
	
	public OperationException(Exception _ex, int _code) {
		super(_ex);
		errCode = _code; 
	}
	
	public void setErrorCode(int _code) {
		errCode = _code;
	}
	
	public int getErrorCode() {
		return errCode ;
	}
}
