package domino;

public class DominoVenezolano extends DominoLatino{
    public DominoVenezolano(int numberOfPlayers, boolean isTeamGame) {
        super(numberOfPlayers, isTeamGame);
        this.targetPoints = 75;
    }

    @Override
    public String getGameName() {
        return "Domino Venezolano";
    }

    @Override
    public void showGameRules() {
        System.out.println( "Es pot jugar per parelles o indivdualment.\n" +
                            "L'objectius es arribar a 75 punts.\n" +
                            "Sortida: A la primera partida comença el jugador que te el doble 6 \n" +
                            "o doble inferior.\n" +
                            "Punts: La parella o el jugador guanyador acumula els punts que li \n" +
                            "queden per jugar a l'altre equip.\n" +
                            "Si hi ha empat a una tranca, a la modaditat en parelles i a la \n" +
                            "individual, ningú guanya punts" );
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
            // En cas d'empat guanya ningú gunya punts
            return 0;
        }
    }
}
