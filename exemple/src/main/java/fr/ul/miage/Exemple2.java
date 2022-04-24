package fr.ul.miage;

import fr.ul.miage.Symbole.Categ;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Prog;
import fr.ul.miage.arbre.TxtAfficheur;

public class Exemple2 {
	private Generation g;
	public Exemple2() {
		Prog prog = new Prog();
        TDS tds = new TDS();
        SymboleFonction main = new SymboleFonction("main", Type.VOID, Categ.FONCTION);
        Symbole i = new SymboleVariable("i", Type.INT, Categ.GLOBAL, 10);
        Symbole j = new SymboleVariable("j", Type.INT, Categ.GLOBAL, 20);
        Symbole k = new SymboleVariable("k", Type.INT, Categ.GLOBAL);
        Symbole l = new SymboleVariable("l", Type.INT, Categ.GLOBAL);
        
        tds.ajouterSymbole(main);
        tds.ajouterSymbole(i);
        tds.ajouterSymbole(j);
        tds.ajouterSymbole(k);
        tds.ajouterSymbole(l);

		Fonction principal = new Fonction(main);
	    prog.ajouterUnFils(principal);
	    g = new Generation(prog, tds);
	    
	}
	public String toString() {
        
		return g.afficherCodeAsm();

	}
}
