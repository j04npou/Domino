package domino;

public interface DominoGameInterface {
    int findTrancaWinnerTeam();
    int findTrancaWinnerPlayer();
    void countPoints();
    void makeFirstEverMoveTurn();
    String getGameName();
    void showGameRules();
    void playerCantMove();
    void whoIsTheWinner();

//    String tilesReverseH = "\uD83C\uDC30";
    String tilesReverseV = "\uD83C\uDC62";
}