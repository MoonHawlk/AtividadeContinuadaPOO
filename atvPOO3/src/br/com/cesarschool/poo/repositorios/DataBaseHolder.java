package br.com.cesarschool.poo.repositorios;

import br.com.cesarschool.poo.entidades.AccountHolder;

public class DataBaseHolder {

	private static final int SIZE_MAX_ACCOUNT_HOLDERS = 1000;
	private static DataBaseHolder instance = null;  
	
	private AccountHolder[] holderRegister = new AccountHolder[SIZE_MAX_ACCOUNT_HOLDERS];
	private int currentSize = 0;
	
	private DataBaseHolder() {
		
	}
	
	public static DataBaseHolder getInstance() {
		if (instance == null) {
			instance = new DataBaseHolder();
		}
		return instance; 
	}
	
	public boolean include(AccountHolder holder) {
		if (findIndex(holder.getCpfCnpj()) != -1) {
			return false;
		} else if (currentSize == SIZE_MAX_ACCOUNT_HOLDERS - 1) {
			return false;
		} else {
			for (int i = 0; i < holderRegister.length; i++) {
				if (holderRegister[i] == null) {
					holderRegister[i] = holder; 
					break;
				}
			}
			currentSize++; 
			return true; 
		}
	}
	public boolean alter(AccountHolder AccountHolder) {
		int index = findIndex(AccountHolder.getCpfCnpj()); 
		if (index == -1) {
			return false;
		} else {
			holderRegister[index] = AccountHolder;
			return true; 
		}
	}
	
	public AccountHolder find(String cpfCnpj) {
		int index = findIndex(cpfCnpj);
		if (index == -1) {
			return null;
		} else {
			return holderRegister[index];
		}
	}
	
	public boolean delete(String cpfCnpj) {
		int index = findIndex(cpfCnpj);
		if (index == -1) {
			return false;
		} else {
			holderRegister[index] = null;
			currentSize--;
			return true;
		}		
	}
	
	public AccountHolder[] findAll() {
		AccountHolder[] result = new AccountHolder[currentSize];
		int index = 0;
		for (AccountHolder AccountHolder : holderRegister) {
			if (AccountHolder != null) {
				result[index++] = AccountHolder; 
			}
		}
		return result;
	}
	
	private int findIndex(String cpfCnpj) {		
		for (int i = 0; i < holderRegister.length; i++) {
			AccountHolder AccountHolder = holderRegister[i];
			if (AccountHolder != null && AccountHolder.getCpfCnpj().equals(cpfCnpj)) {
				return i; 				
			}
		}
		return -1;
	}		
}
