package br.com.cesarschool.poo.mediators;

public class AccountValidationStatus {
		 
	public static final int NOME_NAO_INFORMADO = 1; 
	public static final int NOME_MUITO_CURTO = 2; 
	public static final int TIPO_NAO_PREENCHIDO = 3;
	public static final int PRODUTO_JA_CADASTRADO = 4;
	public static final int PRODUTO_NAO_ENCONTRADO = 5;
	public static final int CODIGO_INVALIDO = 6;
	public static final int PRECO_INVALIDO = 7;
	public static final int PRODUTO_NAO_INFORMADO = 8;
	public static final int QTD_SITUACOES_EXCECAO = 8;
	
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
