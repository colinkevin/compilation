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
import fr.ul.miage.arbre.Retour;
import fr.ul.miage.arbre.TxtAfficheur;

public class Exemple8 {
	private Generation g;

	public Exemple8() {
		// exemple #8
		TDS tds = new TDS();
	    SymboleFonction main = new SymboleFonction("main", Type.VOID, Categ.FONCTION);
	    SymboleFonction f = new SymboleFonction("f", Type.VOID, Categ.FONCTION, 2, 1);

	    SymboleVariable a = new SymboleVariable("a", Type.INT, Categ.GLOBAL);
	    SymboleVariable i = new SymboleVariable("i", Type.INT, Categ.PARAM, 0, "f");
	    SymboleVariable x = new SymboleVariable("x", Type.INT, Categ.LOCAL, 0, "f");
	    SymboleVariable j = new SymboleVariable("j", Type.INT, Categ.PARAM, 1, "f");


	    tds.ajouterSymbole(main);
	    tds.ajouterSymbole(f);
	    tds.ajouterSymbole(a);
	    tds.ajouterSymbole(i);
	    tds.ajouterSymbole(x);
	    tds.ajouterSymbole(j);
        // on crée les noeuds 
        Prog prog = new Prog();
        Fonction fonc = new Fonction(f);
        Affectation aff = new Affectation();
        Idf idfX = new Idf(x);
        Plus plus = new Plus();
        Idf idfI = new Idf(i);
        Idf idfJ = new Idf(j);
        Retour rt = new Retour(f);
        Plus plus2 = new Plus();
        Const const1 = new Const(10);
        Fonction principal = new Fonction(main);
        Affectation aff2 = new Affectation();
        Idf idfA = new Idf(a);
        Appel app = new Appel(f);
        Const const2 = new Const(1);
        Const const3 = new Const(2);
        Ecrire ecrire = new Ecrire();

        // on lie les noeuds 
        prog.ajouterUnFils(fonc);
        fonc.ajouterUnFils(aff);
        aff.setFilsGauche(idfX);
        aff.setFilsDroit(plus);
        plus.setFilsGauche(idfI);
        plus.setFilsDroit(idfJ);
        fonc.ajouterUnFils(rt);
        rt.setLeFils(plus2);
        plus2.setFilsGauche(idfX);
        plus2.setFilsDroit(const1);
        
        prog.ajouterUnFils(principal);
        principal.ajouterUnFils(aff2);
        aff2.setFilsGauche(idfA);
        aff2.setFilsDroit(app);
        app.ajouterUnFils(const2);
        app.ajouterUnFils(const3);
        principal.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(idfA);
        g = new Generation(prog, tds);

	}
	public String toString() {
        
		return g.afficherCodeAsm();

	}
}
