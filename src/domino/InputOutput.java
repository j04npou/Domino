package domino;

import java.util.Scanner;

public class InputOutput {
    public static int choseNumberFromList(String validValues){
        Scanner in = new Scanner(System.in);
        System.out.println(validValues);
        System.out.println("0: Exit game.");
        validValues = validValues.concat("0");
        String s;
        do {
            s = in.nextLine();
        } while ( s.length() != 1 || s.equals(" ") || !validValues.contains(s) );
        return Integer.parseInt(s);
    }

    public static String askLeftOrRight(){
        Scanner in = new Scanner(System.in);
        System.out.println("Chose move: 'L' o 'R'");
        String validValues = "LRlr";
        String s;
        do {
            s = in.nextLine();
        } while (s.length() != 1 || !validValues.contains(s));
        return s.toUpperCase();
    }

    public static void printLN(String inputText) {
        // Imprimeix amb bot de linea
        System.out.println(inputText);
    }
    public static void printLN() {
        System.out.println();
    }

    public static void print(String inputText) {
        // Imprimeix sense bot de linea
        System.out.print(inputText);
    }

    public static String input(String filter) {
        String s;
        Scanner keys = new Scanner(System.in);
        do {
            s = keys.next();
        } while (!filter.contains(s) || s.length() != 1);
        return s;
    }
}