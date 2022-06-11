package domino;

public interface DominoGameInterface {
    int findTrancaWinner();
    void countPoints();
    void makeFirstEverMoveTurn();
    String getGameName();
    void showGameRules();
    boolean playerCantMove();
    String tilesReverseH = "\uD83C\uDC30";
    String tilesReverseV = "\uD83C\uDC62";
}