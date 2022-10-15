package br.com.cesarschool.poo.mediators;

import br.com.cesarschool.poo.entidades.AccountHolder;
import br.com.cesarschool.poo.repositorios.DataBaseHolder;
import br.com.cesarschool.poo.mediators.ValidateCPF;

public class HolderMediator {
	
	private DataBaseHolder dataBaseHolder = DataBaseHolder.getInstance();

	public boolean include(AccountHolder holder) {
		return dataBaseHolder.include(holder);
	}
	public boolean alter(AccountHolder holder) {
		return dataBaseHolder.alter(holder);
	}
	public AccountHolder find(String cpfCnpj) {
		return dataBaseHolder.find(cpfCnpj);
	}
	public boolean delete(String cpfCnpj) {
		return dataBaseHolder.delete(cpfCnpj);
	}
	
	public AccountHolder[] consultAllOrdererPerName() {
		AccountHolder[] all = dataBaseHolder.findAll();
		if (all != null && all.length > 0) {
			organizeHolderPerName(all);
		}
		return all;
	}

	private void organizeHolderPerName(AccountHolder[] holders) {
		AccountHolder ax = null;
		for (int i = 0; i < holders.length; i++) {
			for (int k = i; k < holders.length; k++) {
				if (holders[i].getName().compareTo(holders[k].getName()) > 0) {
					ax = holders[i];
					holders[i] = holders[k];
					holders[k] = ax;
				}
			} 
		}
	}

    public boolean validateCPF(AccountHolder holder) {
        String cpf = holder.getCpfCnpj();

        if (ValidateCPF.isCPF(cpf) == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateName(AccountHolder holder) {
        String name = holder.getName();

        if (name != null) {
            return true;
        } else {
            return false;
        }
    }

}
