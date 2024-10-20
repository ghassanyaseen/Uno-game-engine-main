package Effects;

import Game.Game;
import Player.Player;

import java.util.*;

public class ChangeColorEffect implements Effect {

    @Override
    public void execute(Game game, Player currentPlayer, Player nextPlayer){
        Random random = new Random();
        int randomNumber = random.nextInt(game.getColors().length);
        String color = game.getColors()[randomNumber];
        game.setActiveColor(color);
        System.out.println("The active color was changed to: " + color);
    }
}
