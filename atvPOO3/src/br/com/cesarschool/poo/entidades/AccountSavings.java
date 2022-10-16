package br.com.cesarschool.poo.entidades;

import java.time.LocalDate;

public class AccountSavings extends Account {

    private float taxFees = 0;
    private int totalDeposits = 0;

    public AccountSavings(long number, String status, LocalDate date, AccountHolder holder, float taxFees) {
        super(number, status, date, holder);
        
        this.taxFees = taxFees;
        this.totalDeposits = 0;
    }

    public float getTaxFees() {
        return this.taxFees;
    }

    public void setTaxFees(float taxFees) {
        this.taxFees = taxFees;
    }

    public int getTotalDeposits() {
        return this.totalDeposits;
    }

    public void setTotalDeposits() {
        this.totalDeposits += 1;
    }
    
}
