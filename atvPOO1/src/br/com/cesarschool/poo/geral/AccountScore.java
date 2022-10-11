package br.com.cesarschool.poo.geral;

public enum AccountScore {
	BRONZE(1, "Bronze"),
	SILVER(2, "Prata"),
	GOLD(3, "Ouro"),
	DIAMOND(4, "Diamente"); 
		
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
