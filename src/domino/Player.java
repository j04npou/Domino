package domino;

import java.util.ArrayList;

public class Player {
    protected int playerNumber;
    protected int playerTeam;
    protected ArrayList<Tile> playerTiles;
    protected int points;

    public Player(int playerNumber, int playerTeam) {
        this.playerNumber = playerNumber;
        this.playerTeam = playerTeam;
        this.points = 0;
        this.playerTiles = new ArrayList<>();
    }

    protected int countDots() {
        int dots = 0;
        for (int i = 0; i < playerTiles.size(); i++) {
            dots += playerTiles.get(i).getTileDotsLeft() + playerTiles.get(i).getTileDotsRight();
        }
        return dots;
    }

    protected boolean hasDouble(int doubleTile) {
        for (int i = 0; i < playerTiles.size() ; i++) {
            if ( playerTiles.get(i).getTileDotsLeft() == doubleTile && playerTiles.get(i).getTileDotsRight() == doubleTile ) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasAnyDouble() {
        // Comprovem si te qualsevol doble
        for (int i = 0; i < playerTiles.size() ; i++) {
            if ( playerTiles.get(i).getTileDotsLeft() == playerTiles.get(i).getTileDotsRight()) {
                return true;
            }
        }
        return false;
    }

    protected int findPositionDouble(int doubleTile) {
        for (int i = 0; i < playerTiles.size() ; i++) {
            if ( playerTiles.get(i).getTileDotsLeft() == doubleTile && playerTiles.get(i).getTileDotsRight() == doubleTile ) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString(){
        return "Jugador " + this.playerNumber;
    }
}
