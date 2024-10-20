import Game.DefaultGame;
import Rule.Rule;

public class GameDriver {
    public static void main(String[] args) {
        DefaultGame game = DefaultGame.getInstance(new Rule());
        game.play();
    }
}
