package Game;

import Rule.*;

public class DefaultGame extends Game {
    private static volatile DefaultGame instance;

    private DefaultGame(Rule rule) {
        super(rule);
    }

    public static DefaultGame getInstance(Rule rule) {
        DefaultGame result = instance;
        if (result == null) {
            synchronized (DefaultGame.class) {
                instance = result = new DefaultGame(rule);
            }
        }
        return result;
    }
}
