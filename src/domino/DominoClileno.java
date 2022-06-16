package domino;

import java.util.ArrayList;
import java.util.Random;

public class DominoClileno extends DominoGame {

    public DominoClileno(int numberOfPlayers, boolean isTeamGame) {
        super(numberOfPlayers, isTeamGame);
        this.targetPoints = 121;
    }

    @Override
    public int findTrancaWinnerTeam() {
        int[] teamPoints = new int[2];
        for (int i = 0; i < players.size(); i++) {
            teamPoints[players.get(i).playerTeam - 1] += players.get(i).countDots();
        }
        if (teamPoints[0] > teamPoints[1])
            return 1;
        else if (teamPoints[0] < teamPoints[1])
            return 2;
        else {
            // En cas d'empat es tria un equip aleatoriament
            Random rand = new Random();
            return rand.nextInt(2)+1;
        }
    }

    @Override
    public int findTrancaWinnerPlayer() {
        int maxPoints = players.get(0).countDots();
        int maxPlayer = players.get(0).playerNumber;
        boolean drawFlag = false;

        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).countDots() > maxPoints) {
                maxPoints = players.get(i).countDots();
                maxPlayer = players.get(i).playerNumber;
                drawFlag = false;
            } else if (players.get(i).countDots() == maxPoints) {
                drawFlag = true;
            }
        }
        if (drawFlag) {
            // Si dos jugadors han empatat, es tria un jugador aleatoriament
            ArrayList<Integer> worstPlayers = new ArrayList<>();

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).countDots() == maxPoints) {
                    worstPlayers.add(players.get(i).playerNumber);
                }
            }
            Random rand = new Random();
            return worstPlayers.get(rand.nextInt(worstPlayers.size()));
        } else
            return maxPlayer;
    }

    @Override
    public void countPoints() {
        int totalDots = 0;

        if (playerPassCounter >= players.size()) {
            for (int i = 1; i < players.size(); i++) {
                totalDots += players.get(i).countDots();
            }
            players.get(playerTurn - 1).points += totalDots;
        } else
            if (isTeamGame) {
                for (int i = 1; i < players.size(); i++) {
                    players.get(players.get(i).playerTeam-1).points += players.get(i).countDots();
                }
            } else
                for (int i = 1; i < players.size(); i++) {
                    players.get(i).points += players.get(i).countDots();
                }
    }

    @Override
    public void whoIsTheWinner() {
        // Mostram qui ha guanyat
        if (playerPassCounter >= players.size()) {
            InputOutput.print("Tranca ending: ");
            if (isTeamGame)
                InputOutput.printLN("Looser team " + players.get(playerTurn - 1).playerTeam);
            else
                InputOutput.printLN("Looser player " + players.get(playerTurn - 1).playerNumber);
        } else {
            if (isTeamGame)
                InputOutput.printLN("Winner team " + players.get(playerTurn - 1).playerTeam);
            else
                InputOutput.printLN("Winner player " + players.get(playerTurn - 1).playerNumber);
        }
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
        return "Domino Chileno";
    }

    @Override
    public void showGameRules() {
        InputOutput.printLN( "Es pot jugar per parelles o indivdualment.\n" +
                            "La partida acaba quan algú arriba als 121 punts y guanya el que te menys punts.\n" +
                            "Sortida: A la primera partida comença el jugador que te el doble 6 \n" +
                            "o doble inferior.\n" +
                            "Punts: En cas de tranca, la parella o el jugador amb més punts no jugats acumula els punts que \n" +
                            "queden per jugar a tots els jugadors.\n" +
                            "Si no hi ha tranca, cada jugador acumula els seus propis punts no jugats." +
                            "Si hi ha empat a una tranca, es tria a atzar el 'perdedor'." );
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

    @Override
    protected boolean checkTotalWin() {
        boolean endGameFlag = false;
        int minPoints = players.get(0).countDots();
        int minPlayer = players.get(0).playerNumber;

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).points >= targetPoints) {
                endGameFlag = true;
            }
        }

        if (endGameFlag) {
            for (int i = 1; i < players.size(); i++) {
                if (players.get(i).countDots() < minPoints) {
                    minPoints = players.get(i).countDots();
                    minPlayer = players.get(i).playerNumber;
                }
            }
            showTeams();
            if (isTeamGame)
                InputOutput.printLN("Team " + players.get(minPlayer-1).playerTeam + " WINS");
            else
                InputOutput.printLN("Player " + minPlayer + " WINS");
            return true;
        } else
            return false;
    }
}
