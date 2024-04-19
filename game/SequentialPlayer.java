package game;

import java.util.Random;
import java.util.Scanner;

public class  SequentialPlayer implements Player {

    private String name;
    private final Scanner in;
    public SequentialPlayer(final Scanner in) {
        this.in = in;
    }
    public SequentialPlayer() {
        this(new Scanner(System.in));
        System.out.println("Enter SequentialPlayer name: ");
        name = in.next();
    }
    @Override
    public Move move(final Position position, final Cell cell) {
//
        for (int r = 0; r < 2; r++) {
            for (int c = 0; c < 2; c++) {
                final Move move = new Move(r, c, cell);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }

    @Override
    public String getName() {
        return name;
    }
}
