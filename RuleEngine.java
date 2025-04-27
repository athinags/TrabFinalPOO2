// RuleEngine.java
public class RuleEngine {
    private TrucoRules rules;
    
    public RuleEngine() {
        rules = new MineiroRules();
    }
    
    public TrucoRules getRules() {
        return rules;
    }
    
    public void setRules(TrucoRules rules) {
        this.rules = rules;
    }
}
