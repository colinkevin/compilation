package fr.ul.miage;

import fr.ul.miage.Symbole.Categ;
import fr.ul.miage.arbre.Affectation;
import fr.ul.miage.arbre.Appel;
import fr.ul.miage.arbre.Const;
import fr.ul.miage.arbre.Ecrire;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Idf;
import fr.ul.miage.arbre.Plus;
import fr.ul.miage.arbre.Prog;
import fr.ul.miage.arbre.TxtAfficheur;

public class Exemple7 {
	private Generation g;


	public Exemple7() {
		// exemple #7
        
		TDS tds = new TDS();
	    SymboleFonction main = new SymboleFonction("main", Type.VOID, Categ.FONCTION);
	    SymboleFonction f = new SymboleFonction("f", Type.VOID, Categ.FONCTION, 1, 2);

	    SymboleVariable a = new SymboleVariable("a", Type.INT, Categ.GLOBAL, 10);
	    SymboleVariable i = new SymboleVariable("i", Type.INT, Categ.PARAM, 0, "f");
	    SymboleVariable x = new SymboleVariable("x", Type.INT, Categ.LOCAL, 0, "f");
	    SymboleVariable y = new SymboleVariable("y", Type.INT, Categ.LOCAL, 1, "f");


	    tds.ajouterSymbole(main);
	    tds.ajouterSymbole(f);
	    tds.ajouterSymbole(a);
	    tds.ajouterSymbole(i);
	    tds.ajouterSymbole(x);
	    tds.ajouterSymbole(y);
        // on crée les noeuds 
        Prog prog = new Prog();
        Fonction principal = new Fonction(main);

        Fonction foncF = new Fonction(f);
        Affectation aff = new Affectation();
        Idf idfX = new Idf(x);
        Const const1 = new Const(1);
        Affectation aff2 = new Affectation();
        Idf idfY = new Idf(y);
        Affectation aff3 = new Affectation();
        Idf idfA = new Idf(a);
        Plus plus = new Plus();
        Plus plus2 = new Plus();
        Idf idfI = new Idf(i);
        Const const2 = new Const(3);
        Appel app = new Appel(f);
        Ecrire ecrire = new Ecrire();

        // on lie les noeuds 
        prog.ajouterUnFils(foncF);
        foncF.ajouterUnFils(aff);
        aff.setFilsGauche(idfX);
        aff.setFilsDroit(const1);
        foncF.ajouterUnFils(aff2);
        aff2.setFilsGauche(idfY);
        aff2.setFilsDroit(const1);
        foncF.ajouterUnFils(aff3);
        aff3.setFilsGauche(idfA);
        aff3.setFilsDroit(plus);
        plus.setFilsGauche(idfI);
        plus.setFilsDroit(plus2);
        plus2.setFilsGauche(idfX);
        plus2.setFilsDroit(idfY);

        prog.ajouterUnFils(principal);
        principal.ajouterUnFils(app);
        app.ajouterUnFils(const2);
        principal.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(idfA);
        g = new Generation(prog, tds);

	}
	public String toString() {
        
		return g.afficherCodeAsm();

	}
}
