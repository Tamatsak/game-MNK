package game;

import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Choose one game or tournament: 0/1");
        Scanner in = new Scanner(System.in);
        int gameType = in.nextInt();
        if (gameType == 0) {
            System.out.println("Choose the type of Board: 0/1 (circle/rectangle)");
            int type = in.nextInt();
            System.out.println("Enter the parameters");
            int p1 = in.nextInt();
            int p2 = in.nextInt();
            int p3 = 1;
            if (type == 1) {
                p3 = in.nextInt();
            }
            final Game game = new Game(true, new HumanPlayer(), new HumanPlayer());
            int result;
            if (type == 0) {
                do {
                    result = game.play(new MNKBoard(p1, p2));
                    System.out.println("Game result: " + result);
                } while (result != 0);
            } else {
                do {
                    result = game.play(new MNKBoard(p1, p2, p3));
                    System.out.println("Game result: " + result);
                } while (result != 0);
            }
        } else {
            System.out.println("Choose the type of Board: 0/1 (circle/rectangle)");
            int type = in.nextInt();
            System.out.println("Enter the parameters");
            int p1 = in.nextInt();
            int p2 = in.nextInt();
            int p3 = 1;
            if (type == 1) {
                p3 = in.nextInt();
            }
            System.out.println("Enter the number of HumanPlayers");
            int hCount = in.nextInt();
            LinkedList<Player> players = new LinkedList<Player>();
            for (int i = 0; i < hCount; i++) {
                System.out.println("Enter name of HumanPlayer: ");
                players.add(new HumanPlayer(in.next()));
            }

            System.out.println("Enter the number of RandomPlayers");
            int rCount = in.nextInt();
            for (int i = 0; i < rCount; i++) {
                System.out.println("Enter name of RandomPlayer: ");
                players.add(new RandomPlayer(in.next()));
            }
            final Tournament tournament;
            if (type == 0) {
                tournament = new Tournament(false, players.size(), players, p1, p2);
            } else {
                tournament = new Tournament(false, players.size(), players, p1, p2, p3);
            }
            System.out.println(tournament.tour() + " wins this Tournament!");
        }
    }
}
