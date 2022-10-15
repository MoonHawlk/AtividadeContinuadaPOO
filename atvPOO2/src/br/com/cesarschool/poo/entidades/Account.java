package br.com.cesarschool.poo.entidades;

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
	
	public Account(long number, String status, LocalDate date) {
		this.number = number;
		this.balance = 0;
		
		setStatusVariable(status);
		this.status = status;
		
		AccountScore initial_score = AccountScore.BRONZE;
		String score_value = initial_score.getScore();
		this.score = score_value;
		this.creation_date = date;
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

	public LocalDate setCreationDate(LocalDate date) {
		return this.creation_date = date;
	}
	
	public String getScore() {
		return score;
	}
	
	public void setScore(String score) {
		this.score = score;
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
	
	private void setStatusVariable(String status) {
	    if (status == "Ativa") {
            this.activated = true;
            this.closed = false;
            this.blocked = false;
        } else if (status == "Encerrada") {
            this.activated = false;
            this.closed = true;
            this.blocked = false;
        } else if (status == "Bloqueada") {
            this.activated = false;
            this.closed = false;
            this.blocked = true;
        }
	}
	
}
