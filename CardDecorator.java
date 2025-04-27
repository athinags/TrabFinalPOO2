// CardDecorator.java
public abstract class CardDecorator extends Card {
    protected Card decoratedCard;
    
    public CardDecorator(Card decoratedCard) {
        super(decoratedCard.getSuit(), decoratedCard.getRank());
        this.decoratedCard = decoratedCard;
    }
    
    @Override
    public int getMineiroValue() {
        return decoratedCard.getMineiroValue();
    }
}
