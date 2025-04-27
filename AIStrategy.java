// AIStrategy.java
import java.util.List;

public interface AIStrategy {
    Card chooseCard(List<Card> hand, Table table);
    boolean decideTruco(List<Card> hand, Table table);
}
