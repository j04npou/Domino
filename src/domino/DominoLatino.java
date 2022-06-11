package domino;

public class DominoLatino extends DominoGame {

    public DominoLatino(int numberOfPlayers, boolean isTeamGame, int targetPoints) {
        super(numberOfPlayers, isTeamGame, targetPoints);
    }

    @Override
    public int findTrancaWinner() {
        int[] teamPoints = new int[2];
        for (int i = 0; i < players.size(); i++) {
            teamPoints[players.get(i).playerTeam - 1] += players.get(i).countDots();
        }
        if (teamPoints[0] > teamPoints[1])
            return 2;
        else if (teamPoints[0] < teamPoints[1])
            return 1;
        else {
            if (nextRoundTurn-1 < 1)
                return players.size();
            else
                return nextRoundTurn-1;
        }
    }

    @Override
    public void countPoints() {
        int winnerTeam = players.get(playerTurn-1).playerTeam;
        int points = 0;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).playerTeam != winnerTeam)
                points += players.get(i).countDots();
        }
        players.get(winnerTeam-1).points += points;
    }

    @Override
    public void makeFirstEverMoveTurn() {
        // Si es la primera partida, cercam jugador que compleixi la condició, posa sa fitxa i pasa el torn al següent.

        // cercam jugador amb doble 6
        for (int i = 1; i <= players.size() ; i++) {
            Player tmpPlayer = players.get(i-1);
            // Comprovam si aquest jugador compleix la condició d'esser el primer en jugar
            if ( tmpPlayer.hasDouble(6) ) {
                // posam la fitxa en joc i la llevam del jugador
                putTile(tmpPlayer, tmpPlayer.findPositionDouble(6));
                // Guardam el torn d'aquesta partida per continuar rotant a les següents
                nextRoundTurn = i;
                playerTurn = i+1;
                if ( playerTurn > players.size() )
                    playerTurn = 1;
                break;
            }
        }
    }

    @Override
    public String getGameName() {
        return "Domino Latino";
    }

    @Override
    public void showGameRules() {
        System.out.println("Només per parelles...");
    }

    @Override
    public boolean playerCantMove() {
        System.out.println("Pasa.");
        playerPassCounter++;
        return true;
        // valorar que si es partida de menys de 4 jugadors s'han d'agafar fitxes del munt
    }
}
