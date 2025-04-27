// Card.java
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
        return rank + " de " + suit;
    }
    
    // No truco mineiro, cada carta tem um valor específico
    public int getMineiroValue() {
        // Implementação das regras de valor das cartas no truco mineiro
        // Manilhas fixas em ordem decrescente: 4♣ (zap), 7♥, A♠, 7♦
        if (rank == Rank.QUATRO && suit == Suit.PAUS) return 14; // Zap
        if (rank == Rank.SETE && suit == Suit.COPAS) return 13;
        if (rank == Rank.ÁS && suit == Suit.ESPADAS) return 12;
        if (rank == Rank.SETE && suit == Suit.OUROS) return 11;
        
        // Para as outras cartas, seguimos a ordem de valor normal do truco mineiro
        switch (rank) {
            case TRÊS: return 10;
            case DOIS: return 9;
            case ÁS: return 8;
            case REIS: return 7;
            case VALETE: return 6;
            case RAINHA: return 5;
            case SETE: return 4;
            case SEIS: return 3;
            case CINCO: return 2;
            case QUATRO: return 1;
            default: return 0;
        }
    }
}
