package Modele.Ia;

import java.util.ArrayList;

import Controleur.Game;
import Modele.HexGrid;
import Modele.Move;
import Modele.Player;
import Modele.Insect.Insect;
import Structures.HexCoordinate;
import Structures.Log;

public abstract class Ia
{
    Player us;
    HexGrid Grid;

    public static Ia nouvelle(Game g, String ia, Player p) 
	{
		Ia resultat = null;
		switch (ia) {
			case "Aleatoire":
				resultat = new IaAleatoire(g, p);
				break;
			default:
				Log.addMessage("IA de type " + ia + " non supportée");
		}
		return resultat;
	}

	Move chooseMove() 
	{
		return null;
	}

	 public void playMove()
	{
		chooseMove();
	}
}
