package domino;

public class DominoLatino extends DominoGame {

    public DominoLatino(int numberOfPlayers, boolean isTeamGame, int targetPoints) {
        super(numberOfPlayers, isTeamGame, targetPoints);
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
        // Si es la primera partida, cercam jugador que compleixi la condició, posa sa fitxa i pasa el torn al següent.

        // cercam jugador amb doble 6
        for (int i = 1; i <= players.size() ; i++) {
            Player tmpPlayer = players.get(i-1);
            // Comprovam si aquest jugador compleix la condició d'esser el primer en jugar
            if ( hasDouble(6, tmpPlayer.playerTiles) ) {
                // posam la fitxa en joc i la llevam del jugador
                putTile(tmpPlayer, findPositionDouble(6, tmpPlayer.playerTiles));
                // Guardam el torn d'aquesta partida per continuar rotant a les següents
                playerTurn = i+1;
                if ( playerTurn > players.size() )
                    playerTurn = 1;
                nextRoundTurn = playerTurn;
                break;
            }
        }
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
        System.out.println("Pasa.");
        playerPassCounter++;
        return true;
        // valorar que si es partida de menys de 4 jugadors s'han d'agafar fitxes del munt
    }
}
