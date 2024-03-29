package domino;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public abstract class DominoGame implements DominoGameInterface, Serializable {
    protected ArrayList<Tile> tilePool;
    private ArrayList<Tile> tilesPlayed;
    protected ArrayList<Player> players;
    protected int targetPoints;
    protected boolean isTeamGame;
    protected int playerTurn;
    protected int nextRoundTurn;
    private boolean firstEverTurn;
    protected int playerPassCounter;
    private int chainLeftNumber;
    private int chainRightNumber;
    public boolean serialized;

    public DominoGame(int numberOfPlayers, boolean isTeamGame) {
        this.isTeamGame = isTeamGame;
        this.firstEverTurn=true;
        this.tilePool = new ArrayList<>();
        this.tilesPlayed = new ArrayList<>();
        this.players = new ArrayList<>();
        this.serialized = false;

        // Cream els equips/jugadors
        initPlayers(numberOfPlayers, isTeamGame);
    }

    private void initPlayers(int numberOfPlayers, boolean isTeamGame) {
        if (isTeamGame) {
            // si jugam per equips
            numberOfPlayers = 4;
            int teamChoice = 1;
            for (int i = 0; i < numberOfPlayers; i++) {
                players.add(new Player(i+1, teamChoice));
                teamChoice++;
                if (teamChoice > 2){
                    teamChoice = 1;
                }
            }
        } else {
            // si jugam individual
            for (int i = 0; i < numberOfPlayers; i++) {
                players.add(new Player(i+1, i+1));
            }
        }
    }

    private void initTilePool() {
        tilePool.clear();
        // Cream totes les fitxes i les posam al munt
        for (int x = 0; x <= 6; x++) {
            for (int y = 0; y <= x; y++) {
                tilePool.add(new Tile(x,y));
            }
        }
    }

    protected void showTiles(ArrayList<Tile> array, boolean hidden){
        for (int i = 0; i < array.size(); i++) {
            if (hidden)
                InputOutput.print(tilesReverseV);
            else
                InputOutput.print(array.get(i).showHtile() + " ");
        }
        InputOutput.printLN();
    }

    private void dealTiles() {
        for (int i = 0; i < players.size(); i++) {
            getRandomTiles(players.get(i));
        }
    }
    protected void getRandomTile(Player player) {
        Random rand = new Random();
        int int_random = rand.nextInt(tilePool.size());
        player.playerTiles.add(tilePool.get(int_random));
        tilePool.remove(int_random);
    }

    private void getRandomTiles(Player player) {
        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            int int_random = rand.nextInt(tilePool.size());
            player.playerTiles.add(tilePool.get(int_random));
            tilePool.remove(int_random);
        }
    }

    protected boolean poolTileCanBePlaced() {
        String possibleMoves = checkPossibleMoves(players.get(playerTurn-1).playerTiles);
        if (possibleMoves.replace(" ", "").length() > 0) {

            // colocam fitxa a la cadena
            putTile(players.get(playerTurn-1), Integer.parseInt( possibleMoves.replace(" ", "") ) - 1);
            playerPassCounter = 0;
            return true;
        } else {
            return false;
        }
    }

    protected void showTeams() {
        if (isTeamGame) {
            displayTeams();
        } else {
            displayPlayers();
        }
    }

    protected void displayTeams() {
        for (int i = 1; i <= 2; i++) {
            InputOutput.printLN("Team " + i + " (Points: " + players.get(i-1).points + ") :");
            for (int j = 0; j < players.size(); j++) {
                if (players.get(j).playerTeam == i) {
                    InputOutput.print("\tPlayer " + players.get(j).playerNumber + " ");
                    showTiles(players.get(j).playerTiles, true);
                }
            }
        }
    }

    protected void displayPlayers() {
        for (int j = 0; j < players.size(); j++) {
            InputOutput.print("Player " + players.get(j).playerNumber + " (Points:" + players.get(j).points + "): ");
            showTiles(players.get(j).playerTiles, true);
        }
        InputOutput.print("Pool: ");
        showTiles(tilePool,true);
    }

    private void firstMove() {
        if (firstEverTurn) {
            // Primer moviment de tot el joc
            firstEverTurn = false;
            makeFirstEverMoveTurn();
        }
    }

    protected void putTile(Player tmpPlayer, int playerTilePosition) {
        // actualitzam extrems
        if (tilesPlayed.size() == 0) {
            // posam la fitxa al joc
            tilesPlayed.add(tmpPlayer.playerTiles.get(playerTilePosition));
            // Primera fitxa colocada
            chainLeftNumber = tilesPlayed.get(0).getTileDotsLeft();
            chainRightNumber = tilesPlayed.get(0).getTileDotsRight();
        } else {
            // posteriors fitxes
            boolean isLeft = checkIsLeftMove(tmpPlayer.playerTiles.get(playerTilePosition));
            if (!isLeft) {
                if ( tmpPlayer.playerTiles.get(playerTilePosition).getTileDotsLeft() != chainRightNumber )
                    swapTileDots(tmpPlayer.playerTiles.get(playerTilePosition));
                chainRightNumber = tmpPlayer.playerTiles.get(playerTilePosition).getTileDotsRight();
                // posam la fitxa al joc a la dreta
                tilesPlayed.add(tmpPlayer.playerTiles.get(playerTilePosition));
            } else {
                if ( tmpPlayer.playerTiles.get(playerTilePosition).getTileDotsRight() != chainLeftNumber )
                    swapTileDots(tmpPlayer.playerTiles.get(playerTilePosition));
                chainLeftNumber = tmpPlayer.playerTiles.get(playerTilePosition).getTileDotsLeft();
                // posam la fitxa al joc a l'esquerra
                tilesPlayed.add(0,tmpPlayer.playerTiles.get(playerTilePosition));
            }
        }

        // llevam la fitxa al jugador
        tmpPlayer.playerTiles.remove(playerTilePosition);
    }

    private boolean checkIsLeftMove(Tile tile) {
        boolean leftPossible=false;
        boolean rightPossible=false;
        if ( tile.getTileDotsLeft() == chainLeftNumber || tile.getTileDotsRight() == chainLeftNumber )
            leftPossible = true;
        if ( tile.getTileDotsLeft() == chainRightNumber || tile.getTileDotsRight() == chainRightNumber )
            rightPossible = true;

        String response = "";
        if (chainLeftNumber != chainRightNumber) {
            if (leftPossible && rightPossible)
                response = InputOutput.askLeftOrRight();
            return response.equals("L") || (response.equals("") && leftPossible);
        }
        return false;
    }

    private void swapTileDots(Tile tile) {
        int tmpDots = tile.getTileDotsLeft();
        tile.setTileDotsLeft(tile.getTileDotsRight());
        tile.setTileDotsRight(tmpDots);
    }

    protected boolean checkEndGame() {
        // tots els jugadors passen torn
        if ( playerPassCounter >= players.size() ) {
            // ¿cercar qui ha guañyat? ****
            return true;
        }

        // Un jugador a acabat les seves fitxes
        return findPlayerWithNoTiles() != 0;
    }

    private int findPlayerWithNoTiles() {
        for (int playerNumber = 0; playerNumber < players.size(); playerNumber++) {
            if (players.get(playerNumber).playerTiles.size() == 0) {
                return players.get(playerNumber).playerNumber;
            }
        }
        return 0;
    }

    private boolean game() {
        playerTurn++;
        if ( playerTurn > players.size() )
            playerTurn = 1;

        // Primer moviment, cercam jugador que te el 6 doble, i posa sa fitxa i pasar el torn.
        if ( tilesPlayed.size() == 0 ) {
            firstMove();
        }

        // Mostram els equips i fitxes tapades dels jugadors
        showTeams();

        // Mostram fitxes colocades
        showGame();

        // Mostram fitxes destapades del jugador actiu
        InputOutput.printLN("Player " + playerTurn);
        showTiles(players.get(playerTurn-1).playerTiles, false);

        // jugador fa jugada
        return playerMakeMove();
    }

    private boolean playerMakeMove() {
        // Comprovam si el jugador pot fer alguna jugada
        String possibleMoves = checkPossibleMoves(players.get(playerTurn-1).playerTiles);
        if (possibleMoves.replace(" ", "").length() > 0) {
            // demanam fitxa a posar
            int inputMove = InputOutput.choseNumberFromList(possibleMoves);
            if (inputMove == 0) {
                serialized = true;
                playerTurn--;
                return true;
            }
            else {
                // colocam fitxa a la cadena
                putTile(players.get(playerTurn-1), inputMove - 1);
            }
            playerPassCounter = 0;
        } else {
            // Passam o agafam del munt
            playerCantMove();
        }
        return false;
    }

    private String checkPossibleMoves(ArrayList<Tile> array) {
        String tmpMoves = "";
        if (tilesPlayed.size() == 0) {
            // A partir de la segona ma
            if (players.get(playerTurn-1).hasAnyDouble()) {
                // pinta numeros dels dobles
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i).getTileDotsLeft() == array.get(i).getTileDotsRight())
                        tmpMoves = tmpMoves.concat((i+1) + " ");
                    else
                        tmpMoves = tmpMoves.concat("   ");
                }
            } else {
                // pinta tots els numeros
                tmpMoves = "1  2 3  4  5 6  7";
            }
        } else {
            // Seguents moviments
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).getTileDotsLeft() == chainLeftNumber ||
                        array.get(i).getTileDotsLeft() == chainRightNumber ||
                        array.get(i).getTileDotsRight() == chainLeftNumber ||
                        array.get(i).getTileDotsRight() == chainRightNumber
                ) {
                    tmpMoves = tmpMoves.concat((i + 1) + " ");
                } else {
                    tmpMoves = tmpMoves.concat("   ");
                }
            }
        }
        return tmpMoves;
    }

    private void showGame() {
        InputOutput.printLN("Tiles played:");
        for (int i = 0; i < tilesPlayed.size(); i++) {
            InputOutput.print(tilesPlayed.get(i).toString());
        }
        InputOutput.printLN();
    }

    protected void clearPlayerTiles(){
        for (int i = 0; i < players.size(); i++) {
            players.get(i).playerTiles.clear();
        }
    }

    protected boolean checkTotalWin() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).points >= targetPoints){
                showTeams();
                if (isTeamGame)
                    InputOutput.printLN("Team " + players.get(i).playerTeam + " WINS");
                else
                    InputOutput.printLN("Player " + players.get(i).playerNumber + " WINS");
                return true;
            }
        }
        return false;
    }

    public void gameplay() {
        boolean exitGame;
        if (!serialized) {
            playerTurn = 0;
            InputOutput.printLN("~".repeat(60));
            InputOutput.printLN(getGameName().toUpperCase());
            showGameRules();
            InputOutput.printLN("~".repeat(60));
        } else {
            InputOutput.printLN("Continuing saved game " + getGameName().toUpperCase());
        }
        do {
            if (!serialized) {
                playerPassCounter = 0;
                // Inicialitzam i repartim fitxes
                tilesPlayed.clear();
                clearPlayerTiles();
                initTilePool();
                dealTiles();

                // Assignam qui comença aquesta partida
                playerTurn = nextRoundTurn++;
                if (nextRoundTurn > 4)
                    nextRoundTurn = 1;
            } else {
                serialized = false;
            }

            do {
                exitGame = game();
                InputOutput.printLN("----------------------------------------------------------------------------");
            } while (!checkEndGame() && !exitGame);

            if (!exitGame) {
                if (playerPassCounter >= players.size()) {
                    if (isTeamGame)
                        playerTurn = findTrancaWinnerTeam();
                    else
                        playerTurn = findTrancaWinnerPlayer();
                }

                whoIsTheWinner();

                // Contam punts
                countPoints();
                InputOutput.printLN("----------------------------------------------------------------------------");
            }

        }while (!checkTotalWin() && !exitGame);
    }
}