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

    public String toString(){
        return "Jugador " + this.playerNumber;
    }
}
