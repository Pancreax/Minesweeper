package com.pancreax.minesweeper.game;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Field {

    private static final Integer DEFAULT_ROWS = 13;
    private static final Integer DEFAULT_COLUMNS = 10;
    private static final Integer N_BOMBS_8_8 = 10;
    private static final Integer N_BOMBS_13_10 = 15;
    private static final Integer N_BOMBS_16_12 = 25;
    private static final Integer N_BOMBS_20_15 = 40;

    private ArrayList<ArrayList<Cell>> cells = new ArrayList<>();
    private Integer rows;
    private Integer columns;
    private boolean isFieldPopulated = false;
    private boolean victory = false;
    private boolean lost = false;
    private boolean viewingField = false;

    public Field() {
        this(DEFAULT_ROWS,DEFAULT_COLUMNS);
    }

    public Field(Integer rows, Integer columns) {
        this.rows = rows;
        this.columns = columns;
        isFieldPopulated = false;

        IntStream.range(0,rows).forEach(rowIndex->{
            ArrayList row = new ArrayList<Cell>();
            IntStream.range(0,columns).forEach(columnIndex->{
                row.add(new Cell(this,rowIndex,columnIndex));
            });
            cells.add(row);
        });

        cells.forEach(row->row.forEach(cell -> cell.setNeighbours(getNeighbors(cell))));
    }

    private ArrayList<Cell> getNeighbors(Cell cell){

        int inclusiveStartRow = cell.getRow()-1;
        int exclusiveEndRow = cell.getRow()+2;
        int inclusiveStartColumn = cell.getColumn()-1;
        int exclusiveEndColumn = cell.getColumn()+2;

        ArrayList<Cell> neighbors = new ArrayList<>();

        IntStream.range(inclusiveStartRow,exclusiveEndRow).forEach(rowIndex->{
            IntStream.range(inclusiveStartColumn,exclusiveEndColumn).forEach(columnIndex->{
                if(isPositionInField(rowIndex,columnIndex)){
                    if(!(rowIndex==cell.getRow() && columnIndex==cell.getColumn()))
                    neighbors.add(getCellAt(rowIndex,columnIndex));
                }
            });
        });
        return neighbors;
    }

    public void revealCell(Cell cell){
        if(!isFieldPopulated()) populateField(cell);
        cell.reveal();
        if(cell.isBomb()) {
            lost = true;
            return;
        }
        if(isFinished()) victory = true;
    }

    public void populateField(Cell startCell){

        Integer bombCount = N_BOMBS_8_8;

        if(this.getCellCount() >= 13*10) bombCount = N_BOMBS_13_10;
        if(this.getCellCount() >= 16*12) bombCount = N_BOMBS_16_12;
        if(this.getCellCount() >= 20*15) bombCount = N_BOMBS_20_15;

        ArrayList<Integer> bombPositions = new ArrayList<>();
        IntStream.range(0,bombCount).forEach(i->{
            Integer randomPosition;
            do{
                randomPosition = Double.valueOf(Math.random()*getCellCount()).intValue();
            }while(bombPositions.contains(randomPosition) || startCell.getLinearPosition().equals(randomPosition));
            bombPositions.add(randomPosition);
        });

        bombPositions.forEach(position->getCellAt(position).setBomb(true));
        cells.forEach(rows -> rows.forEach(Cell::setSurroundingBombsCount));
        isFieldPopulated = true;
    }

    public void clearAll(){
        victory = false;
        lost = false;
        isFieldPopulated = false;
        cells.forEach(row->row.forEach(cell -> cell.clear()));
    }

    public Cell getCellAt(Integer linearIndex){
        int row = linearIndex/getColumns();
        int column = linearIndex%getColumns();
        return getCellAt(row,column);
    }

    public Cell getCellAt(Integer row, Integer column){
        return cells.get(row).get(column);
    }

    private boolean isFinished(){
        return cells.stream()
                .allMatch(row ->  row.stream()
                        .allMatch(cell -> cell.isRevealed() || cell.isBomb()));
    }

    private boolean isPositionInField(Integer rowIndex, Integer columnIndex){
        if(rowIndex < 0) return false;
        if(rowIndex >= rows) return false;
        if(columnIndex < 0) return false;
        if(columnIndex >= columns) return false;
        return true;
    }

    public Integer getCellCount(){
        return getColumns()*getRows();
    }

    public ArrayList<ArrayList<Cell>> getCells() {
        return cells;
    }

    public boolean isLost() {
        return lost;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getColumns() {
        return columns;
    }

    public boolean isFieldPopulated() {
        return isFieldPopulated;
    }

    public boolean isViewingField() {
        return viewingField;
    }

    public void setViewingField(boolean viewingField) {
        this.viewingField = viewingField;
    }
}
