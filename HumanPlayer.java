package br.com.trucomineiro.model;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner scanner;
    
    public HumanPlayer(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }
    
    @Override
    public Card playCard(Table table) {
        // Implementação real dependerá da interface (console, GUI, etc.)
        System.out.println("Sua mão, " + name + ": ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ": " + hand.get(i));
        }
        
        System.out.print("Escolha uma carta (0-" + (hand.size()-1) + "): ");
        int choice = scanner.nextInt();
        
        if (choice >= 0 && choice < hand.size()) {
            return hand.remove(choice);
        }
        return null;
    }
    
    @Override
    public boolean decideTruco(Table table) {
        System.out.print("Oponente pediu Truco. Aceitar? (s/n): ");
        String choice = scanner.next().toLowerCase();
        return choice.equals("s");
    }
}