package br.com.cesarschool.poo.telas;

import java.util.Scanner;

import br.com.cesarschool.poo.entidades.Account;
import br.com.cesarschool.poo.entidades.AccountScore;
import br.com.cesarschool.poo.mediators.AccountMediator;
import br.com.cesarschool.poo.mediators.AccountValidationStatus;
import br.com.cesarschool.poo.repositorios.DataBaseAccount;

import java.time.LocalDate;

public class AccountScreen {

	private static final int UNKNOWN_CODE = -1;
	private static final String PRODUTO_NAO_ENCONTRADO = "Produto n�o encontrado!";
	private static final Scanner INPUT = new Scanner(System.in);
	private AccountMediator accountMediator = new AccountMediator();
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
				System.out.println("Digite a Data de Criaçõa da Conta:");
				System.out.println("Nesse Formato (YYYY/MM/DD)");
				System.out.println("Ano:");
				int yearDate = INPUT.nextInt();
				
				System.out.println("Mês:");
				int monthDate = INPUT.nextInt();
				
				System.out.println("Dia:");
				int dayDate = INPUT.nextInt();
				
				account.setCreationDate(LocalDate.of(yearDate, monthDate, dayDate));
				System.out.println(("Data de Criação Alterada!"));
			} else if (sessionVariable == 5) {
				System.out.println("Operação Cancelada!");
			} else {
				System.out.println("Opção Inválida");
			}
		} else {
			processaMensagensErroValidacao(status);
		}
		
	}

	private long searchProcess() {

		System.out.println("Digite o numero da Conta: ");
		long number = INPUT.nextLong();
		Account account = accountMediator.find(number);
		AccountScore score;
		String score_value;

		if (account == null) {
			System.out.println("Conta não encontrada!");
			return UNKNOWN_CODE;
		} else {
			System.out.println("Numero da Conta: " + account.getNumber());
			System.out.println("Status da Conta: " + account.getStatus());
			System.out.println("Saldo da Conta: " + account.getBalance());
			
			if (account.getBlocked()) {
			    System.out.println("Score da Conta: INDISPONÍVEL, Status BLOQUEADO");
			} else if (account.getClosed()){
			    System.out.println("Score da Conta: 0, Status Encerrado");
			} else {
			    float balance = account.getBalance()*3;
			    LocalDate dateToday = LocalDate.now();
			    int accountLifeTime = dateToday.compareTo(account.getCreation_date())*2;
			    
			    float resultScore = balance + accountLifeTime;
			    
			    if (resultScore < 5800) { 
			        score = AccountScore.BRONZE;
			        score_value = score.getScore();
					account.setScore(score_value);
			    } else if (resultScore <= 13000 && resultScore >= 5800) {
			        score = AccountScore.SILVER;
                    score_value = score.getScore();
					account.setScore(score_value);
			    } else if (resultScore <= 39000  && resultScore >= 13001) {
			        score = AccountScore.GOLD;
                    score_value = score.getScore();
					account.setScore(score_value);
			    } else if (resultScore > 39000) {
			        score = AccountScore.DIAMOND;
                    score_value = score.getScore();
					account.setScore(score_value);
			    }
			    
			    System.out.println("Score da Conta: " + account.getScore());
			}
			
			System.out.println("Criação da Conta: " + account.getCreation_date());
			return number;
		}
	}

	private Account captureAccount(long alteredCode) {
		long number;
		long valueStatus = 0;
		LocalDate creationDate = null;
		LocalDate verifyDate = null;
		
		if (alteredCode == UNKNOWN_CODE) {
			System.out.print("Digite o número da conta: ");
			number = INPUT.nextLong();
			
			System.out.println("Digite o Status da Conta:");
			System.out.println("1- Ativa");
			System.out.println("2- Encerrada");
			System.out.println("3- Bloqueada");
			valueStatus = INPUT.nextLong();
			
			System.out.println("Digite a Data de Criaçõa da Conta:");
			System.out.println("Nesse Formato (YYYY/MM/DD)");
			System.out.println("Ano:");
			int yearDate = INPUT.nextInt();
			
			System.out.println("Mês:");
			int monthDate = INPUT.nextInt();
			
			System.out.println("Dia:");
			int dayDate = INPUT.nextInt();
			
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
			
		} else {
			number = alteredCode;
		}
			
		String initial_status = null;
		
		if (valueStatus == 1) {
		    initial_status = "Ativa";
		} else if (valueStatus == 2) {
		    initial_status = "Encerrada";
		} else if (valueStatus == 3) {
		    initial_status = "Bloqueada";
		}
		
		return new Account(number, initial_status, creationDate);
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
		String score_value = null;

		if (account == null) {
			System.out.println("Conta não encontrada!");
		} else {
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
			
		}
	}

}
