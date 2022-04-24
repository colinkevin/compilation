package fr.ul.miage;

public class SymboleFonction extends Symbole {

	private int nbParam;
	private int nBloc;
	public SymboleFonction(String nom, Type type, Categ cat) {
		super(nom, type, cat);
		this.nBloc = 0;
		this.nbParam = 0;

	}
	public SymboleFonction(String nom, Type type, Categ cat, int nbParam, int nBloc) {
		super(nom, type, cat);
		this.nbParam = nbParam;
		this.nBloc = nBloc;
	}
	public int getNbParam() {
		return nbParam;
	}
	public int getnBloc() {
		return nBloc;
	}

}
