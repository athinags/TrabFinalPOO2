package br.com.trucomineiro.model;

import java.util.List;

public interface TrucoRules {
    int getCardValue(Card card);
    Player determineRoundWinner(List<Player> players, List<Card> cards);
    boolean isGameOver(List<Team> teams);
}