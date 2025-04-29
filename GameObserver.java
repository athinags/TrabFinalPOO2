//Interface para observadores
public interface GameObserver {
    void onGameStateChanged(GameState state, Table table);
    void onCardPlayed(Player player, Card card);
    void onRoundFinished(Team winnerTeam);
    void onHandFinished(Team winnerTeam, int points);
    void onTrucoRequested(Player requestingPlayer);
    void onTrucoResponse(boolean accepted);
}
