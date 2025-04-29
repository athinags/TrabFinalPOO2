import java.util.ArrayList;
import java.util.List;

/**
 * Fachada para o jogo de Truco Mineiro
 * Esta classe simplifica a interação com o sistema de jogo,
 * fornecendo uma interface unificada para as principais operações
 */
public class TrucoGameFacade implements GameSubject {
    private Deck deck;
    private List<Team> teams;
    private Table table;
    private RuleEngine ruleEngine;
    private GameState currentState;
    private int dealerIndex;
    private int currentPlayerIndex;
    private List<Player> playOrder;
    private List<GameObserver> observers; // Nova lista de observadores
    
    public TrucoGameFacade() {
        deck = new TrucoDeck();
        teams = new ArrayList<>();
        table = new Table();
        ruleEngine = new RuleEngine();
        currentState = GameState.WAITING_PLAYERS;
        dealerIndex = 0;
        currentPlayerIndex = 0;
        playOrder = new ArrayList<>();
        observers = new ArrayList<>(); // Inicializa a lista de observadores
    }
    
    // Implementação dos métodos da interface GameSubject
    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers(GameState state) {
        currentState = state;
        for (GameObserver observer : observers) {
            observer.onGameStateChanged(state, table);
        }
    }
    
    // Novos métodos para notificar eventos específicos
    private void notifyCardPlayed(Player player, Card card) {
        for (GameObserver observer : observers) {
            observer.onCardPlayed(player, card);
        }
    }
    
    private void notifyRoundFinished(Team winnerTeam) {
        for (GameObserver observer : observers) {
            observer.onRoundFinished(winnerTeam);
        }
    }
    
    private void notifyHandFinished(Team winnerTeam, int points) {
        for (GameObserver observer : observers) {
            observer.onHandFinished(winnerTeam, points);
        }
    }
    
    private void notifyTrucoRequested(Player requestingPlayer) {
        for (GameObserver observer : observers) {
            observer.onTrucoRequested(requestingPlayer);
        }
    }
    
    private void notifyTrucoResponse(boolean accepted) {
        for (GameObserver observer : observers) {
            observer.onTrucoResponse(accepted);
        }
    }
    
    /**
     * Adiciona um time ao jogo
     */
    public void addTeam(Team team) {
        if (teams.size() < 2) {
            teams.add(team);
            table.setTeams(teams);
            System.out.println("Time " + team.getName() + " adicionado ao jogo!");
        } else {
            System.out.println("Já existem 2 times no jogo!");
        }
    }
    
    /**
     * Inicia o jogo se houver times suficientes
     */
    public void startGame() {
        if (teams.size() == 2 && teams.get(0).isComplete() && teams.get(1).isComplete()) {
            // Define a ordem de jogo intercalando os times
            setupPlayOrder();
            notifyObservers(GameState.DEALING);
            dealCards();
            notifyObservers(GameState.PLAYING);
            System.out.println("Jogo iniciado com sucesso!");
            playGame();
        } else {
            System.out.println("Não é possível iniciar o jogo. Verifique se há 2 times completos.");
        }
    }
    
    /**
     * Configura a ordem de jogada, intercalando jogadores dos dois times
     */
    private void setupPlayOrder() {
        playOrder.clear();
        // Define a ordem como A1, B1, A2, B2 (onde A e B são os times)
        playOrder.add(teams.get(0).getPlayers().get(0));
        playOrder.add(teams.get(1).getPlayers().get(0));
        playOrder.add(teams.get(0).getPlayers().get(1));
        playOrder.add(teams.get(1).getPlayers().get(1));
        
        // Define o jogador inicial (depois do dealer)
        currentPlayerIndex = (dealerIndex + 1) % playOrder.size();
    }
    
    /**
     * Distribui as cartas para os jogadores
     */
    private void dealCards() {
        deck = new TrucoDeck(); // Cria um novo baralho
        deck.shuffle(); // Embaralha
        
        // Distribui 3 cartas para cada jogador
        for (Player player : playOrder) {
            List<Card> hand = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                hand.add(deck.drawCard());
            }
            player.setHand(hand);
        }
        
