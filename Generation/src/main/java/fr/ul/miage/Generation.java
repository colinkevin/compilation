package fr.ul.miage;

import fr.ul.miage.Symbole.Categ;
import fr.ul.miage.arbre.Affectation;
import fr.ul.miage.arbre.Appel;
import fr.ul.miage.arbre.Const;
import fr.ul.miage.arbre.Division;
import fr.ul.miage.arbre.Ecrire;
import fr.ul.miage.arbre.Fonction;
import fr.ul.miage.arbre.Idf;
import fr.ul.miage.arbre.Inferieur;
import fr.ul.miage.arbre.Multiplication;
import fr.ul.miage.arbre.Noeud;
import fr.ul.miage.arbre.Noeud.Categories;
import fr.ul.miage.arbre.Plus;
import fr.ul.miage.arbre.Retour;
import fr.ul.miage.arbre.Si;
import fr.ul.miage.arbre.Superieur;
import fr.ul.miage.arbre.TantQue;

public class Generation {
	private String res;
	private  Noeud arbre;
	private TDS tds;
	public Generation(Noeud arbre, TDS tds) {
		this.arbre = arbre;
		this.tds = tds;
		res="";
		Generer_programme(arbre, tds);
	}
	
	public void Generer_programme(Noeud a, TDS tds) {
		res=".include beta.uasm\n";
		res+=".include intio.uasm\n";
		res+=".options tity\n";
		res+="CMOVE(pile, SP)\n";
		res+="BR(debut)\n";
		res+="|data\n";
		for (Symbole symbole : tds.getTds()) {
			if (symbole.getCat().equals(Categ.GLOBAL)) {
				res+=symbole.getNom()+": LONG("+((SymboleVariable) symbole).getVal()+")\n";
			}
		}
		res+="debut: \n";
		res+="CALL(main)\n";
		res+="HALT()\n";

		for (Noeud fils : arbre.getFils()) {
				if(fils.getCat().equals(Categories.FONCTION)) {
					genererFonction((Fonction) fils);
				}
				
		}
		res+="pile:\n";
	}

	public void genererFonction(Fonction arbre) {
		SymboleFonction symbTds = (SymboleFonction) arbre.getValeur();
		String label=symbTds.getNom();
		res+=label+":\n";
		for (Symbole symb : tds.getTds()) {
			if (symb.getCat().equals(Categ.LOCAL)) {
				SymboleVariable variable = (SymboleVariable) symb;
				res+="|data\n";

				if(variable.getScope().equals(label)) {
					res+=variable.getNom()+": LONG("+(variable).getVal()+")\n";

				}
			}
		}
		res+="PUSH(LP)\n";
		res+="PUSH(BP)\n";
		res+="MOVE(SP,BP)\n";
		int nbloc = symbTds.getnBloc();
		res+="ALLOCATE("+nbloc+")\n";
		for (Noeud fils : arbre.getFils()) {
			genererInstructions(fils);
		}
		res+="ret_"+symbTds.getNom()+":\n";
		res+="DEALLOCATE("+nbloc+")\n";
		res+="POP(BP)\n";
		res+="POP(LP)\n";
		res+="RTN()\n";
	}

	private void genererExpression(Noeud arbre) {
		switch (arbre.getCat()) {
		case CONST:
				Const constante = (Const) arbre;
				res+="CMOVE(" +constante.getValeur()+ ",R0)\n";
				res+="PUSH(R0)\n";
			break;
		case IDF:
			Idf idf = (Idf) arbre;
			SymboleVariable symb = (SymboleVariable) idf.getValeur();
			if(symb.getCat().equals(Categ.GLOBAL)) {
				res+= "LD(" +symb.getNom() + ", R0)\n";
				res += "PUSH(R0)\n";
}
			else if(symb.getCat().equals(Categ.PARAM)) {
				res+= "GETFRAME((2+"+symb.getRang()+")*(-4), R0)\n";
				res += "PUSH(R0)\n";
			}
			else {
				res+= "GETFRAME((1+"+symb.getRang()+")*(4), R0)\n";
				res += "PUSH(R0)\n";
			}
			
			break;
		case PLUS:
			genererPlus((Plus) arbre);
			break;
		case MUL:
			genererMultiplication((Multiplication) arbre);
			break;
		case DIV:
			genererDivision((Division) arbre);
			break;
		case APPEL:
			genererAppel((Appel) arbre);
			break;
		case LIRE:
			genererLire(arbre);
			break;
		default:
			break;
		}

	}

	public String afficherCodeAsm() {
		return res;
	}

