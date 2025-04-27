// AIPlayer.java
public class AIPlayer extends Player {
    private AIStrategy strategy;
    
    public AIPlayer(String name, AIStrategy strategy) {
        super(name);
        this.strategy = strategy;
    }
    
    @Override
    public Card playCard(Table table) {
        System.out.println(name + " está pensando...");
        return strategy.chooseCard(hand, table);
    }
    
    @Override
    public boolean decideTruco(Table table) {
        boolean decision = strategy.decideTruco(hand, table);
        System.out.println(name + (decision ? " aceitou" : " recusou") + " o truco!");
        return decision;
    }
}
