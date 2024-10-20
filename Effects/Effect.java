package Effects;

import Game.Game;
import Player.Player;

public interface Effect {
    void execute(Game game, Player currentPlayer, Player nextPlayer);
}