	public void genererInstructions(Noeud arbre) {
		
		switch (arbre.getCat()) {
		case AFF:
			genererAffectation((Affectation) arbre);
			break;
		case SI:
			genererSi((Si)arbre);
			break;
		case TQ:
			genererTantQue((TantQue)arbre);
			break;
		case ECR:
			genererEcrire((Ecrire) arbre);
			break;
		case RET:
			genererRetour((Retour) arbre);
			break;
		case APPEL:
			genererAppel((Appel) arbre);
			break;
		default:
			break;
		}
	}
	public void genererAppel(Appel arbre) {
		SymboleFonction symb = (SymboleFonction) arbre.getValeur();
		if(!symb.getType().equals(Type.VOID.toString())) {
			res+="ALLOCATE(1)\n";
		}
		for (Noeud fils : arbre.getFils()) {
			genererExpression(fils);
		}
		res+="CALL("+symb.getNom()+")\n";
		int n = symb.getnBloc();
		res+="DEALLOCATE("+n+")\n";
	}
	private void genererLire(Noeud arbre) {
		res+="RDINT()\n";
		res+="PUSH(R0)\n";
	}
	private void genererEcrire(Ecrire arbre) {
		genererExpression(arbre.getLeFils());
		res+="POP(R0)\n";
		res+="WRINT()\n";
	}
	private void genererAffectation(Affectation arbre) {
		Idf fg = (Idf) arbre.getFilsGauche();
		SymboleVariable symb = (SymboleVariable) fg.getValeur();
		genererExpression(arbre.getFilsDroit());
		res+="POP(R0)\n";
		res+="ST(R0,"+symb.getNom()+")\n";
	}
	private void genererInferieur(Inferieur arbre) {
		
		genererExpression(arbre.getFilsGauche());
		genererExpression(arbre.getFilsDroit());
		res+="POP(R1)\n";
		res+="POP(R0)\n";
		res+="CMPLT(R0, R1, R2)\n";
		res+="PUSH(R2)\n";
	}
	private void genererSuperieur(Superieur arbre) {
		genererExpression(arbre.getFilsGauche());
		genererExpression(arbre.getFilsDroit());
		res+="POP(R1)\n";
		res+="POP(R0)\n";
		res+="CMPLT(R1, R0, R2)\n";
		res+="PUSH(R2)\n";
	}
	private void genererBloc(Noeud arbre) {
		for (Noeud fils : arbre.getFils()) {
			genererInstructions(fils);
		}
	}
	private void genererCondition(Noeud arbre) {
		if(arbre.getCat().equals(Categories.INF)) {
			genererInferieur((Inferieur) arbre);
		}
		if(arbre.getCat().equals(Categories.SUP)) {
			genererSuperieur((Superieur) arbre);
		}
	}
	private void genererSi(Si arbre) {
		
		res+="si_"+arbre.getValeur()+": \n";
		genererCondition(arbre.getFils().get(0));
		res+="POP(R0)\n";
		//if a.getFils().size()>2
		res+="BF(R0, sinon"+arbre.getValeur()+")\n";
		res+="alors:\n";
		genererBloc(arbre.getFils().get(1));
		res+="BR(finsi"+arbre.getValeur()+")\n";
		res+="sinon"+arbre.getValeur()+" :\n";
		genererBloc(arbre.getFils().get(2));
		res+="finsi"+arbre.getValeur()+": \n";
	}
	private void genererTantQue(TantQue arbre) {
		res += "BR(tantQue_" + arbre.getValeur() + ")\n";
		res+="tantQue_"+arbre.getValeur()+":\n";
		genererCondition(arbre.getFils().get(0));
		res+="POP(R0)\n";
		res+="BF(R0, fintantQue_" +arbre.getValeur() + ")\n";
		genererBloc(arbre.getFils().get(1));
		res+="BR(tantQue_" +arbre.getValeur()+")\n";
		res+="fintantQue_" +arbre.getValeur()+":\n";
	}
	private void genererPlus(Plus arbre) {
		genererExpression (arbre.getFilsGauche());
		genererExpression (arbre.getFilsDroit());
		res+="POP(R0)\n";
		res+="POP(R1)\n";
		res+="ADD(R0,R1,R2)\n";
		res+="PUSH(R2)\n";		
	}
	private void genererMultiplication(Multiplication arbre) {
		genererExpression (arbre.getFilsGauche());
		genererExpression (arbre.getFilsDroit());
		res+="POP(R0)\n";
		res+="POP(R1)\n";
		res+="MUL(R0,R1,R2)\n";
		res+="PUSH(R2)\n";
	}
	private void genererDivision(Division arbre) {
		genererExpression (arbre.getFilsGauche());
		genererExpression (arbre.getFilsDroit());
		res+="POP(R0)\n";
		res+="POP(R1)\n";
		res+="DIV(R0,R1,R2)\n";
		res+="PUSH(R2)\n";
	}

	private void genererRetour(Retour arbre) {
		SymboleFonction symb = (SymboleFonction) arbre.getValeur();
		int offset = 2+symb.getNbParam();
		if(!arbre.estFeuille()) {
			genererExpression(arbre.getLeFils());
			res+="POP(R0)\n";
			res+="PUTFRAME(R0, "+-4*offset+")\n";
		}
		res+="BR(ret_"+symb.getNom()+")\n";
	}
}
