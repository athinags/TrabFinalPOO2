package br.com.trucomineiro.view;

import br.com.trucomineiro.model.GameState;
import br.com.trucomineiro.model.Table;
import br.com.trucomineiro.model.Team;

public class ConsoleView implements GameObserver {
    @Override
    public void update(GameState state, Table table) {
        System.out.println("\n===== Estado do Jogo: " + state + " =====");
        System.out.println("Cartas na mesa: " + table.getCardsOnTable());
        System.out.println("Rodada atual: " + table.getCurrentRound());
        System.out.println("Pontos da mão atual: " + table.getCurrentPoints());
        
        if (table.getTeams() != null && table.getTeams().size() >= 2) {
            Team team1 = table.getTeams().get(0);
            Team team2 = table.getTeams().get(1);
            
            System.out.println("Rodadas ganhas por " + team1.getName() + ": " + table.getRoundsWonByTeam(0));
            System.out.println("Rodadas ganhas por " + team2.getName() + ": " + table.getRoundsWonByTeam(1));
            System.out.println("Pontuação total - " + team1.getName() + ": " + team1.getScore() + 
                              ", " + team2.getName() + ": " + team2.getScore());
        }
        System.out.println("=====================================\n");
    }
}