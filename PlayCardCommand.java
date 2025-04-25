package br.com.trucomineiro.command;

import br.com.trucomineiro.model.Card;
import br.com.trucomineiro.model.Player;
import br.com.trucomineiro.model.Table;
import java.util.List;

public class PlayCardCommand implements GameCommand {
    private Player player;
    private Table table;
    private int cardIndex;
    
    public PlayCardCommand(Player player, Table table, int cardIndex) {
        this.player = player;
        this.table = table;
        this.cardIndex = cardIndex;
    }
    
    @Override
    public void execute() {
        List<Card> hand = player.getHand();
        if (cardIndex >= 0 && cardIndex < hand.size()) {
            Card card = hand.remove(cardIndex);
            table.addCard(card, player);
        }
    }
}