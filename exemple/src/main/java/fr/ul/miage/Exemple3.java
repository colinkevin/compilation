package fr.ul.miage;

import fr.ul.miage.Symbole.Categ;
import fr.ul.miage.arbre.Affectation;
import fr.ul.miage.arbre.Const;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Idf;
import fr.ul.miage.arbre.Multiplication;
import fr.ul.miage.arbre.Plus;
import fr.ul.miage.arbre.Prog;

public class Exemple3 {
	private Generation g;

	public Exemple3() {
        // exemple #3
        // on crée les noeuds 
		
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
        Affectation aff = new Affectation();
        Affectation aff2 = new Affectation();
        Idf idfI = new Idf(i);
        Idf idfJ = new Idf(j);
        Idf idfK = new Idf(k);
        Idf idfL = new Idf(l);
        
        Const const1 = new Const(2);
        Const const2 = new Const(3);
        Plus plus = new Plus();
        Multiplication mult = new Multiplication();
        // on lie les noeuds 
        prog.ajouterUnFils(principal);
        principal.ajouterUnFils(aff);
        principal.ajouterUnFils(aff2);
        aff.setFilsGauche(idfK);
        aff.setFilsDroit(const1);
        aff2.setFilsGauche(idfL);
        aff2.setFilsDroit(plus);
        plus.setFilsGauche(idfI);
        plus.setFilsDroit(mult);
        mult.setFilsGauche(const2);
        mult.setFilsDroit(idfJ);
        
        g = new Generation(prog, tds);
	}
	public String toString() {
        
		return g.afficherCodeAsm();

	}
}
