package br.com.trucomineiro.strategy;

import br.com.trucomineiro.model.Card;
import br.com.trucomineiro.model.Table;
import java.util.Comparator;
import java.util.List;

public class MineiroStrategy implements AIStrategy {
    @Override
    public Card chooseCard(List<Card> hand, Table table) {
        if (hand.isEmpty()) return null;
        
        // Organizamos as cartas por valor específico do truco mineiro
        hand.sort(Comparator.comparing(Card::getMineiroValue));
        
        // Se é o primeiro a jogar na rodada
        if (table.getCardsOnTable().isEmpty()) {
            // Na primeira rodada, joga a carta mais alta
            if (table.getCurrentRound() == 1) {
                return hand.remove(hand.size() - 1);
            }
            // Nas outras rodadas, joga a menor carta
            else {
                return hand.remove(0);
            }
        }
        
        // Se não é o primeiro, tenta ganhar com a menor carta possível
        Card highestOnTable = table.getHighestCard();
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (card.getMineiroValue() > highestOnTable.getMineiroValue()) {
                return hand.remove(i);
            }
        }
        
        // Se não pode ganhar, joga a menor carta
        return hand.remove(0);
    }
    
    @Override
    public boolean decideTruco(List<Card> hand, Table table) {
        // Avalia a força da mão usando as regras do truco mineiro
        int handStrength = evaluateHandStrength(hand);
        int roundsWon = table.getRoundsWonByTeam(0); // Precisaria implementar para pegar os rounds ganhos pelo time
        
        // Aceita o truco se tiver uma mão forte ou já ganhou uma rodada
        return handStrength > 20 || roundsWon > 0;
    }
    
    private int evaluateHandStrength(List<Card> hand) {
        // Soma o valor de todas as cartas na mão
        return hand.stream().mapToInt(Card::getMineiroValue).sum();
    }
}