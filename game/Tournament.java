package game;

import java.util.*;

public class Tournament {
    private List<Player> players, winners;
    private List<Player> places;
    private int count;
    private int[] boardParams;
    private BoardType type;
    private boolean log;

    private Tournament(boolean log, int count, LinkedList<Player> players) {
        this.players = players;
        this.places = new LinkedList<Player>();
        this.count = count;
        this.log = log;
        this.winners = new LinkedList<Player>();
    }

    private void firstRound() {
        int deg = 1;
        do {
            deg *= 2;
        } while (deg * 2<= this.count);
        int result;
        for (int i = 0; i < deg; i++) {
            if (i < count - deg) {
                Game game = new Game(log, players.get(i), players.get(count - i - 1));
                do {
                    result = game.play(makeNewBoard());
                    System.out.println("Game result: " + result);
                } while (result == 0);
                winners.add(result == 1 ? players.get(i) : players.get(count - i - 1));
            } else {
                winners.add(players.get(i));
            }
        }
        players = new LinkedList<Player>(winners);
        winners.clear();
        count = players.size();
    }

    public Tournament(boolean log, int count, LinkedList<Player> players, int m, int n, int k) {
        this(log, count, players);
        this.type = BoardType.Rectangle;
        boardParams = new int[]{m, n, k};
        firstRound();
    }

    public Tournament(boolean log, int count, LinkedList<Player> players, int r, int k) {
        this(log, count, players);
        this.type = BoardType.Circle;
        boardParams = new int[]{r, k};
        firstRound();
    }

    private Board makeNewBoard() {
        if (type == BoardType.Rectangle) {
            return new MNKBoard(boardParams[0], boardParams[1], boardParams[2]);
        } else {
            return new MNKBoard(boardParams[0], boardParams[1]);
        }
    }

    public void Round() {
        Collections.shuffle(players);
        int result;

        for (int i = 0; i < count / 2; i++) {
            Game game = new Game(log, players.get(i), players.get(count - i - 1));
            do {
                result = game.play(makeNewBoard());
                System.out.println("Game result: " + result);
            } while (result == 0);
            System.err.println("game has gone");
            winners.add(result == 1 ? players.get(i) : players.get(count - i - 1));
        }
        players = new LinkedList<Player>(winners);
        System.err.println("round has gone" + players.size());
        winners.clear();
        count = players.size();
    }

    public String tour(){
        while (players.size()!=1){
            Round();
        }
        return players.get(0).getName();
    }

}
