package br.com.cesarschool.poo.mediators;

import br.com.cesarschool.poo.entidades.Account;
import br.com.cesarschool.poo.repositorios.DataBaseAccount;

public class AccountMediator {

    private DataBaseAccount dataBaseAccount = DataBaseAccount.getInstance();

	private static final String MENSAGEM_PRODUTO_NAO_INFORMADO = "Produto n�o informado!";
	private static final String MENSAGEM_CODIGO_INVALIDO = "C�digo inv�lido!";
	private static final String MENSAGEM_PRECO_INVALIDO = "Pre�o inv�lido!";
	private static final String MENSAGEM_NOME_NAO_INFORMADO = "Nome n�o informado!";	
	private static final String MENSAGEM_TIPO_NAO_PREENCHIDO = "Tipo n�o preenchido!";
	private static final String MENSAGEM_PRODUTO_JA_CADASTRADO = "Produto j� cadastrado!";
	private static final String MENSAGEM_PRODUTO_NAO_ENCONTRADO = "Produto n�o encontrado!";
	private static final int TAMANHO_MINIMO_NOME = 3; //NUMERO MINIMO AJEITAR
	
	public AccountValidationStatus include(Account account) {
		AccountValidationStatus status = validate(account);
		if (status.isValid()) {
			boolean dataBaseReturn = dataBaseAccount.include(account);
			if (!dataBaseReturn) {
				status.getErrorCodes()[0] = AccountValidationStatus.PRODUTO_JA_CADASTRADO;
				status.getMesages()[0] = MENSAGEM_PRODUTO_JA_CADASTRADO;
				status.setValid(false);
			}
		}				
		return status;
	}
	public AccountValidationStatus alter(Account account) {
		AccountValidationStatus status = validate(account);
		if (status.isValid()) {
			boolean dataBaseReturn = dataBaseAccount.change(account);
			if (!dataBaseReturn) {
				status.getErrorCodes()[0] = AccountValidationStatus.PRODUTO_NAO_ENCONTRADO;
				status.getMesages()[0] = MENSAGEM_PRODUTO_NAO_ENCONTRADO;
				status.setValid(false);
			}
		}				
		return status;
	}	
	public boolean delete(long number) {
		return dataBaseAccount.delete(number);
	}	
	public Account find(long number) {
		return dataBaseAccount.findAccount(number);
	}
	
	private AccountValidationStatus validate(Account account) {
		int[] codeStatus = new int[AccountValidationStatus.QTD_SITUACOES_EXCECAO]; 
		String[] mesagesStatus = new String[AccountValidationStatus.QTD_SITUACOES_EXCECAO];
		int contErrors = 0;
		if (account == null) {
			codeStatus[contErrors++] = AccountValidationStatus.PRODUTO_NAO_INFORMADO;
			mesagesStatus[contErrors] = MENSAGEM_PRODUTO_NAO_INFORMADO;			
		} else {
			if (!validateNumber(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.CODIGO_INVALIDO;
				mesagesStatus[contErrors] = MENSAGEM_CODIGO_INVALIDO;
			}
			/*if (!validateDate(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.CODIGO_INVALIDO;
				mesagesStatus[contErrors] = MENSAGEM_CODIGO_INVALIDO;
			}*/
			if (!validateBalance(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.PRECO_INVALIDO;
				mesagesStatus[contErrors] = MENSAGEM_PRECO_INVALIDO;				
			}		
			if (!filledScore(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.TIPO_NAO_PREENCHIDO;
				mesagesStatus[contErrors] = MENSAGEM_TIPO_NAO_PREENCHIDO;												
			}
			if (!validateStatus(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.TIPO_NAO_PREENCHIDO;
				mesagesStatus[contErrors] = MENSAGEM_TIPO_NAO_PREENCHIDO;	
			}
		}		
		return new AccountValidationStatus(codeStatus, mesagesStatus, contErrors == 0);		
	}	
	boolean validateNumber(Account account) {
		if (account.getNumber() <= 0) {
			return false;
		}
		return true;
	}
	boolean validateBalance(Account account) {
		return account.getBalance() >= 0;
	}
    boolean validateDate(Account account) {
		return account.getCreation_date() != null;
	}
	boolean filledScore(Account account) {
		return account.getScore() != null;
	}
	boolean validateStatus(Account account) {
		return account.getScore() != null;
	}
}
