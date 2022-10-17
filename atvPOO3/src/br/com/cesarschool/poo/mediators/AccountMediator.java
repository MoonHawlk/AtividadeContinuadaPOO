package br.com.cesarschool.poo.mediators;

import br.com.cesarschool.poo.entidades.Account;
import br.com.cesarschool.poo.entidades.AccountSavings;
import br.com.cesarschool.poo.repositorios.DataBaseAccount;

public class AccountMediator {

    private DataBaseAccount dataBaseAccount = DataBaseAccount.getInstance();

	private static final String MESAGE_ACCOUNT_NOT_INFORMED = "Conta não informada!";
	private static final String MESAGE_INVALID_NUMBER = "Numero inválido!";
	private static final String MENSAGEM_PRECO_INVALIDO = "Pre�o inv�lido!";
	//private static final String MESAGE_NUMBER_NOT_INFORMED = "Numero não informado!";	
	private static final String MESAGE_SCORE_NOT_FILLED = "Score não preenchido!";
	private static final String MESAGE_ACCOUNT_REGISTERED = "Conta já cadastrado!";
	private static final String MESAGE_CONTA_NOT_FOUND = "Produto n�o encontrado!";
	//private static final int TAMANHO_MINIMO_NOME = 3; //NUMERO MINIMO AJEITAR
	
	public AccountValidationStatus include(Account account) {
		AccountValidationStatus status = validate(account);
		if (status.isValid()) {
			boolean dataBaseReturn = dataBaseAccount.include(account);
			if (!dataBaseReturn) {
				status.getErrorCodes()[0] = AccountValidationStatus.MESAGE_ACCOUNT_REGISTERED;
				status.getMesages()[0] = MESAGE_ACCOUNT_REGISTERED;
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
				status.getErrorCodes()[0] = AccountValidationStatus.MESAGE_CONTA_NOT_FOUND;
				status.getMesages()[0] = MESAGE_CONTA_NOT_FOUND;
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
		int[] codeStatus = new int[AccountValidationStatus.QTT_EXCEPTION_SITUATIONS]; 
		String[] mesagesStatus = new String[AccountValidationStatus.QTT_EXCEPTION_SITUATIONS];
		int contErrors = 0;
		if (account == null) {
			codeStatus[contErrors++] = AccountValidationStatus.ACCOUNT_NOT_INFORMED;
			mesagesStatus[contErrors] = MESAGE_ACCOUNT_NOT_INFORMED;			
		} else {
			if (!validateNumber(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.INVALID_CODE;
				mesagesStatus[contErrors] = MESAGE_INVALID_NUMBER;
			}
			/*if (!validateDate(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.INVALID_CODE;
				mesagesStatus[contErrors] = MENSAGEM_CODIGO_INVALIDO;
			}*/
			if (!validateBalance(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.PRECO_INVALIDO;
				mesagesStatus[contErrors] = MENSAGEM_PRECO_INVALIDO;				
			}		
			if (!filledScore(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.SCORE_NOT_FILLED;
				mesagesStatus[contErrors] = MESAGE_SCORE_NOT_FILLED;												
			}
			if (!validateStatus(account)) {
				codeStatus[contErrors++] = AccountValidationStatus.SCORE_NOT_FILLED;
				mesagesStatus[contErrors] = MESAGE_SCORE_NOT_FILLED;	
			}
			if (account instanceof AccountSavings) {
				AccountSavings accountSavings = (AccountSavings) account;
				if (accountSavings.getTotalDeposits() < 0) {
					codeStatus[contErrors++] = AccountValidationStatus.SCORE_NOT_FILLED;
					mesagesStatus[contErrors] = MESAGE_SCORE_NOT_FILLED;	
				}
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
	public boolean validateTaxFee(int taxFee) {
		
		if (taxFee < 0) {
			return false;
		} else {
			return true;
		}
	}
}
