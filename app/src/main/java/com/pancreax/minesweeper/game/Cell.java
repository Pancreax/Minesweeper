package com.pancreax.minesweeper.game;

import java.util.ArrayList;

public class Cell {
    private Field field;
    private Integer row;
    private Integer column;
    private Boolean isBomb = false;
    private Boolean isRevealed = false;
    private ArrayList<Cell> neighbours;
    private Integer surroundingBombsCount = 0;

    public Cell(Field field, Integer row, Integer column){
        this.field = field;
        this.row = row;
        this.column = column;
        neighbours = new ArrayList<>();
    }

    public void setSurroundingBombsCount(Integer count) {
        this.surroundingBombsCount = count;
    }

    public void setSurroundingBombsCount() {
        this.surroundingBombsCount = Long.valueOf(getNeighbors().stream()
                .filter(cell -> cell.isBomb)
                .count()).intValue();
    }

    public void clear(){
        isRevealed = false;
        isBomb = false;
        setSurroundingBombsCount(0);
    }

    public void reveal(){
        if(isRevealed) return;
        isRevealed = true;
        if(isEmpty()) getNeighbors().forEach(cell -> {
            if(!cell.isBomb()) cell.reveal();
        });
    }

    public Integer getLinearPosition(){
        return field.getColumns()*row+column;
    }

    public Integer getSurroundingBombsCount() {
        return surroundingBombsCount;
    }

    public ArrayList<Cell> getNeighbors() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<Cell> neighbours) {
        this.neighbours = neighbours;
    }

    public boolean isEmpty(){
        return getSurroundingBombsCount().equals(0) && !isBomb;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public Boolean isBomb() {
        return isBomb;
    }

    public void setBomb(Boolean bomb) {
        isBomb = bomb;
    }

    public Boolean isRevealed() {
        return isRevealed;
    }

    private void setRevealed(Boolean revealed) {
        isRevealed = revealed;
    }
}

