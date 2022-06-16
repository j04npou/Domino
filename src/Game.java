import domino.*;

import java.io.*;

public class Game {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        resumeGame();
        boolean exit;
        do {
            exit = menu();
            InputOutput.printLN();
        } while (exit);
    }

    private static boolean menu() throws IOException {

        InputOutput.printLN("[1] - Domino Latino (Team)");
        InputOutput.printLN("[2] - Domino Latino (Single)");
        InputOutput.printLN("[3] - Domino Venezonlano (Team)");
        InputOutput.printLN("[4] - Domino Venezolano (Single)");
        InputOutput.printLN("[5] - Domino Mexicano (Team)");
        InputOutput.printLN("[6] - Domino Mexicano (Single)");
        InputOutput.printLN("[7] - Domino Colombiano (Team)");
        InputOutput.printLN("[8] - Domino Colombiano (Single)");
        InputOutput.printLN("[9] - Domino Chileno (Team)");
        InputOutput.printLN("[a] - Domino Chileno (Single)");
        InputOutput.printLN("[0] - Exit Game");
        String menu = InputOutput.input("0123456789A");

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
            case "5":
                domino = new DominoMexicano(4,true);
                break;
            case "6":
                InputOutput.printLN("Enter number of players 2-4:");
                domino = new DominoMexicano(4,false);
                break;
            case "7":
                domino = new DominoColombiano(4,true);
                break;
            case "8":
                InputOutput.printLN("Enter number of players 2-4:");
                domino = new DominoColombiano(InputOutput.numberOfPlayers(),false);
                break;
            case "9":
                domino = new DominoClileno(4,true);
                break;
            case "A":
                InputOutput.printLN("Enter number of players 2-4:");
                domino = new DominoClileno(InputOutput.numberOfPlayers(),false);
                break;
            case "0":
                return false;
        }
        if (domino != null) {
            domino.gameplay();
            askForSerialize(domino);
        } else
            InputOutput.printLN("⛔ Unexpected error. ⛔");

        return true;
    }

    private static void askForSerialize(DominoGame domino) throws IOException {
        if (domino.serialized) {
            InputOutput.printLN("Do you want to save this game? (Y/N)");
            String s=InputOutput.input("YNyn").toUpperCase();
            if (s.equals("Y")){
                try {
                    ObjectOutputStream writeFile = new ObjectOutputStream(new FileOutputStream("/tmp/save.dat"));
                    writeFile.writeObject(domino);
                    writeFile.close();
                }catch (Exception e){
                    throw e;
                }
            }
        }
    }

    private static void resumeGame() throws IOException, ClassNotFoundException {
        DominoGame domino;
        File f = new File("/tmp/save.dat");
        if(f.exists() && !f.isDirectory()) {
            InputOutput.printLN("Do you want to continue the saved game? (Y/N)");
            String s=InputOutput.input("YNyn").toUpperCase();
            if (s.equals("Y")){
                InputOutput.printLN("Deserializing data...");
                try {
                    ObjectInputStream readFile = new ObjectInputStream(new FileInputStream("/tmp/save.dat"));
                    domino = (DominoGame) readFile.readObject();
                    readFile.close();
                    // Esborram arxiu
                    File serializedFile = new File("/tmp/save.dat");
                    serializedFile.delete();
                }catch (Exception e){
                    throw e;
                }
                if (domino != null ) {
                    domino.gameplay();
                    askForSerialize(domino);
                } else {
                    InputOutput.printLN("Failed to deserialize domino object");
                }
            }
        }
    }
}