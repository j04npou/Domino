package domino;

import java.util.Random;
// Pedent: Inicialitzar les parelles per a partida individual i canvi de parella a cada ma
public class DominoMexicano extends DominoGame {
    private int round;
    private final int[][] teamDistribution = {
        {1, 1, 2, 2},
        {1, 2, 1, 2},
        {1, 2, 2, 1}
    };


    public DominoMexicano(int numberOfPlayers, boolean isTeamGame) {
        super(numberOfPlayers, isTeamGame);
        this.targetPoints = 200;
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
            // En cas d'empat, contam les fitxes restants de cada equip
            int[] teamTiles = new int[2];
            for (int i = 0; i < players.size(); i++) {
                teamTiles[players.get(i).playerTeam - 1] += players.get(i).playerTiles.size();
            }
            if (teamTiles[0] > teamTiles[1])
                return 2;
            else if (teamTiles[0] < teamTiles[1])
                return 1;
            else
                return 0;
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
        if (drawFlag)  {
            // Si dos jugadors han empatat, contam les fitxes restants de cada jugador
            int minimumTiles = players.get(0).playerTiles.size();
            minimumPlayer = players.get(0).playerNumber;
            drawFlag = false;

            for (int i = 1; i < players.size(); i++) {
                if (players.get(i).playerTiles.size() < minimumTiles) {
                    minimumTiles = players.get(i).playerTiles.size();
                    minimumPlayer = players.get(i).playerNumber;
                    drawFlag = false;
                } else if (players.get(i).playerTiles.size() == minimumTiles) {
                    drawFlag = true;
                }
            }
        }
        if (drawFlag)
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
                points += players.get(i).countDots();
            }
            if (isTeamGame)
                players.get(winnerTeam - 1).points += points;
            else {
                if (playerPassCounter >= players.size()) {
                    for (int i = 0; i < players.size(); i++) {
                        if (players.get(i).playerTeam == players.get(playerTurn - 1).playerTeam)
                            players.get(i).points += points;
                    }
                }else
                    players.get(playerTurn - 1).points += points;
            }
        }
    }

    @Override
    public void whoIsTheWinner() {
        // Mostram qui ha guanyat
        if (isTeamGame) {
            InputOutput.printLN("Winner team " + players.get(playerTurn - 1).playerTeam);
            nextRoundTurn = playerTurn -1;
        }else if (playerTurn == 0) {
            InputOutput.printLN("DRAW, no points for anyone");
        }else {
            InputOutput.printLN("Winner player " + players.get(playerTurn - 1).playerNumber);
            nextRoundTurn = playerTurn -1;
        }
    }

    @Override
    public void makeFirstEverMoveTurn() {
        // Si es la primera partida, cercam jugador aleatoriament, posa sa fitxa i pasa el torn al següent.
        Random rand = new Random();
        playerTurn = rand.nextInt(players.size())+1;
    }

    @Override
    public String getGameName() {
        return "Domino Mexicano";
    }

    @Override
    public void showGameRules() {
        InputOutput.printLN( "Sempre seràn 4 jugadors tan a la modalitat \n" +
                            "per parelles o indivdualment.\n" +
                            "L'objectius es arribar a 200 punts.\n" +
                            "Sortida: A la primera partida comença un jugador triat aleatoriament. \n" +
                            "Punts: La parella o el jugador guanyador acumula tots els punts que \n" +
                            "queden per jugar a tots els jugadors.\n" +
                            "Si hi ha empat a una tranca, guanya la parella \n" +
                            "o el jugador que te menys fitxes. Si empaten, ningú guanya el punts.\n" +
                            "Quan el mode de joc es individual i es guanya per tranca, els dos jugadors \n" +
                            "del mateix equip sumaran el punts no jugats.\n" +
                            "El guanyador serà qui començarà la següent ma." );
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
    protected void clearPlayerTiles(){
        super.clearPlayerTiles();
        round++;
        if (round >= teamDistribution.length)
            round = 0;

        for (int i = 0; i < players.size(); i++) {
            players.get(i).playerTeam = teamDistribution[round][players.get(i).playerNumber-1];
        }
    }

    @Override
    protected void displayPlayers() {
        for (int i = 1; i <= 2; i++) {
            InputOutput.printLN("Team " + i);
            for (int j = 0; j < players.size(); j++) {
                if (players.get(j).playerTeam == i) {
                    InputOutput.print("\tPlayer " + players.get(j).playerNumber + " (Points:" + players.get(j).points + "): ");
                    showTiles(players.get(j).playerTiles, true);
                }
            }
        }
    }

}
