package fr.ul.miage;

import fr.ul.miage.Symbole.Categ;
import fr.ul.miage.arbre.Affectation;
import fr.ul.miage.arbre.Bloc;
import fr.ul.miage.arbre.Const;
import fr.ul.miage.arbre.Ecrire;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Idf;
import fr.ul.miage.arbre.Lire;
import fr.ul.miage.arbre.Prog;
import fr.ul.miage.arbre.Si;
import fr.ul.miage.arbre.Superieur;

public class Exemple5 {
	private Generation g;
	public Exemple5() {
		// exemple #5 
        
        // on crée les noeuds 
		   Prog prog = new Prog();
	       TDS tds = new TDS();
	       Symbole main = new SymboleFonction("main", Type.VOID, Categ.FONCTION);
	       Symbole i = new SymboleVariable("i", Type.INT, Categ.GLOBAL);
	       
	       tds.ajouterSymbole(main);
	       tds.ajouterSymbole(i);

	       Fonction principal = new Fonction(main);
	       Affectation aff = new Affectation();
	       Idf idfI = new Idf(i);
	       Lire lire = new Lire();
	       Const const1 = new Const(10);
	       Const const2 = new Const(1);
	       Const const3 = new Const(2);
	       Superieur sup = new Superieur();
	       Si si1 = new Si(3);
	       Ecrire ecrire = new Ecrire();
	       Ecrire ecrire2 = new Ecrire();
	       Bloc bloc1 = new Bloc();
	       Bloc bloc2 = new Bloc();

        // on lie les noeuds 
        prog.ajouterUnFils(principal);
        principal.ajouterUnFils(aff); 
        principal.ajouterUnFils(si1); 
        aff.setFilsGauche(idfI);
        aff.setFilsDroit(lire);
        si1.setCondition(sup);
        sup.setFilsGauche(idfI);
        sup.setFilsDroit(const1);
        si1.setBlocAlors(bloc1);
        bloc1.ajouterUnFils(ecrire);
        ecrire.ajouterUnFils(const2);
        si1.setBlocSinon(bloc2);
        bloc2.ajouterUnFils(ecrire);
        ecrire2.ajouterUnFils(const3);
        g = new Generation(prog, tds);

	}
	public String toString() {
        
		return g.afficherCodeAsm();

	}
}
