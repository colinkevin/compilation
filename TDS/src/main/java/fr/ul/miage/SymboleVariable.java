package fr.ul.miage;

public class SymboleVariable extends Symbole {
	private int rang;
	private int val;
	private String scope;


	public SymboleVariable(String nom, Type type, Categ cat) {
		super(nom, type, cat);
		this.val=0;
		this.scope="";
	}
	public SymboleVariable(String nom, Type type, Categ cat, int val) {
		super(nom, type, cat);
		this.val=val;
		scope="";
	}

	
	public SymboleVariable(String nom, Type type, Categ cat, int rang, String scope) {
		super(nom, type, cat);
		val=0;
		this.rang = rang;
		this.scope = scope;
	}
	public int getRang() {
		return rang;
	}
	public int getVal() {
		return val;
	}
	public String getScope() {
		return scope;
	}


}
