package domino;

import java.util.ArrayList;

public class Player {
    public int playerNumber;
    public int playerTeam;
    public ArrayList<Tile> playerTiles;
    public int points;

    public Player(int playerNumber, int playerTeam) {
        this.playerNumber = playerNumber;
        this.playerTeam = playerTeam;
        this.points = 0;
        this.playerTiles = new ArrayList<>();
    }

    public int countDots() {
        int dots = 0;
        for (int i = 0; i < playerTiles.size(); i++) {
            dots += playerTiles.get(i).getTileDotsLeft() + playerTiles.get(i).getTileDotsRight();
        }
        return dots;
    }

    public boolean hasDouble(int doubleTile) {
        for (int i = 0; i < playerTiles.size() ; i++) {
            if ( playerTiles.get(i).getTileDotsLeft() == doubleTile && playerTiles.get(i).getTileDotsRight() == doubleTile ) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDouble() {
        for (int i = 0; i < playerTiles.size() ; i++) {
            if ( playerTiles.get(i).getTileDotsLeft() == playerTiles.get(i).getTileDotsRight()) {
                return true;
            }
        }
        return false;
    }

    public int findPositionDouble(int doubleTile) {
        for (int i = 0; i < playerTiles.size() ; i++) {
            if ( playerTiles.get(i).getTileDotsLeft() == doubleTile && playerTiles.get(i).getTileDotsRight() == doubleTile ) {
                return i;
            }
        }
        return -1;
    }

    public String toString(){
        return "Jugador " + this.playerNumber;
    }
}
