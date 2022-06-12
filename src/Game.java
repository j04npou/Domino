import domino.*;

import java.io.*;

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
        InputOutput.printLN("[3] - Domino Venezonlano (Team)");
        InputOutput.printLN("[4] - Domino Venezolano (Single)");
        InputOutput.printLN("[0] - Exit Game");
        String menu = InputOutput.input("01234");

        DominoGame domino = null;
        switch (menu) {
            case "1":
                domino = new DominoLatino(4,true);
                break;
            case "2":
                InputOutput.printLN("Enter number of players 2-4:");
                domino = new DominoLatino(InputOutput.numberOfPlayers(),false);
                break;
            case "3":
                domino = new DominoVenezolano(4,true);
                break;
            case "4":
                InputOutput.printLN("Enter number of players 2-4:");
                domino = new DominoVenezolano(InputOutput.numberOfPlayers(),false);
                break;
            case "0":
                return false;
        }
        domino.gameplay();
        if (domino.serialized) {
            InputOutput.printLN("Do you want to save this game? (Y/N)");
            String s=InputOutput.input("YNyn").toUpperCase();
            if (s.equals("Y")){
                try {
                    ObjectOutputStream writeFile = new ObjectOutputStream(new FileOutputStream("/tmp/save.dat"));
                    writeFile.writeObject(domino);
                    writeFile.close();
                }catch (Exception e){

                }
            }
        }

        return true;
    }
}
