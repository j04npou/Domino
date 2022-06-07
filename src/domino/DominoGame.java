package domino;

import java.util.ArrayList;
import java.util.Random;

public abstract class DominoGame implements DominoGameInterface {
    private ArrayList<Tile> tilePool;
    private ArrayList<Tile> tilesPlayed;
    public ArrayList<Player> players;
    private int targetPoints;
    private boolean isTeamGame;
    public int playerTurn;
    public int nextRoundTurn;
    private boolean firstEverTurn;
    private int playerPassCounter;
    private int chainLeftNumber;
    private int chainRightNumber;

    public DominoGame(int numberOfPlayers, boolean isTeamGame, int targetPoints) {
        this.isTeamGame = isTeamGame;
        this.targetPoints = targetPoints;
        this.firstEverTurn=true;
        this.tilePool = new ArrayList<>();
        this.tilesPlayed = new ArrayList<>();
        this.players = new ArrayList<>();

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
        // Cream totes les fitxes i les posam al munt
        for (int x = 0; x <= 6; x++) {
            for (int y = 0; y <= x; y++) {
                tilePool.add(new Tile(x,y));
            }
        }
    }

    public void showTiles(ArrayList<Tile> array, boolean hidden){
        for (int i = 0; i < array.size(); i++) {
            if (hidden)
                System.out.print(tilesReverseV);
            else
                System.out.print(array.get(i).showHtile() + " ");
        }
        System.out.println();
    }

    private void dealTiles() {
        for (int i = 0; i < players.size(); i++) {
            getRandomTiles(players.get(i));
        }
    }

    private void getRandomTiles(Player player) {
        Random rand = new Random();
        for (int i = 0; i < 7; i++) {
            int int_random = rand.nextInt(tilePool.size());
            player.playerTiles.add(tilePool.get(int_random));
            tilePool.remove(int_random);
        }
    }

    private void showTeams() {
        if (isTeamGame) {
            for (int i = 1; i <= 2; i++) {
                System.out.println("Team " + i + ":");
                for (int j = 0; j < players.size(); j++) {
                    if (players.get(j).playerTeam == i) {
                        System.out.print("\tPlayer " + players.get(j).playerNumber + " ");
                        showTiles(players.get(j).playerTiles, true);
                    }
                }
            }
        } else {
            for (int j = 0; j < players.size(); j++) {
                System.out.print("Player " + players.get(j).playerNumber + " ");
                showTiles(players.get(j).playerTiles, true);
            }
        }
    }

    private void firstMove() {
        if (firstEverTurn) {
            // Primer moviment de tot el joc
            firstEverTurn = false;
            makeFirstEverMoveTurn();
        }
        // FALTA COMPLETAR EL PRIMER MOVIMENT DE LES SEGUENTS PARTIDES
    }

    public boolean hasDouble(int doubleTile, ArrayList<Tile> playerTiles) {
        for (int i = 0; i < playerTiles.size() ; i++) {
            if ( playerTiles.get(i).getTileDotsLeft() == doubleTile && playerTiles.get(i).getTileDotsRight() == doubleTile ) {
                return true;
            }
        }
        return false;
    }

    public int findPositionDouble(int doubleTile, ArrayList<Tile> playerTiles) {
        for (int i = 0; i < playerTiles.size() ; i++) {
            if ( playerTiles.get(i).getTileDotsLeft() == doubleTile && playerTiles.get(i).getTileDotsRight() == doubleTile ) {
                return i;
            }
        }
        return -1;
    }

    public void putTile(Player tmpPlayer, int playerTilePosition) {
        // actualitzam extrems
        if (tilesPlayed.size() == 1) {
            // Primera fitxa colocada
            chainLeftNumber = tilesPlayed.get(0).getTileDotsLeft();
            chainRightNumber = tilesPlayed.get(0).getTileDotsRight();
            // posam la fitxa al joc
            tilesPlayed.add(tmpPlayer.playerTiles.get(playerTilePosition));
        } else {
            // posteriors fitxes
            boolean isLeft = checkIsLeftMove(tmpPlayer.playerTiles.get(playerTilePosition));
            if (!isLeft) {
                chainRightNumber = tmpPlayer.playerTiles.get(playerTilePosition).getTileDotsRight();
                // posam la fitxa al joc a la dreta
                tilesPlayed.add(tmpPlayer.playerTiles.get(playerTilePosition));
            } else {
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
        String response;
        if (leftPossible && rightPossible && chainLeftNumber != chainRightNumber) {
            response = InputOutput.askLeftOrRight();
            if (response.equals("L")) {
                if ( tile.getTileDotsRight() != chainLeftNumber )
                    swapTileDots(tile);
                return true;
            } else {
                if ( tile.getTileDotsLeft() != chainRightNumber )
                    swapTileDots(tile);
                return false;
            }
        }
        return false;
    }

    private void swapTileDots(Tile tile) {
        int tmpDots = tile.getTileDotsLeft();
        tile.setTileDotsLeft(tile.getTileDotsRight());
        tile.setTileDotsRight(tmpDots);
    }

    public boolean checkEndGame() {
        // tots els jugadors passen torn
        if ( playerPassCounter >= players.size() ) {
            // ¿cercar qui ha guañyat? ****
            return true;
        }

        // Un jugador a acabat les seves fitxes
        if (findPlayerWithNoTiles() != 0)
            return true;

        return false;
    }

    private int findPlayerWithNoTiles() {
        for (int playerNumber = 1; playerNumber <= players.size(); playerNumber++) {
            if (players.get(playerNumber).playerTiles.size() == 0) {
                return playerNumber;
            }
        }
        return 0;
    }

    private void game() {
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
        System.out.println("Player " + playerTurn);
        showTiles(players.get(playerTurn).playerTiles, false);

        // jugador fa jugada
//        cancelGame = playerMakeMove();

        System.out.println("----------------------------------------------------------------------------");
    }

    private void showGame() {
        for (int i = 0; i < tilesPlayed.size(); i++) {
            System.out.print(tilesPlayed.get(i));
        }
        System.out.println();
    }

    public void gameplay() {
        boolean exitGame = false;
        playerTurn = 0;
        do {
            playerPassCounter = 0;
            // Inicialitzam i repartim fitxes
            tilesPlayed.clear();
            initTilePool();
            dealTiles();

            // Assignam qui comença aquesta partida
            playerTurn = nextRoundTurn++;
            do {
                game();
            }while (!checkEndGame() && !exitGame);

            if ( playerPassCounter >= players.size() ) {
                // Cercar guanyador: playerTurn = findTrancaWinner();
            }
            System.out.println("Guanyador " + playerTurn);

            // Contam punts
            countPoints();
            System.out.println("----------------------------------------------------------------------------");

        }while (!checkTotalWin() && !exitGame);
    }
}