        // Incrementa o dealer para a próxima mão
        dealerIndex = (dealerIndex + 1) % playOrder.size();
    }
    
    /**
     * Método principal que controla o fluxo do jogo
     */
    private void playGame() {
        MineiroRules rules = new MineiroRules();
        
        // Loop principal do jogo
        while (!rules.isGameOver(teams)) {
            playHand();
            
            // Verifica se algum time venceu
            if (rules.isGameOver(teams)) {
                Team winner = teams.get(0).getScore() >= 12 ? teams.get(0) : teams.get(1);
                System.out.println("\n===== FIM DE JOGO =====");
                System.out.println("O time " + winner.getName() + " venceu o jogo!");
                notifyObservers(GameState.GAME_OVER);
                break;
            }
            
            // Prepara a próxima mão
            dealCards();
            notifyObservers(GameState.DEALING);
        }
    }
    
    /**
     * Joga uma mão completa (máximo 3 rodadas)
     */
    private void playHand() {
        int round = 1;
        table.resetHand(); // Reseta o estado da mão
        
        // Joga até 3 rodadas ou até um time ganhar 2 rodadas
        while (round <= 3 && table.getRoundsWonByTeam(0) < 2 && table.getRoundsWonByTeam(1) < 2) {
            System.out.println("\n===== RODADA " + round + " =====");
            playRound();
            round++;
        }
        
        // Verifica qual time ganhou a mão
        for (int i = 0; i < teams.size(); i++) {
            if (table.getRoundsWonByTeam(i) >= 2) {
                Team winnerTeam = teams.get(i);
                notifyHandFinished(winnerTeam, table.getCurrentPoints());
                break;
            }
        }
        
        notifyObservers(GameState.HAND_FINISHED);
    }
    
    /**
     * Joga uma rodada onde cada jogador coloca uma carta
     */
    private void playRound() {
        // Cada jogador joga uma carta
        for (int i = 0; i < playOrder.size(); i++) {
            int playerIndex = (currentPlayerIndex + i) % playOrder.size();
            Player currentPlayer = playOrder.get(playerIndex);
            
            System.out.println("\nVez de " + currentPlayer.getName() + " jogar");
            
            // Jogador decide se pede truco
            if (decideTruco(currentPlayer)) {
                notifyTrucoRequested(currentPlayer);
                requestTruco(currentPlayer);
                
                // Se o truco foi recusado, terminamos a mão
                if (table.isTrucoRejected()) {
                    return;
                }
            }
            
            // Jogador joga uma carta
            Card playedCard = currentPlayer.playCard(table);
            if (playedCard != null) {
                table.addCard(playedCard, currentPlayer);
                notifyCardPlayed(currentPlayer, playedCard);
            }
        }
        
        // Determina o vencedor da rodada
        MineiroRules rules = new MineiroRules();
        Player roundWinner = rules.determineRoundWinner(table.getCurrentPlayers(), table.getCardsOnTable());
        Team winnerTeam = null;
        
        for (Team team : teams) {
            if (team.getPlayers().contains(roundWinner)) {
                winnerTeam = team;
                break;
            }
        }
        
        if (winnerTeam != null) {
            notifyRoundFinished(winnerTeam);
        }
        
        // Avança para a próxima rodada
        table.nextRound();
        notifyObservers(GameState.NEW_ROUND);
        
        // Atualiza o jogador inicial para a próxima rodada
        currentPlayerIndex = (currentPlayerIndex + 1) % playOrder.size();
    }
    
    /**
     * Decide se o jogador vai pedir truco
     */
    private boolean decideTruco(Player player) {
        // Simplificação: apenas uma chance pequena de pedir truco
        // Na implementação real, isso seria mais complexo e baseado na estratégia
        return Math.random() < 0.1 && !table.isTrucoRequested() && table.canRequestTruco();
    }
    
    /**
     * Processa um pedido de truco
     */
    private void requestTruco(Player requestingPlayer) {
        table.requestTruco();
        
        // Encontra o time oposto ao do jogador que pediu truco
        Team requestingTeam = null;
        Team respondingTeam = null;
        
        for (Team team : teams) {
            if (team.getPlayers().contains(requestingPlayer)) {
                requestingTeam = team;
            } else {
                respondingTeam = team;
            }
        }
        
        // Escolhe o primeiro jogador do time oposto para responder ao truco
        if (respondingTeam != null) {
            Player respondingPlayer = respondingTeam.getPlayers().get(0);
            
            boolean accepted = respondingPlayer.decideTruco(table);
            if (accepted) {
                table.acceptTruco();
                notifyTrucoResponse(true);
            } else {
                table.rejectTruco();
                notifyTrucoResponse(false);
                // No truco mineiro, se recusar o truco, o outro time ganha a mão
                if (requestingTeam != null) {
                    requestingTeam.addPoints(table.getCurrentPoints());
                    notifyHandFinished(requestingTeam, table.getCurrentPoints());
                }
            }
        }
    }
    
    /**
     * Cria uma partida com jogadores humanos e IA
     */
    public void setupDefaultGame() {
        // Cria times
        Team team1 = new Team("Nós");
        Team team2 = new Team("Eles");
        
        // Adiciona jogadores
        team1.addPlayer(PlayerFactory.createHumanPlayer("Jogador"));
        team1.addPlayer(PlayerFactory.createMineiroAIPlayer("Parceiro"));
        team2.addPlayer(PlayerFactory.createRandomAIPlayer("Oponente 1"));
        team2.addPlayer(PlayerFactory.createRandomAIPlayer("Oponente 2"));
        
        // Adiciona times ao jogo
        addTeam(team1);
        addTeam(team2);
    }
}
