package br.com.cesarschool.poo.geral;

import java.util.Scanner;
import java.time.LocalDate;

public class AccountScreen {
	
	private static final int UNKNOWN_CODE = -1;
	private static final Scanner INPUT = new Scanner(System.in);
	private DataBaseAccount dataBaseAccount = new DataBaseAccount();
	
	public void executeScreen() {
		while(true) {
			
			long code = UNKNOWN_CODE;
			printHomePage();
			int option = INPUT.nextInt();
			
			switch (option) {
				case 1:
					includeAccount();
					break;
					
				case 2:
					//code = 
					System.out.println("ALTERAR");	
					break;
					
				case 3:
					code = searchProcess();
					
					if (code != UNKNOWN_CODE) {
						closeAccount(code);
					}
					break;
					
				case 4:
					code = searchProcess();
					
					if (code != UNKNOWN_CODE) {
						blockAccount(code);
					}
				
					break;
					
				case 5:
					code = searchProcess();
					
					if (code != UNKNOWN_CODE) {
						unblockAccount(code);
					}
					break;
					
				case 6:
					code = searchProcess();		
					
					if (code != UNKNOWN_CODE) {
						creditAccount(code);
					}
					break;
					
					
				case 7:
					code = searchProcess();	
					
					if (code != UNKNOWN_CODE) {
						debitAccount(code);
					}
					break;
				
				case 8:
					System.out.println("Encerrando o Sistema, Valeu Calabria...");
					System.exit(0);
					break;
				
				default:
					System.out.println("Opção inválida!!");
					
			}										
		} 
	}
	
	private void printHomePage() {		
		System.out.println("1- Incluir");
		System.out.println("2- Alterar");
		System.out.println("3- Encerar");
		System.out.println("4- Bloquear");
		System.out.println("5- Desbloquear");
		System.out.println("6- Creditar");
		System.out.println("7- Debitar");
		System.out.println("8- Sair");
		System.out.print("Digite a opção: ");
	}	
	
	private void includeAccount() {
		Account account = captureAccount(UNKNOWN_CODE);
		String validationReturn = validate(account);
		
		if (validationReturn == null) {		
			
			boolean dataBaseReturn = dataBaseAccount.include(account);
			if (dataBaseReturn) {
				System.out.println("Conta incluído com sucesso!");
			} else {
				System.out.println("Erro na inclusão da Conta!");
			}
		} else {
			System.out.println(validationReturn);
		}	
		
	}
	
	private long searchProcess() {
		
		System.out.println("Digite o numero da Conta: ");
		long number = INPUT.nextLong();
		Account account = dataBaseAccount.find(number);
		
		if (account == null) {
			System.out.println("Conta não encontrada!");
			return UNKNOWN_CODE;
		}else {
			System.out.println("Numero da Conta: " + account.getNumber());
			System.out.println("Status da Conta: " + account.getStatus());
			System.out.println("Saldo da Conta: " + account.getBalance());
			System.out.println("Score da Conta: " + account.getScore());
			System.out.println("Criação da Conta: " + account.getCreation_date());
			return number;
		}
	}
	
	private Account captureAccount(long alteredCode) {
		long number;
		
		if (alteredCode == UNKNOWN_CODE) {
			System.out.print("Digite o número da conta: ");
			number = INPUT.nextLong();			
		} else {
			number = alteredCode;
		}
		
		String initial_status = "Ativa";
		
		
		return new Account(number, initial_status);
	}
	
	private String validate(Account account) {
		
		if (!account.validateNumber()) {
			return "Número inválido!";
		} else if (!account.validateStatus()) {
			return "Status inválido!";
		} else if (!account.validateBalance()) {
			return "Saldo negativo inválido!";
		} else if (!account.filledScore()) {
			return "Score não preenchido!";
		} else if (!account.validateDate()) {
			return "Data inválida!";
		}else {
			return null;
		}
	}
	
	private void creditAccount(long number) {
		
		Account account = dataBaseAccount.findAccount(number);
			
		boolean closedAccountVerify = account.getClosed();
		
		if (!closedAccountVerify) {
			System.out.println("Digite o valor a ser Creditado: ");
			float creditValue = INPUT.nextFloat();
			boolean dataBaseReturn = dataBaseAccount.credit(account, creditValue);
				
			if (dataBaseReturn) {
				System.out.printf("Valor de %f foi Creditado com sucesso!", creditValue);
			} else {
				System.out.println("Erro ao Creditar valor");
			}	
		} else {
			System.out.println("Conta ENCERRADA, IMPOSSÍVEL CREDITAR");
		}
		
		
	
	}
	
	private void debitAccount(long number) {
		
		Account account = dataBaseAccount.findAccount(number);
		
		boolean blockedAccountVerify = account.getBlocked();
		
		if (!blockedAccountVerify) {
			System.out.println("Digite o valor a ser Debitado: ");
			float debitValue = INPUT.nextFloat();
			boolean dataBaseReturn = dataBaseAccount.debit(account, debitValue);
				
			if (dataBaseReturn) {
				System.out.printf("Valor de %f foi Debitado com sucesso!\n", debitValue);
			} else {
				System.out.println("Erro ao Debitar valor");
			}
		} else {
			System.out.println("Conta BLOQUEDA, IMPOSSÍVEL DEBITAR");
		}
			
	
	}
	
	private void closeAccount(long number) {
		
		Account account = dataBaseAccount.findAccount(number);
		
		System.out.println("Deseja encerrar a conta? ");
		System.out.println("1- Encerrar Conta");
		System.out.println("5- Sair da sessão Encerar");
		
		int sessionVariable = INPUT.nextInt();
		
		if (sessionVariable == 1) {
			System.out.println("CONTA ENCERRADA");
			account.setStatus("encerrada");
			account.setClosed(true);
			account.setActivated(false);
		} else if (sessionVariable == 5) {
			System.out.println("SAIR DA SESSÃO ENCERRAR");
		} else {
			System.out.println("Entrada INVÁLIDA, Saindo da Sessão");
		}
	}
	
	private void blockAccount(long number) {
		
		Account account = dataBaseAccount.findAccount(number);
		
		System.out.println("Deseja bloquear a conta?");
		System.out.println("1- Bloquear Conta");
		System.out.println("5- Sair da sessão Bloquear");
		
		int sessionVariable = INPUT.nextInt();
		
		if (sessionVariable == 1) {
			System.out.println("CONTA BLOQEADA");
			account.setStatus("bloqueada");
			account.setBlocked(true);
			account.setActivated(false);
		} else if (sessionVariable == 5) {
			System.out.println("SAIR DA SESSÃO BLOQUEAR");
		} else {
			System.out.println("Entrada INVÁLIDA, Saindo da Sessão");
		}
	}
	

	private void unblockAccount(long number) {
		
		Account account = dataBaseAccount.findAccount(number);
		
		System.out.println("Deseja desbloquear conta? ");
		System.out.println("1- Desbloquear Conta");
		System.out.println("5- Sair da sessão Desbloquear");
		
		int sessionVariable = INPUT.nextInt();
		
		if (sessionVariable == 1) {
			System.out.println("CONTA DESBLOQEADA");
			account.setStatus("ativa");
			account.setBlocked(false);
			account.setActivated(true);
		} else if (sessionVariable == 5) {
			System.out.println("SAIR DA SESSÃO DESBLOQUEAR");
		} else {
			System.out.println("Entrada INVÁLIDA, Saindo da Sessão");
		}
	}

}
