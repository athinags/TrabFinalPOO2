// TrucoDeck.java
public class TrucoDeck extends Deck {
    @Override
    protected void initializeDeck() {
        // No truco mineiro, usamos um baralho de 40 cartas (sem 8, 9 e 10)
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                // Adicionamos apenas as cartas utilizadas no truco mineiro
                cards.add(new Card(suit, rank));
            }
        }
    }
}
