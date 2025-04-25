package br.com.trucomineiro.model;

import java.util.List;

public class MineiroRules implements TrucoRules {
    @Override
    public int getCardValue(Card card) {
        return card.getMineiroValue();
    }
    
    @Override
    public Player determineRoundWinner(List<Player> players, List<Card> cards) {
        if (players.size() != cards.size() || players.isEmpty()) return null;
        
        int winnerIndex = 0;
        Card highestCard = cards.get(0);
        
        for (int i = 1; i < cards.size(); i++) {
            Card currentCard = cards.get(i);
            if (currentCard.getMineiroValue() > highestCard.getMineiroValue()) {
                highestCard = currentCard;
                winnerIndex = i;
            }
        }
        
        return players.get(winnerIndex);
    }
    
    @Override
    public boolean isGameOver(List<Team> teams) {
        for (Team team : teams) {
            if (team.getScore() >= 12) {
                return true;
            }
        }
        return false;
    }
}