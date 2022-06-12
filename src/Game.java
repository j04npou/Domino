import domino.*;

public class Game {
    public static void main(String[] args) {
        boolean exit = false;
        do {
            exit = menu();
            InputOutput.printLN();
        } while (exit);
    }

    public static void newGame(int numberOfPlayers, boolean isTeamGame) {
        DominoGame domino = new DominoLatino(numberOfPlayers,isTeamGame);
        domino.gameplay();
    }

    private static boolean menu() {

        InputOutput.printLN("[1] - Domino Latino (Team)");
        InputOutput.printLN("[2] - Domino Latino (Single)");
        InputOutput.printLN("[0] - Exit Game");
        String menu = InputOutput.input("012");

        switch (menu) {
            case "1":
                newGame(4,true);
                break;
            case "2":
                InputOutput.printLN("Enter number of players 2-4:");
                int nPlayers;
                do {
                    String s = InputOutput.input("234");
                    nPlayers = Integer.parseInt(s);
                } while (nPlayers < 2 || nPlayers > 4);
                newGame(nPlayers,false);
                break;
            case "0":
                return false;
        }
        return true;
    }
}
