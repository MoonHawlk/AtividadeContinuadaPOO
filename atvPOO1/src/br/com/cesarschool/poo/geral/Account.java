package br.com.cesarschool.poo.geral;

import java.time.LocalDate;

public class Account {
	
	private long number;
	private String status;
	private float balance;
	private String score;
	private LocalDate creation_date;
	private boolean activated;
	private boolean closed;
	private boolean blocked;
	
	public Account(long number, String status) {
		this.number = number;
		this.status = "ativa";
		this.balance = 0;
		
		this.activated = true;
		this.closed = false;
		this.blocked = false;
		

		AccountScore initial_score = AccountScore.BRONZE;
		String score_value = initial_score.getScore();
		this.score = score_value;
		this.creation_date = LocalDate.now();
	}

	public long getNumber() {
		return number;
	}
	
	public void setNumber(long number) {
		this.number = number;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public float getBalance() {
		return balance;
	}
	
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public LocalDate getCreation_date() {
		return creation_date;
	}
	
	public String getScore() {
		return score;
	}
	
	public void setScore(String score) {
		this.score = score;
	}
	
	boolean validateNumber() {
		if (this.number <= 0) {
			return false;
		}
		return true;
	}
	
	boolean validateStatus() {
		return status != null;
	}
	
	boolean validateBalance() {
		return this.balance >= 0;
	}
	
	boolean filledScore() {
		return score != null;
	}
	
	boolean validateDate() {
		return creation_date != null;
	}

	public boolean getActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean getClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
}
