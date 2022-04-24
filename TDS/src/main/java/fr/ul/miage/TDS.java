package fr.ul.miage;


import java.util.LinkedList;
import java.util.List;


public class TDS {
	private LinkedList<Symbole> tds;
	
	public TDS() {
		tds = new LinkedList<Symbole>();
	}
	public void ajouterSymbole(Symbole symbole)
	{
		tds.add(symbole);
	}
	public List<Symbole> getTds(){
		return tds;
	}
	
}
