package br.com.trucomineiro.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected List<Card> hand;
    protected Team team;
    
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }
    
    public void setTeam(Team team) {
        this.team = team;
    }
    
    public void setHand(List<Card> hand) {
        this.hand = new ArrayList<>(hand);
    }
    
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
    
    public String getName() {
        return name;
    }
    
    public Team getTeam() {
        return team;
    }
    
    public abstract Card playCard(Table table);
    
    public abstract boolean decideTruco(Table table);
}