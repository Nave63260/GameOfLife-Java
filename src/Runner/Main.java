package Runner;

import Board.Grid;

import java.util.Scanner;

public class Main {
    public static void main() {
        System.out.println("Working...");

        Grid g = new Grid(100);
        g.setAt(94, 4, 1);
        g.setAt(94, 5, 1);
        g.setAt(94, 6, 1);
        g.setAt(95, 6, 1);
        g.setAt(96, 5, 1);

        Scanner myScanner = new Scanner(System.in);
        while (myScanner.nextLine().isEmpty()) {
            System.out.println(g);
            g.simulate(1);
        }
    }
}