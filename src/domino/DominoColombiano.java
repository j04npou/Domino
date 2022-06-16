package domino;

public class DominoColombiano extends DominoLatino{
    public DominoColombiano(int numberOfPlayers, boolean isTeamGame) {
        super(numberOfPlayers, isTeamGame);
        this.targetPoints = 100;
    }

    @Override
    public String getGameName() {
        return "Domino Colombiano";
    }

    @Override
    public void showGameRules() {
        System.out.println( "Es pot jugar per parelles o indivdualment.\n" +
                            "L'objectius es arribar a 100 punts.\n" +
                            "Sortida: A la primera partida comença el jugador que te el doble 6 \n" +
                            "o doble inferior.\n" +
                            "Punts: La parella o el jugador guanyador acumula els punts que li \n" +
                            "queden per jugar a l'altre equip.\n" +
                            "Si hi ha empat a una tranca, a la modaditat en parelles guanya la \n" +
                            "parella que era ma. Si era un joc individual ningú guanya punts.\n" +
                            "El guanyador serà qui començarà la següent ma." );
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
}
