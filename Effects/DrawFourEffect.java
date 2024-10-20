package Effects;

import Card.*;
import Game.Game;
import Player.Player;

import java.util.List;

public class DrawFourEffect implements Effect {

    @Override
    public void execute(Game game, Player currentPlayer, Player nextPlayer){
        // draw4
        for (int i = 0; i < 4; i++)
            nextPlayer.draw(game);

        System.out.println("bot"+nextPlayer.getId()+" has drawn 4 cards now he has "+nextPlayer.getCards().size()+" cards");

        // change color
        ChangeColorEffect changeColorEffect = new ChangeColorEffect();
        changeColorEffect.execute(game, currentPlayer, nextPlayer);

    }
}
