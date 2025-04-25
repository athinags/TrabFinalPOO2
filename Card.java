package br.com.trucomineiro.model;

public class Card {
    private Suit suit;
    private Rank rank;
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public Suit getSuit() {
        return suit;
    }
    
    public Rank getRank() {
        return rank;
    }
    
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    
    // No truco mineiro, cada carta tem um valor específico
    public int getMineiroValue() {
        // Implementação das regras de valor das cartas no truco mineiro
        // Manilhas fixas em ordem decrescente: 4♣ (zap), 7♥, A♠, 7♦
        if (rank == Rank.FOUR && suit == Suit.CLUBS) return 14; // Zap
        if (rank == Rank.SEVEN && suit == Suit.HEARTS) return 13;
        if (rank == Rank.ACE && suit == Suit.SPADES) return 12;
        if (rank == Rank.SEVEN && suit == Suit.DIAMONDS) return 11;
        
        // Para as outras cartas, seguimos a ordem de valor normal do truco mineiro
        switch (rank) {
            case THREE: return 10;
            case TWO: return 9;
            case ACE: return 8;
            case KING: return 7;
            case JACK: return 6;
            case QUEEN: return 5;
            case SEVEN: return 4;
            case SIX: return 3;
            case FIVE: return 2;
            case FOUR: return 1;
            default: return 0;
        }
    }
}