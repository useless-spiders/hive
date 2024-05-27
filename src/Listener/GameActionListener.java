package Listener;

import Controller.GameController;
import Model.HexCell;
import Model.Insect.Beetle;
import Model.Insect.Insect;
import Model.Move;
import Model.Player;
import Structure.HexCoordinate;
import Structure.Log;

import java.util.ArrayList;

public class GameActionListener {
    private GameController gameController;
    private Insect insect;
    private boolean isInsectButtonClicked;
    private boolean isInsectCellClicked;
    private HexCoordinate hexClicked;
    private ArrayList<HexCoordinate> playableCoordinates;

    public GameActionListener(GameController gameController) {
        this.gameController = gameController;
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
        if (this.gameController.getPlayerController().getCurrentPlayer().isAi()) {
            return;
        }
        Insect insect = cell.getTopInsect();

        if (!this.isInsectCellClicked) { //On clique sur un insecte à déplacer
            this.isInsectCellClicked = true;
            this.hexClicked = hexagon;
            // on affiche la pile
            if (this.gameController.getGrid().getGrid().get(this.hexClicked).getInsects().size() >= 2) {
                this.gameController.getDisplayGame().getDisplayStack().updateStackClickState(this.isInsectCellClicked, this.hexClicked);
            }

            if (insect.getPlayer().equals(this.gameController.getPlayerController().getCurrentPlayer())) {
                this.playableCoordinates = insect.getPossibleMovesCoordinates(this.hexClicked, this.gameController.getGrid());
                if (this.playableCoordinates.isEmpty() && !this.gameController.getPlayerController().getCurrentPlayer().isBeePlaced()) {
                    Log.addMessage("Aucun déplacement autorisé car l'abeille n'est pas sur le plateau");
                }
                // rendre transparente la case
                this.gameController.getDisplayGame().getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);

            } else {
                Log.addMessage("Ce pion ne vous appartient pas");
                if (this.gameController.getGrid().getGrid().get(this.hexClicked).getInsects().size() < 2) { //Si c'est une pile ennemie
                    this.isInsectCellClicked = false; //On déselectionne la pile ennemie affichée
                }
            }

        } else {
            HexCell cellClicked = this.gameController.getGrid().getCell(this.hexClicked);
            if (cellClicked.getTopInsect().getClass() == Beetle.class && !hexagon.equals(this.hexClicked)) { //On clique sur un insecte cible d'un scarabée
                this.handleInsectMoved(hexagon);
            } else { //On clique sur un insecte déjà sélectionné
                //On retire la transparence du pion/pile et l'affichage de la pile
                this.isInsectCellClicked = false;
                this.gameController.getDisplayGame().getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
                this.gameController.getDisplayGame().getDisplayStack().updateStackClickState(isInsectCellClicked, hexClicked);
                this.playableCoordinates.clear();
            }
        }
    }

    public void handleInsectMoved(HexCoordinate hexagon) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.gameController.getPlayerController().getCurrentPlayer().isAi()) {
            return;
        }
        if (this.playableCoordinates.contains(hexagon)) {
            HexCell cellClicked = this.gameController.getGrid().getCell(this.hexClicked);
            Insect movedInsect = cellClicked.getTopInsect();
            Move move = new Move(movedInsect, this.hexClicked, hexagon);

            this.gameController.getGrid().applyMove(move, this.gameController.getPlayerController().getCurrentPlayer());
            this.isInsectCellClicked = false;
            this.gameController.getPlayerController().switchPlayer();
            this.gameController.getHistoryController().addMove(move);
        } else {
            Log.addMessage("Déplacement impossible");
        }
        //On retire la transparence du pion/pile et l'affichage de la pile
        this.isInsectCellClicked = false;
        this.gameController.getDisplayGame().getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
        this.gameController.getDisplayGame().getDisplayStack().updateStackClickState(this.isInsectCellClicked, this.hexClicked);
        this.playableCoordinates.clear();
    }

    public void handleInsectPlaced(HexCoordinate hexagon) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.gameController.getPlayerController().getCurrentPlayer().isAi()) {
            return;
        }
        if (this.playableCoordinates.contains(hexagon)) {

            Move move = new Move(this.insect, null, hexagon);
            this.gameController.getGrid().applyMove(move, this.gameController.getPlayerController().getCurrentPlayer());

            //Modifier le compteur des boutons
            this.gameController.getDisplayGame().getDisplayBankInsects().updateAllLabels();

            this.gameController.getPlayerController().switchPlayer();
            this.gameController.getHistoryController().addMove(move);
        } else {
            Log.addMessage("placement impossible !");
        }
        this.isInsectButtonClicked = false;
        this.gameController.getDisplayGame().getDisplayBankInsects().updateButtonClickState(isInsectButtonClicked);
        this.playableCoordinates.clear();
    }

    public void clicInsectButton(Class<? extends Insect> insectClass, Player player) {
        // Bloque les interactions avec l'interface si c'est l'IA qui joue
        if (this.gameController.getPlayerController().getCurrentPlayer().isAi()) {
            return;
        }
        if (this.isInsectButtonClicked) { //On désélectionne un bouton
            this.isInsectButtonClicked = false;
            this.playableCoordinates.clear();

        } else { //On clique sur un bouton
            this.isInsectButtonClicked = true;
            this.isInsectCellClicked = false;

            Log.addMessage(this.gameController.getPlayerController().getCurrentPlayer().getName() + " " + this.gameController.getPlayerController().getCurrentPlayer().getColor() + " " + this.gameController.getPlayerController().getCurrentPlayer().isAi() + " --- " + player.getName() + " " + player.getColor() + " " + player.isAi());

            if (this.gameController.getPlayerController().getCurrentPlayer().equals(player)) {
                this.insect = this.gameController.getPlayerController().getCurrentPlayer().getInsect(insectClass);
                this.playableCoordinates = this.gameController.getMoveController().generatePlayableInsertionCoordinates(insectClass, this.gameController.getPlayerController().getCurrentPlayer());
                if (this.playableCoordinates.isEmpty()) {
                    this.isInsectButtonClicked = false;
                }
            } else {
                Log.addMessage("Pas le bon joueur !");
            }
        }
        this.gameController.getDisplayGame().getDisplayHexGrid().updateInsectClickState(this.isInsectCellClicked, this.hexClicked);
        this.gameController.getDisplayGame().getDisplayBankInsects().updateButtonClickState(this.isInsectButtonClicked);
        this.gameController.getDisplayGame().repaint();
    }
}
