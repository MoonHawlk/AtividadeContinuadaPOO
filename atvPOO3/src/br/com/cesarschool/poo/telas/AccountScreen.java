package br.com.cesarschool.poo.telas;

import java.util.Scanner;

import br.com.cesarschool.poo.entidades.Account;
import br.com.cesarschool.poo.entidades.AccountScore;
import br.com.cesarschool.poo.entidades.AccountHolder;
import br.com.cesarschool.poo.entidades.AccountSavings;
import br.com.cesarschool.poo.mediators.AccountMediator;
import br.com.cesarschool.poo.mediators.HolderMediator;
import br.com.cesarschool.poo.mediators.AccountValidationStatus;
import br.com.cesarschool.poo.repositorios.DataBaseAccount;

import java.time.LocalDate;

public class AccountScreen {

	private static final int UNKNOWN_CODE = -1;
	private static final String PRODUTO_NAO_ENCONTRADO = "Produto n�o encontrado!";
	private static final Scanner INPUT = new Scanner(System.in);
	private AccountMediator accountMediator = new AccountMediator();
	private HolderMediator accountHolderMediator = new HolderMediator();
	private DataBaseAccount dataBaseAccount = DataBaseAccount.getInstance();
    
	public void executeScreen() {
		while (true) {

			long code = UNKNOWN_CODE;
			printHomePage();
			int option = INPUT.nextInt();

			switch (option) {
				case 1:
					includeAccount();
					break;

				case 2:
					code = searchProcess();
					
					if (code != UNKNOWN_CODE) {
						alterAccount(code);
					}
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
						unlockAccount(code);
					}
					break;

				case 6: 
					code = searchProcess();

					if (code != UNKNOWN_CODE) {
						deleteAccount(code);
					}
					break;

				case 7:
					code = searchProcess();

					if (code != UNKNOWN_CODE) {
						findAccount(code);
					}
					break;

				case 8:
					code = searchProcess();

					if (code != UNKNOWN_CODE) {
						creditAccount(code);
					}
					break;

				case 9:
					code = searchProcess();

					if (code != UNKNOWN_CODE) {
						debitAccount(code);
					}
					break;

				case 10:
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
		System.out.println("6- Excluir");
		System.out.println("7- Buscar");
		System.out.println("8- Creditar");
		System.out.println("9- Debitar");
		System.out.println("10- Sair");
		System.out.print("Digite a opção: ");
	}

	private void processaMensagensErroValidacao(AccountValidationStatus status) {
		String[] mesagesErrors = status.getMesages();
		System.out.println("Problemas ao incuir/alterar produto:");
		for (String mesageError : mesagesErrors) {
			if (mesageError != null) {
				System.out.println(mesageError);
			} 
		}
	}

	private void includeAccount() {
		Account account = captureAccount(UNKNOWN_CODE);
		AccountValidationStatus status = accountMediator.include(account);

		if (account != null) {
			if (status.isValid()) {
				System.out.println("Conta incluído com sucesso!");
			}else {
					System.out.println("Erro na inclusão da Conta!");
				}
			} else {
				System.out.println(status);
			}
		} 

	private void alterAccount(long number) {
		Account account = accountMediator.find(number);
		AccountValidationStatus status = accountMediator.alter(account);

		if (status.isValid()) {
			System.out.println("Deseja alterar a Data da Conta:");
			System.out.println("1- Alterar");
			System.out.println("5- Cancelar");

			int sessionVariable = INPUT.nextInt();

			if (sessionVariable == 1) {
				int yearDate = setYearDateAccount();
				
				int monthDate = setMonthDateAccount();
				
				int dayDate = setDayDateAccount();
				
				account.setCreationDate(LocalDate.of(yearDate, monthDate, dayDate));
				System.out.println(("Data de Criação Alterada!"));
			} else if (sessionVariable == 5) {
				System.out.println("Operação Cancelada!");
			} else {
				System.out.println("Opção Inválida");
			}

			if (account instanceof AccountSavings) {
				AccountSavings accountSavings = (AccountSavings) account;
				boolean validateTaxFee;
				System.out.println("Deseja alterar a Taxa de Juros da Conta:");
				System.out.println("1- Alterar");
				System.out.println("5- Cancelar");

				sessionVariable = INPUT.nextInt();

				if (sessionVariable == 1) {
					System.out.println("Digite a taxa de Juros: ");
					int taxFee = INPUT.nextInt();

					validateTaxFee = accountMediator.validateTaxFee(taxFee);

					if (validateTaxFee) {
						accountSavings.setTaxFees(taxFee);
						System.out.println("Taxa de Juros Alterada!");
					} else {
						System.out.println("Valor da Taxa de Juros Inválida!");
					}

				} else if (sessionVariable == 5) {
					System.out.println("Operação Cancelada!");
				} else {
					System.out.println("Opção Inválida");
				}

			}
		} else {
			processaMensagensErroValidacao(status);
		}
		
	}

	private long searchProcess() {

		System.out.println("Digite o numero da Conta: ");
		long number = INPUT.nextLong();
		Account account = accountMediator.find(number);

		if (account == null ) {
			System.out.println("Conta não encontrada!");
			return UNKNOWN_CODE;
		} else {
			return number;
		} 
	}

	private Account captureAccount(long alteredCode) {
		long number;
		int status;
		int yearDate;
		int monthDate;
		int dayDate;
		boolean verifyCPF;
		boolean validateTaxFee;
		String initial_status = null;
		LocalDate creationDate = null;
		LocalDate verifyDate = null;
		Account account = null;
		
		if (alteredCode == UNKNOWN_CODE) {
			number = setAccountNumber();
			
			status = setAccountStatus();
			initial_status = setStatusAccount(status);
		
			yearDate = setYearDateAccount();
			
			monthDate = setMonthDateAccount();
			
			dayDate = setDayDateAccount();
			
			creationDate = LocalDate.of(yearDate, monthDate, dayDate);
			verifyDate = LocalDate.now();

			int monthVerifyDate = verifyDate.getMonthValue();
			int monthLifeTime = monthVerifyDate - monthDate;

			int dayCreationDate = dayDate;
			int dayVerifyDate = verifyDate.getDayOfMonth();
			int accountLifeTime = dayVerifyDate - dayCreationDate;

			if (yearDate == verifyDate.getYear()) {

				if (monthDate == monthVerifyDate) {

					if (accountLifeTime < 0) {
						System.out.println("Data Inválida!");
						return null;
					}
				} else if (monthLifeTime == 1) {
					if (accountLifeTime > 0) {
						System.out.println("Data Inválida!");
						return null;
					}
				} else {
					System.out.println("Data Inválida!");
					return null;
				}
			} else {
				System.out.println("Data Inválida!");
				return null;
			}

			System.out.println("Digite o nome do Correntista: ");
			String name = INPUT.next();

			System.out.println("Digite o CPF do Correntista:");
			String cpf = INPUT.next();
			verifyCPF = accountHolderMediator.validateCPF(cpf);

			if (verifyCPF) {
				AccountHolder holder = new AccountHolder(cpf, name);
				System.out.print("ATENÇÃO: se a Conta NÃO for Poupança, DIGITE 0 NO PRÓXIMO CAMPO!!");
				System.out.print("Digite a taxa de juros: ");
				int taxFees = INPUT.nextInt();

				if (taxFees == 0) {			
					account = new Account(number, initial_status, creationDate, holder);
					account.setHolder(holder);
				} else {
					validateTaxFee = accountMediator.validateTaxFee(taxFees);

					if (validateTaxFee) {
						account = new AccountSavings(number, initial_status, creationDate, holder, taxFees);
					} else {
						System.out.println("Valor da Taxa de Juros Inválida!");
					}
					
				}
			} else {
				System.out.println("CPF INVÁLIDO");
			}
			
		} else {
			number = alteredCode;
		}
		
		return account;
	}

	private void creditAccount(long number) {

		Account account = accountMediator.find(number);
		boolean closedAccountVerify = account.getClosed();

		if (!closedAccountVerify) {
			System.out.println("Digite o valor a ser Creditado: ");
			float creditValue = INPUT.nextFloat();

			boolean dataBaseReturn = dataBaseAccount.credit(account, creditValue);

			if (dataBaseReturn) {
				System.out.printf("Valor de %f foi Creditado com sucesso!", creditValue);
				
				if (account instanceof AccountSavings) {
					AccountSavings accountSavings = (AccountSavings) account;
					accountSavings.setTotalDeposits();
				}
			} else {
				System.out.println("Erro ao Creditar valor");
			}
		} else {
			System.out.println("Conta ENCERRADA, IMPOSSÍVEL CREDITAR");
		}

	}

	private void debitAccount(long number) {

		Account account = accountMediator.find(number);
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

		Account account = accountMediator.find(number);

		if (account.getClosed()) {
			System.out.println("Conta já Encerrada!");
		} else {
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
	}

	private void blockAccount(long number) {

		Account account = accountMediator.find(number);

		if (account.getBlocked() || account.getClosed()) {
			System.out.println("Conta já Bloqueada ou Encerrada!");
		} else {
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

	}

	private void unlockAccount(long number) {

		Account account = accountMediator.find(number);

		if (account.getActivated() || account.getClosed()) {
			System.out.println("Conta já Ativa ou Conta Encerrada!");
		} else {
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

	private void deleteAccount(long number) {

		Account account = accountMediator.find(number);
		boolean deleteAccountVerify = accountMediator.delete(number);

		if (deleteAccountVerify) {
			System.out.printf("Conta de numero %d foi Excluida!", account.getNumber());
		} else {
			System.out.println(PRODUTO_NAO_ENCONTRADO);
		}
	}

	private void findAccount(long number) {
		
		Account account = accountMediator.find(number);

		if (account == null) {
			System.out.println("Conta não encontrada!");
		} else {
			printAccount(account);
		} 
	}

	private long setAccountNumber() {
		long number;

		System.out.print("Digite o número da conta: ");
		number = INPUT.nextLong();

		return number;
	}

	private int setAccountStatus() {
		int valueStatus = 0;

		System.out.println("Digite o Status da Conta:");
		System.out.println("1- Ativa");
		System.out.println("2- Encerrada");
		System.out.println("3- Bloqueada");
		valueStatus = INPUT.nextInt();

		return valueStatus;
	}

	private int setYearDateAccount() {
		System.out.println("Digite a Data de Criaçõa da Conta:");
		System.out.println("Nesse Formato (YYYY/MM/DD)");
		System.out.println("Ano:");
		int yearDate = INPUT.nextInt();

		return yearDate;
	}

	private int setMonthDateAccount() {
		System.out.println("Mês:");
		int monthDate = INPUT.nextInt();

		return monthDate;
	}

	private int setDayDateAccount() {
		System.out.println("Dia:");
		int dayDate = INPUT.nextInt();

		return dayDate;
	}

	private String setStatusAccount(int status) {
		String initial_status = null;

		if (status == 1) {
		    initial_status = "Ativa";
		} else if (status == 2) {
		    initial_status = "Encerrada";
		} else if (status == 3) {
		    initial_status = "Bloqueada";
		}

		return initial_status;
	}

	private void printAccount(Account account) {
		AccountHolder holder = null;
		String score_value = null;

		System.out.println("Numero da Conta: " + account.getNumber());
		System.out.println("Status da Conta: " + account.getStatus());
		System.out.println("Saldo da Conta: " + account.getBalance());
			
		if (account.getBlocked()) {
			System.out.println("Score da Conta: INDISPONÍVEL, Status BLOQUEADO");
		} else if (account.getClosed()){
			    System.out.println("Score da Conta: 0, Status ENCERRADO");
		} else {
			float balance = account.getBalance()*3;
			LocalDate dateToday = LocalDate.now();
			int accountLifeTime = dateToday.compareTo(account.getCreation_date())*2;
			    
			float resultScore = balance + accountLifeTime;
			    
			if (resultScore < 5800) { 
			    score_value = "Bronze";
			} else if (resultScore <= 13000 && resultScore >= 5800) {
			     score_value = "Prata";
			} else if (resultScore <= 39000  && resultScore >= 13001) {
			    score_value = "Ouro";
			} else if (resultScore > 39000) {
			    score_value = "Diamante";
			}
			    
			System.out.println("Score da Conta: " + score_value);
		}
			
			System.out.println("Criação da Conta: " + account.getCreation_date());

			holder = account.getHolder();
			System.out.println("Nome do Correntista: " + holder.getName());
			System.out.println("CPF do Correntista: " + holder.getCpfCnpj());

			if (account instanceof AccountSavings) {
				AccountSavings accountSavings = (AccountSavings) account;
				System.out.println("Taxa de Juros: " + accountSavings.getTaxFees());
				System.out.println("Total de Depósitos: " + accountSavings.getTotalDeposits());
			}

	}

}
