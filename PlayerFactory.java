// PlayerFactory.java
public class PlayerFactory {
    public static Player createHumanPlayer(String name) {
        return new HumanPlayer(name);
    }
    
    public static Player createRandomAIPlayer(String name) {
        return new AIPlayer(name, new RandomStrategy());
    }
    
    public static Player createMineiroAIPlayer(String name) {
        return new AIPlayer(name, new MineiroStrategy());
    }
}
