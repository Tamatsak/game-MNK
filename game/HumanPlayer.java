package game;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;
    private String name;

    public HumanPlayer(String name) {
        this(System.out, new Scanner(System.in));
        this.name = name;
    }

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;

    }
    @Override
    public String getName(){
        return name;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
        System.out.println("Enter Player Name: ");
        this.name = in.next();
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(getName() + "'s move");
            out.println("Enter row and column");
            int r = -1, c = -1;
            if (in.hasNextInt()){
                r = in.nextInt();
            } else {
                in.next();
            }
            if (in.hasNextInt()){
                c = in.nextInt();
            } else {
                in.next();
            }



            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
}
