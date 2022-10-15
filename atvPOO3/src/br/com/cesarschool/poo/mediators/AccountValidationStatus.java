package br.com.cesarschool.poo.mediators;

public class AccountValidationStatus {
		 
	public static final int NOME_NAO_INFORMADO = 1; 
	//public static final int NOME_MUITO_CURTO = 2; NUMBER NEGATIVE 
	public static final int SCORE_NOT_FILLED = 3;
	public static final int MESAGE_ACCOUNT_REGISTERED = 4;
	public static final int MESAGE_CONTA_NOT_FOUND = 5;
	public static final int INVALID_CODE = 6;
	public static final int PRECO_INVALIDO = 7;
	public static final int ACCOUNT_NOT_INFORMED = 8;
	public static final int QTT_EXCEPTION_SITUATIONS = 8;
	
	private int[] errorCodes;
	private String[] mesages;
	private boolean valid;
	
	public AccountValidationStatus(int[] errorCodes, String[] mesages, boolean valid) {
		super();
		this.errorCodes = errorCodes;
		this.mesages = mesages;
		this.valid = valid;
	}
	
	public int[] getErrorCodes() {
		return errorCodes;
	}
	public String[] getMesages() {
		return mesages;
	}
	public boolean isValid() {
		return valid;
	}
	void setValid(boolean valid) {
		this.valid = valid;
	}	
}
