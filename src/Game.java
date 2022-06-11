import domino.DominoGame;
import domino.DominoLatino;

public class Game {
    public static void main(String[] args) {
        newGame();
    }

    public static void newGame() {
        DominoGame domino = new DominoLatino(3,false,100 );
        domino.gameplay();
    }
}
