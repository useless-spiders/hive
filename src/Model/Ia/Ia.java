package Model.Ia;

import Controller.Game;
import Model.HexGrid;
import Structure.HexCoordinate;
import Structure.Log;

public abstract class Ia
{
    
    HexGrid Grid;

    public static Ia nouvelle(Game g, String ia) 
	{
		Ia resultat = null;
		switch (ia) {
			case "Aleatoire":
				resultat = new IaAleatoire(g);
				break;
			default:
				Log.addMessage("IA de type " + ia + " non supportée");
		}
		return resultat;
	}

	void joue() 
	{

	}

	void joue(HexCoordinate from, HexCoordinate to)
	{
		joue();
	}
}
