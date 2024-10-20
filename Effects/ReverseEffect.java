package Effects;

import Game.Game;
import Player.Player;

public class ReverseEffect implements Effect {

    @Override
    public void execute(Game game, Player currentPlayer, Player nextPlayer){
        game.reverse();
    }
}
