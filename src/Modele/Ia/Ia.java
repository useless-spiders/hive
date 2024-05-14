package Modele.Ia;

import java.util.ArrayList;

import Controleur.Game;
import Modele.HexGrid;
import Modele.Move;
import Modele.Player;
import Structures.HexCoordinate;
import Structures.Log;

public abstract class Ia
{
    Player us;
    HexGrid Grid;
	ArrayList<HexCoordinate> pieces;

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

	void initPieces()
	{
		Move init = new Move(null, null, null);
		if(this.us.getColor() == "WHITE")
		{
			pieces = init.getWhitePieces();
		}
		else 
		{
			if(this.us.getColor() == "BLACK")
			{
				pieces = init.getBlackPieces();
			}
			else
			{
				Log.addMessage("couleur non blache ni noir");
			}
		}
	}

	void joue() 
	{

	}

	void joue(HexCoordinate from, HexCoordinate to)
	{
		joue();
	}
}
