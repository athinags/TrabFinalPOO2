package br.com.trucomineiro.strategy;

import br.com.trucomineiro.model.Card;
import br.com.trucomineiro.model.Table;
import java.util.List;

public interface AIStrategy {
    Card chooseCard(List<Card> hand, Table table);
    boolean decideTruco(List<Card> hand, Table table);
}