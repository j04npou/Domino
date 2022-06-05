package domino;

public class DominoLatino extends DominoGame {

    public DominoLatino(int numberOfPlayers, boolean isTeamGame, int targetPoints) {
        super(numberOfPlayers, isTeamGame, targetPoints);
    }

    @Override
    public boolean canBeTheFirstPlayer(Player player) {
        return false;
    }

    @Override
    public boolean checkTotalWin() {
        return false;
    }

    @Override
    public int countPoints() {
        return 0;
    }

    @Override
    public void makeFirstEverMoveTurn() {

    }

    @Override
    public String getGameName() {
        return null;
    }

    @Override
    public void showGameRules() {

    }

    @Override
    public boolean playerCantMove() {
        return false;
    }
}
