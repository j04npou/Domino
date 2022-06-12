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
}