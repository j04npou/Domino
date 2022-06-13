package domino;

public class DominoLatino extends DominoGame {

    public DominoLatino(int numberOfPlayers, boolean isTeamGame) {
        super(numberOfPlayers, isTeamGame);
        this.targetPoints = 100;
    }

    @Override
    public int findTrancaWinnerTeam() {
        int[] teamPoints = new int[2];
        for (int i = 0; i < players.size(); i++) {
            teamPoints[players.get(i).playerTeam - 1] += players.get(i).countDots();
        }
        if (teamPoints[0] > teamPoints[1])
            return 2;
        else if (teamPoints[0] < teamPoints[1])
            return 1;
        else {
            // En cas d'empat guanya el jugador que havia començat la partida
            if (nextRoundTurn-1 < 1)
                return players.size();
            else
                return nextRoundTurn-1;
        }
    }

    @Override
    public int findTrancaWinnerPlayer() {
        int minimumPoints = players.get(0).countDots();
        int minimumPlayer = players.get(0).playerNumber;
        boolean drawFlag = false;

        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).countDots() < minimumPoints) {
                minimumPoints = players.get(i).countDots();
                minimumPlayer = players.get(i).playerNumber;
                drawFlag = false;
            } else if (players.get(i).countDots() == minimumPoints) {
                drawFlag = true;
            }
        }
        if (drawFlag)
            // Si dos jugadors han empatat, no s'assignaran els punts a ningú
            return 0;
        else
            return minimumPlayer;
    }

    @Override
    public void countPoints() {
        if (playerTurn != 0) {
            int winnerTeam = players.get(playerTurn - 1).playerTeam;
            int points = 0;

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).playerTeam != winnerTeam)
                    points += players.get(i).countDots();
            }
            players.get(winnerTeam - 1).points += points;
        }
    }

    @Override
    public void whoIsTheWinner() {
        // Mostram qui ha guanyat
        if (isTeamGame)
            InputOutput.printLN("Winner team " + players.get(playerTurn-1).playerTeam);
        else if (playerTurn == 0)
            InputOutput.printLN("DRAW, no points for anyone");
        else
            InputOutput.printLN("Winner player " + players.get(playerTurn-1).playerNumber);
    }

    @Override
    public void makeFirstEverMoveTurn() {
        // Si es la primera partida, cercam jugador que compleixi la condició, posa sa fitxa i pasa el torn al següent.

        boolean moveDone = false;

        for (int doubleTile = 6; doubleTile >= 0; doubleTile--) {
            // cercam jugador amb doble 6 o doble per ordre decreixent
            for (int i = 1; i <= players.size(); i++) {
                Player tmpPlayer = players.get(i - 1);
                // Comprovam si aquest jugador compleix la condició de ser el primer en jugar
                if (tmpPlayer.hasDouble(doubleTile)) {
                    // posam la fitxa en joc i la llevam del jugador
                    putTile(tmpPlayer, tmpPlayer.findPositionDouble(doubleTile));
                    // Guardam el torn d'aquesta partida per continuar rotant a les següents
                    nextRoundTurn = i;
                    playerTurn = i + 1;
                    if (playerTurn > players.size())
                        playerTurn = 1;
                    moveDone = true;
                    break;
                }
            }
            if (moveDone)
                break;
        }
    }

    @Override
    public String getGameName() {
        return "Domino Latino";
    }

    @Override
    public void showGameRules() {
        InputOutput.printLN( "Es pot jugar per parelles o indivdualment.\n" +
                            "L'objectius es arribar a 100 punts.\n" +
                            "Sortida: A la primera partida comença el jugador que te el doble 6 \n" +
                            "o doble inferior.\n" +
                            "Punts: La parella o el jugador guanyador acumula els punts que li \n" +
                            "queden per jugar a l'altre equip.\n" +
                            "Si hi ha empat a una tranca, a la modaditat en parelles guanya la \n" +
                            "parella que era ma. Si era un joc individual ningú guanya punts." );
    }

    @Override
    public void playerCantMove() {
        boolean tilePlaced = false;

        if (tilePool.size() > 0) {
            do {
                InputOutput.printLN("Getting tile from pool");
                getRandomTile(players.get(playerTurn-1));
                tilePlaced = poolTileCanBePlaced();
            } while (tilePool.size() > 0 && !tilePlaced);
        }

        if (!tilePlaced){
            InputOutput.printLN("Pass.");
            playerPassCounter++;
        }
    }
}
