
//Implementação concreta de um observador
public class ConsoleGameObserver implements GameObserver {
    @Override
    public void onGameStateChanged(GameState state, Table table) {
        System.out.println("\n===== Estado do Jogo: " + state + " =====");
        System.out.println("Cartas na mesa: " + table.getCardsOnTable());
        System.out.println("Rodada atual: " + table.getCurrentRound());
        System.out.println("Pontos da mão atual: " + table.getCurrentPoints());
        
        List<Team> teams = table.getTeams();
        if (teams != null && teams.size() >= 2) {
            Team team1 = teams.get(0);
            Team team2 = teams.get(1);
            
            System.out.println("Rodadas ganhas por " + team1.getName() + ": " + table.getRoundsWonByTeam(0));
            System.out.println("Rodadas ganhas por " + team2.getName() + ": " + table.getRoundsWonByTeam(1));
            System.out.println("Pontuação total - " + team1.getName() + ": " + team1.getScore() + 
                              ", " + team2.getName() + ": " + team2.getScore());
        }
        System.out.println("=====================================\n");
    }

    @Override
    public void onCardPlayed(Player player, Card card) {
        System.out.println(player.getName() + " jogou " + card);
    }

    @Override
    public void onRoundFinished(Team winnerTeam) {
        System.out.println(winnerTeam.getName() + " venceu a rodada!");
    }

    @Override
    public void onHandFinished(Team winnerTeam, int points) {
        System.out.println(winnerTeam.getName() + " ganhou " + points + " pontos! Pontuação atual: " + winnerTeam.getScore());
    }

    @Override
    public void onTrucoRequested(Player requestingPlayer) {
        System.out.println(requestingPlayer.getName() + " pediu TRUCO!!!");
    }

    @Override
    public void onTrucoResponse(boolean accepted) {
        System.out.println("Truco " + (accepted ? "aceito!" : "recusado!"));
    }
}
