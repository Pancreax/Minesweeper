package com.pancreax.minesweeper.view;

import com.pancreax.minesweeper.game.Cell;
import com.pancreax.minesweeper.game.Field;

public interface ViewPresenterContract {
    interface View{
        void setField(Field field);
        void renderField();
        void showVictory();
        void showLost();
    }
    interface Presenter{
        void onCreate(ViewPresenterContract.View context);
        void onCellClicked(Cell cell);
        void restartGame();
        void setFieldSize(Integer rows, Integer columns);
        void setViewingField(boolean viewingField);
    }
}
