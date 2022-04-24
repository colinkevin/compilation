package fr.ul.miage;

import fr.ul.miage.Symbole.Categ;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Prog;

public class Exemple1 {
	private Generation g;
	public Exemple1() {
		Prog prog = new Prog();
        TDS tds = new TDS();
        SymboleFonction main = new SymboleFonction("main", Type.VOID, Categ.FONCTION);
        tds.ajouterSymbole(main);
        Fonction principal = new Fonction(main);
        prog.ajouterUnFils(principal);
        g = new Generation(prog, tds);
}
	public String toString() {
        
		return g.afficherCodeAsm();

	}
}
