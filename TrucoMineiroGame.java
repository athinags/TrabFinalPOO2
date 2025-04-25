package br.com.trucomineiro.model;

import java.util.ArrayList;
import java.util.List;

// Padrão Singleton - Controlador principal do jogo
public class TrucoMineiroGame {
    private static TrucoMineiroGame instance;
    private Deck deck;
    private List<Team> teams;
    private Table table;
    private RuleEngine ruleEngine;
    private GameState currentState;
    private int dealerIndex;
    private int currentPlayerIndex;
    private List<Player> playOrder;
    
    private TrucoMineiroGame() {
        deck = new TrucoDeck();
        teams = new ArrayList<>();
        table = new Table();
        ruleEngine = new RuleEngine();
        currentState = GameState.WAITING_PLAYERS;
        dealerIndex = 0;
        currentPlayerIndex = 0;
        playOrder = new ArrayList<>();
    }
    
    public static synchronized TrucoMineiroGame getInstance() {
        if (instance == null) {
            instance = new TrucoMineiroGame();
        }
        return instance;
    }
    
    public void addTeam(Team team) {
        if (teams.size() < 2) {
            teams.add(team);
            table.setTeams(teams);
        }
    }
    
    public void startGame() {
        if (teams.size() == 2 && teams.get(0).isComplete() && teams.get(1).isComplete()) {
            // Define a ordem de jogo intercalando os times
            setupPlayOrder();
            currentState = GameState.DEALING;
            dealCards();
            currentState = GameState.PLAYING;
            table.notifyObservers(currentState);
        }
    }
    
    private void setupPlayOrder() {
        playOrder.clear();
        // Para simplificar, definimos a ordem como A1, B1, A2, B2 (onde A e B são os times)
        playOrder.add(teams.get(0).getPlayers().get(0));
    }
}