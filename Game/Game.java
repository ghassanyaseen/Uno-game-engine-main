package Game;

import java.util.*;

import Card.*;
import Player.*;
import Rule.*;


public abstract class Game {
    private List<Card> playGround = new ArrayList<>();
    private boolean clockWise = true;
    private List<Player> activePlayers = new ArrayList<Player>();
    private Rule rule;
    private List<Card> deck;
    private boolean isSkip;
    private String activeColor;
    private String[] colors;

    protected Game(Rule rule) {
        this.rule = rule;
    }

    public String[] getColors() {
        return colors;
    }
    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public void setActiveColor(String activeColor) {
        this.activeColor = activeColor;
    }
    public String getActiveColor() {
        return activeColor;
    }

    public boolean isClockWise() {
        return clockWise;
    }
    public void reverse() {
        this.clockWise = !clockWise;
    }

    public List<Card> getPlayGround() {
        return playGround;
    }

    public void addPlayer(Player player) {
        this.activePlayers.add(player);
    }
    public void removePlayer(Player player) {
        this.activePlayers.remove(player);
    }
    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    public Rule getRule() {
        return rule;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }
    public List<Card> getDeck() {
        return deck;
    }

    public boolean isSkip(){
        return isSkip;
    }
    public void setSkip(boolean skip){
        this.isSkip = skip;
    }

    // initialization methods' template
    // 1: create deck
    public void setMyDeck(){
        this.setDeck(this.getRule().createDeckRule(this));
    }

    // 2: create players and give cards to them
    public void createPlayers(Game game){
        game.getRule().createPlayersRule(game, 10, 2);
    }

    // 3: add first card to playground
    public void addToPlayGround(Game game){
        game.getRule().addToPlayGroundRule(game);
    }

    // 4: game flow
    public void gameFlow(Game game){
        game.getRule().gameFlowRule(game);
    }

    // initialization template method
    public final void play(){

        //1
        this.setMyDeck();

        //2
        createPlayers(this);

        //3
        addToPlayGround(this);

        //4
        gameFlow(this);
    }
}
