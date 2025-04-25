package br.com.trucomineiro.strategy;

import br.com.trucomineiro.model.Card;
import br.com.trucomineiro.model.Table;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements AIStrategy {
    private Random random = new Random();
    
    @Override
    public Card chooseCard(List<Card> hand, Table table) {
        if (hand.isEmpty()) return null;
        int index = random.nextInt(hand.size());
        return hand.remove(index);
    }
    
    @Override
    public boolean decideTruco(List<Card> hand, Table table) {
        // 50% de chance de aceitar o truco
        return random.nextBoolean();
    }
}