package br.com.trucomineiro.model;

public enum GameState {
    WAITING_PLAYERS,
    DEALING,
    PLAYING,
    CARD_PLAYED,
    TRUCO_REQUESTED,
    TRUCO_ACCEPTED,
    TRUCO_REJECTED,
    NEW_ROUND,
    HAND_FINISHED, // Uma mão completa terminou (melhor de 3 rodadas)
    GAME_OVER      // Um time atingiu 12 pontos
}