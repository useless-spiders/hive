package Controleur;

import Modele.Grille;

public abstract class IA
{
    Grille grille;

    public static IA nouvelle(Jeu j, String ia) 
	{
		IA resultat = null;
		switch (ia) {
			case "Aleatoire":
				resultat = new IAAleatoire(j);
				break;
			case "Difficile":
				resultat = new IADifficile(j);
				break;
			default:
				System.out.println("IA de type " + ia + " non supportée");
		}
		return resultat;
	}

	void joue() 
	{

	}

	void joue(int precedentX, int precedentY)
	{
		joue();
	}
}
