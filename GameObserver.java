package br.com.trucomineiro.view;

import br.com.trucomineiro.model.GameState;
import br.com.trucomineiro.model.Table;

public interface GameObserver {
    void update(GameState state, Table table);
}