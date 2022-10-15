package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.entidades.Account;

public class DataBaseAccount {
	
	private static final int MAX_SIZE_ACCOUNTS = 1000;
	private static DataBaseAccount instance = null;
	
	private Account[] arrayAccounts = new Account[MAX_SIZE_ACCOUNTS];
	private int currentSize = 0;

	private DataBaseAccount() {

	}

	public static DataBaseAccount getInstance() {
		if (instance == null) {
			instance = new DataBaseAccount();
		}
		return instance; 
	}
		
	public boolean include(Account account) {
		if (findIndex(account.getNumber()) != -1) {
			return false;
		} else if (currentSize == MAX_SIZE_ACCOUNTS - 1) {
			return false;
		} else {
			for (int i = 0; i < arrayAccounts.length; i++) {
				if (arrayAccounts[i] == null) {
					arrayAccounts[i] = account;
					break;
				}
			}
			currentSize++; 
			return true; 
		}
	}
	
	public boolean change(Account account) {
		int index = findIndex(account.getNumber()); 
		if (index == -1) {
			return false;
		} else {
			arrayAccounts[index] = account;
			return true; 
		}
	}
	
	public boolean delete(long code) {
		int index = findIndex(code);
		if (index == -1) {
			return false;
		} else {
			arrayAccounts[index] = null;
			currentSize--;
			return true;
		}		
	}
	
	public boolean block(long code) {
		int index = findIndex(code);
		if (index == -1) {
			return false;
		} else {
			arrayAccounts[index].setStatus("bloqueada");
			return true;
		}		
	}
	
	public boolean unblock(long code) {
		int index = findIndex(code);
		if (index == -1) {
			return false;
		} else {
			arrayAccounts[index].setStatus("ativa");
			return true;
		}		
	}
	
	public boolean credit(Account account, float creditValue) {
		long number = account.getNumber();
		if (number < 0) {
			return false;
		} else {
			account.setBalance(account.getBalance() + creditValue);
			return true;
		}		
	}
	
	public boolean debit(Account account, float debitValue) {
		long number = account.getNumber();
		float verifyDebit = account.getBalance()-debitValue;
		if (number < 0) {
			return false;
		} else {
			if (verifyDebit < 0) {
				System.out.println("Impossível Debitar Conta de Saldo Zero");
				return false;
			}else {
				account.setBalance(account.getBalance() - debitValue);
				return true;
			}
		}		
	}
	
	private int findIndex(long number) {		
		for (int i = 0; i < arrayAccounts.length; i++) {
			Account account = arrayAccounts[i];
			if (account != null && account.getNumber() == number) {
				return i; 				
			}
		}
		return -1;
	}

	public Account findAccount(long number) {
		for (int i = 0; i < arrayAccounts.length; i++) {
			long numberVerify = arrayAccounts[i].getNumber();
			if (numberVerify > 0 && numberVerify == number) {
				return arrayAccounts[i]; 				
			}
		}
		return null;
	}

}
