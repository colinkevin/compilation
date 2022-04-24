package fr.ul.miage;

import fr.ul.miage.Symbole.Categ;
import fr.ul.miage.arbre.Affectation;
import fr.ul.miage.arbre.Ecrire;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Idf;
import fr.ul.miage.arbre.Lire;
import fr.ul.miage.arbre.Plus;
import fr.ul.miage.arbre.Prog;
import fr.ul.miage.arbre.TxtAfficheur;

public class Exemple4 {
	private Generation g;

	public Exemple4() {
		 // exemple #4 
       // on crée les noeuds 
       Prog prog = new Prog();
       TDS tds = new TDS();
       SymboleFonction main = new SymboleFonction("main", Type.VOID, Categ.FONCTION);
       SymboleVariable i = new SymboleVariable("i", Type.INT, Categ.GLOBAL);
       SymboleVariable j = new SymboleVariable("j", Type.INT, Categ.GLOBAL, 20);
       
       tds.ajouterSymbole(main);
       tds.ajouterSymbole(i);
       tds.ajouterSymbole(j);

       Fonction principal = new Fonction(main);
       Affectation aff = new Affectation();
       Idf idfI = new Idf(i);
       Idf idfJ = new Idf(j);
       Lire lire = new Lire();
       Ecrire ecrire = new Ecrire();
       Plus plus = new Plus();
       // on lie les noeuds 
       prog.ajouterUnFils(principal);
       principal.ajouterUnFils(aff); 
       principal.ajouterUnFils(ecrire); 
       aff.setFilsGauche(idfI);
       aff.setFilsDroit(lire);
       ecrire.setLeFils(plus);
       plus.setFilsGauche(idfI);
       plus.setFilsDroit(idfJ);
       
       g = new Generation(prog, tds);

	}
	public String toString() {
        
		return g.afficherCodeAsm();

	}
}
