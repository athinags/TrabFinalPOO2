// Team.java
import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Player> players;
    private int score;
    
    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.score = 0;
    }
    
    public void addPlayer(Player player) {
        if (players.size() < 2) {  // Limite de 2 jogadores por equipe no truco mineiro padrão
            players.add(player);
            player.setTeam(this);
        }
    }
    
    public boolean isComplete() {
        return players.size() == 2;
    }
    
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
    
    public void addPoints(int points) {
        score += points;
        System.out.println(name + " ganhou " + points + " pontos! Pontuação atual: " + score);
    }
    
    public int getScore() {
        return score;
    }
    
    public String getName() {
        return name;
    }
}
