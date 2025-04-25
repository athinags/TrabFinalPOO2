package br.com.trucomineiro.model;

import br.com.trucomineiro.view.GameObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Table {
    private List<Card> cardsOnTable;
    private List<Player> currentPlayers; // Jogadores que colocaram cartas na mesa
    private int currentRound;
    private int currentPoints;
    private boolean trucoRequested;
    private List<GameObserver> observers;
    private int[] roundsWonByTeam; // índice 0 = time 1, índice 1 = time 2
    private List<Team> teams;
    
    public Table() {
        cardsOnTable = new ArrayList<>();
        currentPlayers = new ArrayList<>();
        currentRound = 1;
        currentPoints = 1;
        trucoRequested = false;
        observers = new ArrayList<>();
        roundsWonByTeam = new int[2];
        teams = new ArrayList<>();
    }
    
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    
    public void notifyObservers(GameState state) {
        for (GameObserver observer : observers) {
            observer.update(state, this);
        }
    }
    
    public void addCard(Card card, Player player) {
        cardsOnTable.add(card);
        currentPlayers.add(player);
        System.out.println(player.getName() + " jogou " + card);
        notifyObservers(GameState.CARD_PLAYED);
    }
    
    public List<Card> getCardsOnTable() {
        return new ArrayList<>(cardsOnTable);
    }
    
    public List<Player> getCurrentPlayers() {
        return new ArrayList<>(currentPlayers);
    }
    
    public Card getHighestCard() {
        if (cardsOnTable.isEmpty()) return null;
        
        return cardsOnTable.stream()
                .max(Comparator.comparing(Card::getMineiroValue))
                .orElse(null);
    }
    
    public int getCurrentRound() {
        return currentRound;
    }
    
    public void nextRound() {
        // Determina o vencedor da rodada atual
        if (!cardsOnTable.isEmpty() && teams.size() == 2) {
            MineiroRules rules = new MineiroRules();
            Player roundWinner = rules.determineRoundWinner(currentPlayers, cardsOnTable);
            
            // Identifica a qual time o jogador pertence
            for (int i = 0; i < teams.size(); i++) {
                if (teams.get(i).getPlayers().contains(roundWinner)) {
                    roundsWonByTeam[i]++;
                    System.out.println(teams.get(i).getName() + " venceu a rodada " + currentRound + "!");
                    break;
                }
            }
        }
        
        currentRound++;
        cardsOnTable.clear();
        currentPlayers.clear();
        
        // Se uma equipe venceu 2 rodadas, ganha a mão
        for (int i = 0; i < roundsWonByTeam.length; i++) {
            if (roundsWonByTeam[i] >= 2) {
                teams.get(i).addPoints(currentPoints);
                resetHand();
                break;
            }
        }
        
        notifyObservers(GameState.NEW_ROUND);
    }
    
    private void resetHand() {
        System.out.println("Nova mão começando!");
        currentRound = 1;
        roundsWonByTeam = new int[2];
        currentPoints = 1;
        trucoRequested = false;
    }
    
    public int getCurrentPoints() {
        return currentPoints;
    }
    
    public void requestTruco() {
        trucoRequested = true;
        System.out.println("TRUCO!!!");
        notifyObservers(GameState.TRUCO_REQUESTED);
    }
    
    public void acceptTruco() {
        currentPoints += 3;
        trucoRequested = false;
        System.out.println("Truco aceito! Valor da mão: " + currentPoints + " pontos");
        notifyObservers(GameState.TRUCO_ACCEPTED);
    }
    
    public void rejectTruco() {
        trucoRequested = false;
        System.out.println("Truco recusado!");
        notifyObservers(GameState.TRUCO_REJECTED);
    }
    
    public int getRoundsWonByTeam(int teamIndex) {
        if (teamIndex >= 0 && teamIndex < roundsWonByTeam.length) {
            return roundsWonByTeam[teamIndex];
        }
        return 0;
    }
    
    public boolean canRequestTruco() {
        return currentPoints < 12; // No truco mineiro, o valor máximo é 12
    }
    
    public boolean isTrucoRequested() {
        return trucoRequested;
    }
    
    public List<Team> getTeams() {
        return teams;
    }
}