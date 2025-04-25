package br.com.trucomineiro.command;

import br.com.trucomineiro.model.Player;
import br.com.trucomineiro.model.Table;
import br.com.trucomineiro.model.Team;

public class TrucoCommand implements GameCommand {
    private Player requestingPlayer;
    private Table table;
    
    public TrucoCommand(Player requestingPlayer, Table table) {
        this.requestingPlayer = requestingPlayer;
        this.table = table;
    }
    
    @Override
    public void execute() {
        if (table.canRequestTruco()) {
            table.requestTruco();
            
            // Encontra o time oposto ao do jogador que pediu truco
            Team requestingTeam = requestingPlayer.getTeam();
            Team respondingTeam = null;
            
            for (Team team : table.getTeams()) {
                if (!team.equals(requestingTeam)) {
                    respondingTeam = team;
                    break;
                }
            }
            
            if (respondingTeam != null) {
                // Escolhe o primeiro jogador do time oposto para responder ao truco
                Player respondingPlayer = respondingTeam.getPlayers().get(0);
                
                boolean accepted = respondingPlayer.decideTruco(table);
                if (accepted) {
                    table.acceptTruco();
                } else {
                    table.rejectTruco();
                    // No truco mineiro, se recusar o truco, o outro time ganha a mão
                    requestingTeam.addPoints(table.getCurrentPoints());
                }
            }
        }
    }
}