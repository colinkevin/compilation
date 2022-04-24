package fr.ul.miage;


public  abstract class Symbole {
	private String nom;
	private Type type;
	private Categ cat;
	public enum Categ {
		GLOBAL,
		PARAM,
		LOCAL,
		FONCTION
	}
	public Symbole(String nom, Type type, Categ cat) {
		this.nom = nom;
		this.type = type;
		this.cat = cat;
	}

	public String getType() {
		return type.toString();
	}

	public String getNom() {
		return nom;
	}
	protected void setNom(String nom) {
		this.nom = nom;
	}
	protected Categ getCat() {
		return cat;
	}

	protected void setCat(Categ cat) {
		this.cat = cat;
	}

	protected void setType(Type type) {
		this.type = type;
	}

}
