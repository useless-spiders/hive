package Modele;

import java.util.ArrayList;

import Modele.Insect.Insect;
import Structures.HexCoordinate;

public class Move {
    private Insect insect;
    private HexCoordinate previousCoor;
    private HexCoordinate newCoor;
    private ArrayList<HexCoordinate> whitePieces;
    private ArrayList<HexCoordinate> blackPieces;

    public HexCoordinate WHITE_ANT = new HexCoordinate(-50000, -50000) ;
	public HexCoordinate WHITE_BEE = new HexCoordinate(-50001, -50001) ;
	public HexCoordinate WHITE_BEETLE = new HexCoordinate(-50002, -50002) ;
	public HexCoordinate WHITE_GRASSHOPPER = new HexCoordinate(-50003, -50003) ;
	public HexCoordinate WHITE_SPIDER = new HexCoordinate(-55555, -55555) ;

	public HexCoordinate BLACK_ANT = new HexCoordinate(-60000, -60000) ;
	public HexCoordinate BLACK_BEE = new HexCoordinate(-60001, -60001) ;
	public HexCoordinate BLACK_BEETLE = new HexCoordinate(-60002, -60002) ;
	public HexCoordinate BLACK_GRASSHOPPER = new HexCoordinate(-60003, -60003) ;
	public HexCoordinate BLACK_SPIDER = new HexCoordinate(-66666, -66666) ;

    public Move(Insect insect, HexCoordinate previousCoor, HexCoordinate newCoor) {
        this.insect = insect;
        this.previousCoor = previousCoor;
        this.newCoor = newCoor;
        initPieces();
    }

    void initPieces()
    {
        whitePieces = new ArrayList<>();

        whitePieces.add(WHITE_ANT);
        whitePieces.add(WHITE_BEE);
        whitePieces.add(WHITE_BEETLE);
        whitePieces.add(WHITE_GRASSHOPPER);
        whitePieces.add(WHITE_SPIDER);

        blackPieces = new ArrayList<>();

        blackPieces.add(BLACK_ANT);
        blackPieces.add(BLACK_BEE);
        blackPieces.add(BLACK_BEETLE);
        blackPieces.add(BLACK_GRASSHOPPER);
        blackPieces.add(BLACK_SPIDER);
    }

    public ArrayList<HexCoordinate> getWhitePieces()
    {
        return this.whitePieces;
    }

    public ArrayList<HexCoordinate> getBlackPieces()
    {
        return this.blackPieces;
    }

    public Insect getInsect() {
        return insect;
    }

    // if null, then the insect is being placed
    public HexCoordinate getPreviousCoor() {
        return previousCoor;
    }

    public HexCoordinate getNewCoor() {
        return newCoor;
    }
}