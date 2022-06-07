package domino;

public interface DominoGameInterface {
    boolean checkTotalWin();
    int countPoints();
    void makeFirstEverMoveTurn();
    String getGameName();
    void showGameRules();
    boolean playerCantMove();
    String tilesReverseH = "\uD83C\uDC30";
    String tilesReverseV = "\uD83C\uDC62";
}