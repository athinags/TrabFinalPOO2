// Main.java (atualizado)
public class Main {
    public static void main(String[] args) {
        System.out.println("=== TRUCO MINEIRO ===");
        System.out.println("Demonstração do Padrão Facade");
        System.out.println("================================");
        
        // Cria a fachada do jogo
        TrucoGameFacade jogo = new TrucoGameFacade();
        
        // Adiciona um observador para acompanhar o jogo
        jogo.addObserver(new ConsoleGameObserver());
        
        // Configuração usando o método auxiliar da fachada
        jogo.setupDefaultGame();
        
        // Inicia o jogo
        System.out.println("\nIniciando o jogo...");
        jogo.startGame();
        
        System.out.println("\n===== FIM DA DEMONSTRAÇÃO =====");
        System.out.println("Padrões de Projeto aplicados:");
        System.out.println("1. Facade: Simplifica a interface para o cliente");
        System.out.println("2. Factory: Cria diferentes tipos de jogadores");
        System.out.println("3. Strategy: Define diferentes estratégias para IA");
        System.out.println("4. Decorator: Permite adicionar comportamento a cartas individuais");
        System.out.println("5. Observer: Notifica alterações do estado do jogo");
    }
}
