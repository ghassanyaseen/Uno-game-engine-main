package Player;

import java.util.List;
import Card.Card;
import Game.Game;


public class Player {
    private List<Card> cards;
    private int id;

    public Player(List<Card> cards, int id) {
        this.cards = cards;
        this.id = id;
    };

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards(){
        return cards;
    }

    public int getId() {
        return id;
    }

    public boolean draw(Game game) {
        return game.getRule().drawRule(this, game);
    }

    public boolean placeCard(Game game) {
        return game.getRule().placeCardRule(this, game);
    }

}
