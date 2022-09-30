package br.com.cesarschool.poo.geral;

public enum AccountScore {
	INATIVA(0, "Inativa"),
	BRONZE(1, "Bronze"),
	PRATA(2, "Prata"),
	OURO(3, "Ouro"),
	DIAMENTE(4, "Diamente"); 
		
	private int code;
	private String score;
	
	private AccountScore(int code, String score) {
		this.code = code;
		this.score = score;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getScore() {
		return score;
	}
	
	public static AccountScore getByCode(int code) {
		for (AccountScore accountscore : AccountScore.values()) {
			if (accountscore.getCode() == code) {
				return accountscore;
			}
		}
		return null;
	}

}
