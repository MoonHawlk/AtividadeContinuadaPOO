package br.com.cesarschool.poo.entidades;

public class AccountHolder {
	private String CPF_CNPJ;
	private String name;

	public AccountHolder(String cpfCnpj, String name) {
		super();
		this.CPF_CNPJ = cpfCnpj;
		this.name = name;
	}
	
	public String getCpfCnpj() {
		return CPF_CNPJ;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.CPF_CNPJ = cpfCnpj;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	
	
}
