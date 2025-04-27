// HighlightedCard.java
public class HighlightedCard extends CardDecorator {
    private boolean isHighlighted = true;
    
    public HighlightedCard(Card decoratedCard) {
        super(decoratedCard);
    }
    
    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }
    
    public boolean isHighlighted() {
        return isHighlighted;
    }
    
    @Override
    public String toString() {
        return isHighlighted ? "** " + decoratedCard.toString() + " **" : decoratedCard.toString();
    }
}
