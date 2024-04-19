package game;

import java.util.Random;
import java.util.Scanner;

public class RandomPlayer implements Player {
    private final Random random;
    private String name;
    private final Scanner in;

    public RandomPlayer(final Random random,  final Scanner in) {
        this.random = random;
        this.in = in;
    }

    public RandomPlayer(String name) {
        this(new Random(), new Scanner(System.in));
        this.name = name;
    }

    public RandomPlayer() {
        this(new Random(), new Scanner(System.in));
        System.out.println("Enter RandomPlayer name: ");
        name = in.next();
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            int r = random.nextInt(25);
            int c = random.nextInt(25);
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }

    @Override
    public String getName(){
        return name;
    }
}
