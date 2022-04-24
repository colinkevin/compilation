package fr.ul.miage;

import fr.ul.miage.Symbole.Categ;
import fr.ul.miage.arbre.Affectation;
import fr.ul.miage.arbre.Bloc;
import fr.ul.miage.arbre.Const;
import fr.ul.miage.arbre.Ecrire;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Idf;
import fr.ul.miage.arbre.Inferieur;
import fr.ul.miage.arbre.Plus;
import fr.ul.miage.arbre.Prog;
import fr.ul.miage.arbre.TantQue;
import fr.ul.miage.arbre.TxtAfficheur;

public class Exemple6 {
	private Generation g;
	public Exemple6() {
		// exemple #6 
        
		TDS tds = new TDS();
	    Symbole main = new SymboleFonction("main", Type.VOID, Categ.FONCTION);
	    Symbole i = new SymboleVariable("i", Type.INT, Categ.GLOBAL);
	    Symbole n = new SymboleVariable("n", Type.INT, Categ.GLOBAL, 5);
  
	    tds.ajouterSymbole(main);
	    tds.ajouterSymbole(i);
	    tds.ajouterSymbole(n);

        // on crée les noeuds 
        Prog prog = new Prog();
        Fonction principal = new Fonction(main);
        Affectation aff = new Affectation();
        Idf idfI = new Idf(i);
        Const const1 = new Const(0);
        TantQue tq = new TantQue(1);
        Inferieur inf = new Inferieur();
        Idf idfN = new Idf(n);
        Bloc bloc1 = new Bloc();
        Ecrire ecrire = new Ecrire();
        Affectation aff2 = new Affectation();
        Plus plus = new Plus();
        Const const2 = new Const(1);
        
        // on lie les noeuds 
        prog.ajouterUnFils(principal);
        principal.ajouterUnFils(aff);
        principal.ajouterUnFils(tq);
        aff.setFilsGauche(idfI);
        aff.setFilsDroit(const1);
        tq.setCondition(inf);
        inf.setFilsGauche(idfI);
        inf.setFilsDroit(idfN);
        tq.setBloc(bloc1);
        bloc1.ajouterUnFils(ecrire);
        bloc1.ajouterUnFils(aff2);
        ecrire.ajouterUnFils(idfI);
        aff2.setFilsGauche(idfI);
        aff2.setFilsDroit(plus);
        plus.setFilsGauche(idfI);
        plus.setFilsDroit(const2);
        g = new Generation(prog, tds);

	}
	public String toString() {
        
		return g.afficherCodeAsm();

	}
}
