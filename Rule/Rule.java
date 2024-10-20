package Rule;

import Card.*;
import Player.*;
import Game.*;
import java.util.*;
import Effects.*;
import Rule.RuleToolBox.*;


public class Rule {

    public boolean placeCardRule(Player player, Game game){
        List<Card> playerHand = player.getCards();
        List<Card> playGround = game.getPlayGround();

        Card lastPlacedUnoCard = playGround.getLast();

        for (Card card : playerHand) {
            if (card instanceof SpecialCard && ((SpecialCard) card).isWild()
            || card.getColor().equals(game.getActiveColor())
            || card instanceof NumberedCard && lastPlacedUnoCard instanceof NumberedCard && ((NumberedCard) card).getNumber().equals(((NumberedCard)lastPlacedUnoCard).getNumber())){
                playerHand.remove(card);
                playGround.add(card);

                //change active color
                game.setActiveColor(card.getColor());

                return true;
            }
        }
        return false;
    }

    public boolean drawRule(Player player, Game game){
        List<Card> deck = game.getDeck();
        if (deck.isEmpty()){
            return false;
        }
        Random random = new Random();
        int index = random.nextInt(deck.size()); // Generate a random index
        Card drawnCard = deck.get(index); // Get the card at that index
        deck.remove(index); // Remove the card from the deck
        player.addCard(drawnCard); // Add the card to the player's cards
        return true;
    }

    public List<Card> createDeckRule(Game game){
        List<Card> deckCards = new ArrayList<>();

        String[] colors = {"Red", "Yellow", "Green", "Blue"};
        game.setColors(colors);

        String[] numbers = {"1", "2", "3", "4","5","6","7","8","9"};

        // create wild cards
        // create Draw4 cards
        for (int i = 0; i < 4; i++)
            deckCards.add(new SpecialCard("Black", new DrawFourEffect(), true, "Draw4"));

        // create ChangeColor cards
        for (int i = 0; i < 4; i++)
            deckCards.add(new SpecialCard("Black", new ChangeColorEffect(), true, "ChangeColor"));


        // create colored cards
        for (String color : game.getColors()) {
            // create "0" card
            deckCards.add(new NumberedCard(color, "0"));

            // create numbered cards
            int count = 1;
            for (String number : numbers) {
                for (int i = 0; i < 2; i++)
                    deckCards.add(new NumberedCard(color, String.valueOf(count)));
                count++;
            }

            // create colored special effect cards
            for (int i = 0; i < 2; i++){
                deckCards.add(new SpecialCard(color, new SkipEffect(), false, "SkipCard"));
                deckCards.add(new SpecialCard(color, new ReverseEffect(), false, "ReverseCard"));
                deckCards.add(new SpecialCard(color, new DrawTwoEffect(), false, "Draw2"));
            }
        }
        return deckCards;
    }

    public void scoringRule(List<Player> finishedPlayers){
        if (finishedPlayers.isEmpty()){
            System.out.println("Non of the players finish");
        }else{
            for (int j = 0; j<finishedPlayers.size(); j++){
                Player finishedPlayer = finishedPlayers.get(j);
                if (j == 0){
                    System.out.println("bot"+finishedPlayer.getId()+" was 1st");
                }else if (j == 1){
                    System.out.println("bot"+finishedPlayer.getId()+" was 2nd ");
                }else if (j == 2){
                    System.out.println("bot"+finishedPlayer.getId()+" was 3rd ");
                }else{
                    System.out.println("bot"+finishedPlayer.getId()+" was "+(j+1)+"th");
                }
            }
            System.out.println("The rest didn't finish");
        }
    }

    public void createPlayersRule(Game game, int max, int min) {
        int noOfPlayers = RuleToolBox.getNoOfPlayers(max, min);

        List<Card> deck = game.getDeck();
        for (int i = 0; i < noOfPlayers; i++) {
            List<Card> playerHand = new ArrayList<>(); // Initialize the list
            for (int j = 0; j < 7; j++) {
                Card wantedCard = RuleToolBox.getRandomCardFromDeck(deck);
                playerHand.add(wantedCard);
                deck.remove(wantedCard);
            }
            game.addPlayer(new Player(playerHand, i));
        }
    }

    public void addToPlayGroundRule(Game game){
        // get a random card from deck
        List<Card> deck = game.getDeck();

        boolean Special = true;
        Card drawnCard = null;
        while(Special){
            Random random = new Random();
            int index = random.nextInt(deck.size()); // Generate a random index
            drawnCard = deck.get(index); // Get the card at that index

            // place the random card
            game.getPlayGround().add(drawnCard);

            // check if placed card is special
            if (!(drawnCard instanceof SpecialCard))
                Special = false;
        }

        System.out.println("The "+drawnCard+" was placed into the playground");
    }

    public void gameFlowRule(Game game){
        List<Player> players = game.getActivePlayers();
        List<Player> finishedPlayers = new ArrayList<>();

        int i = 0;
        while(players.size() >= 2){

            Player activePlayer = players.get(i);

            int nextPlayerIndex = RuleToolBox.moveFlow(game, i, players.size());
            Player nextPlayer = players.get(nextPlayerIndex);

            if(!(activePlayer.placeCard(game))){
                // the player hasn't placed a card
                if(!(activePlayer.draw(game))){
                    // the player wasn't able to draw a card
                    System.out.println("The Game has ended because the deck is empty");
                    break;
                }
                System.out.println("bot"+activePlayer.getId()+" has drawn 1 card now he has "+activePlayer.getCards().size()+" cards" );

                // the player draw a card
            }else{
                // the player has placed a card
                Card placedCard = game.getPlayGround().getLast();
                System.out.println("bot"+activePlayer.getId()+" has placed a "+placedCard+" now he has "+activePlayer.getCards().size()+" cards");

                // check if player has won
                if (activePlayer.getCards().isEmpty()){
                    System.out.println("bot"+activePlayer.getId()+" has no cards left");
                    game.removePlayer(activePlayer);
                    finishedPlayers.add(activePlayer);
                }

                // run the card's effect
                if (placedCard instanceof SpecialCard specialCard){
                    specialCard.getSpecialEffect().execute(game, activePlayer, nextPlayer);
                    if (game.isSkip()){
                        // placed card was a skip card
                        i = RuleToolBox.moveFlow(game, i, players.size());
                        System.out.println("bot"+nextPlayer.getId()+" was skipped");
                        game.setSkip(false);
                    }
                }
            }
            // set counter value
            i = RuleToolBox.moveFlow(game, i, players.size());
        }

        // show final score
        RuleToolBox.Scorer(game, finishedPlayers);

    }
}
