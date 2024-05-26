package Listener;

import Controller.Game;
import Model.HexCell;
import Model.Insect.Beetle;
import Model.Insect.Insect;
import Model.Move;
import Model.Player;
import Structure.HexCoordinate;
import Structure.Log;

import java.util.ArrayList;

public class GameActionListener {
    private Game game;
    private Insect insect;
    private boolean isInsectButtonClicked;
    private boolean isInsectCellClicked;
    private HexCoordinate hexClicked;
    private ArrayList<HexCoordinate> playableCoordinates;

    public GameActionListener(Game game) {
        this.game = game;
        this.isInsectCellClicked = false;
        this.hexClicked = null;
        this.playableCoordinates = new ArrayList<>();
    }

    public HexCoordinate getHexClicked() {
        return this.hexClicked;
    }

    public void setHexClicked(HexCoordinate hexClicked) {
        this.hexClicked = hexClicked;
    }

    public boolean getIsInsectCellClicked() {
        return this.isInsectCellClicked;
    }

    public void setIsInsectCellClicked(boolean isInsectCellClicked) {
        this.isInsectCellClicked = isInsectCellClicked;
    }

    public boolean getIsInsectButtonClicked() {
        return this.isInsectButtonClicked;
    }

    public void setIsInsectButtonClicked(boolean isInsectButtonClicked) {
        this.isInsectButtonClicked = isInsectButtonClicked;
    }

    public ArrayList<HexCoordinate> getPlayableCoordinates() {
        return this.playableCoordinates;
    }

    public void setPlayableCoordinates(ArrayList<HexCoordinate> playableCoordinates) {
        this.playableCoordinates = playableCoordinates;
    }

    public void handleCellClicked(HexCell cell, HexCoordinate hexagon) { //Clic sur un insecte du plateau
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.game.getPlayerController().getCurrentPlayer().isAi()) {
            return;
        }
        Insect insect = cell.getTopInsect();

        if (!this.isInsectCellClicked) { //On clique sur un insecte à déplacer
            this.isInsectCellClicked = true;
            this.hexClicked = hexagon;
            // on affiche la pile
            if (this.game.getGrid().getGrid().get(this.hexClicked).getInsects().size() >= 2) {
                this.game.getDisplayGame().getDisplayStack().updateStackClickState(this.isInsectCellClicked, this.hexClicked);
            }

            if (insect.getPlayer().equals(this.game.getPlayerController().getCurrentPlayer())) {
                this.playableCoordinates = insect.getPossibleMovesCoordinates(this.hexClicked, this.game.getGrid());
                if (this.playableCoordinates.isEmpty() && !this.game.getPlayerController().getCurrentPlayer().isBeePlaced()) {
                    Log.addMessage("Aucun déplacement autorisé car l'abeille n'est pas sur le plateau");
                }
                // rendre transparente la case
                this.game.getDisplayGame().getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);

            } else {
                Log.addMessage("Ce pion ne vous appartient pas");
                if (this.game.getGrid().getGrid().get(this.hexClicked).getInsects().size() < 2) { //Si c'est une pile ennemie
                    this.isInsectCellClicked = false; //On déselectionne la pile ennemie affichée
                }
            }

        } else {
            HexCell cellClicked = this.game.getGrid().getCell(this.hexClicked);
            if (cellClicked.getTopInsect().getClass() == Beetle.class && !hexagon.equals(this.hexClicked)) { //On clique sur un insecte cible d'un scarabée
                this.handleInsectMoved(hexagon);
            } else { //On clique sur un insecte déjà sélectionné
                //On retire la transparence du pion/pile et l'affichage de la pile
                this.isInsectCellClicked = false;
                this.game.getDisplayGame().getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
                this.game.getDisplayGame().getDisplayStack().updateStackClickState(isInsectCellClicked, hexClicked);
                this.playableCoordinates.clear();
            }
        }
    }

    public void handleInsectMoved(HexCoordinate hexagon) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.game.getPlayerController().getCurrentPlayer().isAi()) {
            return;
        }
        if (this.playableCoordinates.contains(hexagon)) {
            HexCell cellClicked = this.game.getGrid().getCell(this.hexClicked);
            Insect movedInsect = cellClicked.getTopInsect();
            Move move = new Move(movedInsect, this.hexClicked, hexagon);

            this.game.getGrid().applyMove(move, this.game.getPlayerController().getCurrentPlayer());
            this.isInsectCellClicked = false;
            this.game.getPlayerController().switchPlayer();
            this.game.getHistoryController().addMove(move);
        } else {
            Log.addMessage("Déplacement impossible");
        }
        //On retire la transparence du pion/pile et l'affichage de la pile
        this.isInsectCellClicked = false;
        this.game.getDisplayGame().getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
        this.game.getDisplayGame().getDisplayStack().updateStackClickState(this.isInsectCellClicked, this.hexClicked);
        this.playableCoordinates.clear();
    }

    public void handleInsectPlaced(HexCoordinate hexagon) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.game.getPlayerController().getCurrentPlayer().isAi()) {
            return;
        }
        if (this.playableCoordinates.contains(hexagon)) {

            Move move = new Move(this.insect, null, hexagon);
            this.game.getGrid().applyMove(move, this.game.getPlayerController().getCurrentPlayer());

            //Modifier le compteur des boutons
            this.game.getDisplayGame().getDisplayBankInsects().updateAllLabels();

            this.game.getPlayerController().switchPlayer();
            this.game.getHistoryController().addMove(move);
        } else {
            Log.addMessage("placement impossible !");
        }
        this.isInsectButtonClicked = false;
        this.game.getDisplayGame().getDisplayBankInsects().updateButtonClickState(isInsectButtonClicked);
        this.playableCoordinates.clear();
    }

    public void clicInsectButton(Class<? extends Insect> insectClass, Player player) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.game.getPlayerController().getCurrentPlayer().isAi()) {
            return;
        }
        if (this.isInsectButtonClicked) { //On désélectionne un bouton
            this.isInsectButtonClicked = false;
            this.playableCoordinates.clear();

        } else { //On clique sur un bouton
            this.isInsectButtonClicked = true;
            this.isInsectCellClicked = false;

            Log.addMessage(this.game.getPlayerController().getCurrentPlayer().getName() + " " + this.game.getPlayerController().getCurrentPlayer().getColor() + " " + this.game.getPlayerController().getCurrentPlayer().isAi() + " --- " + player.getName() + " " + player.getColor() + " " + player.isAi());

            if (this.game.getPlayerController().getCurrentPlayer().equals(player)) {
                this.insect = this.game.getPlayerController().getCurrentPlayer().getInsect(insectClass);
                this.playableCoordinates = this.game.getMoveController().generatePlayableInsertionCoordinates(insectClass, this.game.getPlayerController().getCurrentPlayer());
                if (this.playableCoordinates.isEmpty()) {
                    this.isInsectButtonClicked = false;
                }
            } else {
                Log.addMessage("Pas le bon joueur !");
            }
        }
        this.game.getDisplayGame().getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
        this.game.getDisplayGame().getDisplayBankInsects().updateButtonClickState(this.isInsectButtonClicked);
        this.game.getDisplayGame().repaint();
    }
}
