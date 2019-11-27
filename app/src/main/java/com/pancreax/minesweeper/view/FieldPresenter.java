package com.pancreax.minesweeper.view;

import com.pancreax.minesweeper.game.Cell;
import com.pancreax.minesweeper.game.Field;

public class FieldPresenter implements ViewPresenterContract.Presenter {

    Field field;
    ViewPresenterContract.View view;
    boolean gameFinished = false;
    boolean controlsEnable = true;

    @Override
    public void onCreate(ViewPresenterContract.View view) {
        this.view = view;
        restartGame();
    }

    @Override
    public void onCellClicked(Cell cell) {
        if(!controlsEnable || gameFinished || field.isViewingField()) return;
        field.revealCell(cell);
        view.renderField();
        if(field.isLost()) {
            view.showLost();
            gameFinished = true;
        }
        else if(field.isVictory()) {
            view.showVictory();
            gameFinished = true;
        }
    }

    @Override
    public void restartGame() {
        controlsEnable = true;
        gameFinished = false;
        if(field == null) {
            field = new Field();
            view.setField(field);
            view.renderField();
        }
        else {
            field.clearAll();
            view.renderField();
        }
    }

    @Override
    public void setFieldSize(Integer rows, Integer columns) {
        field = new Field(rows,columns);
        view.setField(field);
        restartGame();
    }

    @Override
    public void setViewingField(boolean viewingField) {
        field.setViewingField(viewingField);
        view.renderField();
    }
}
