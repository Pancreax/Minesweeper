package com.pancreax.minesweeper.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pancreax.minesweeper.R;
import com.pancreax.minesweeper.game.Cell;
import com.pancreax.minesweeper.game.Field;

public class FieldAdapter extends BaseAdapter {

    private Context context;
    private Field field;
    private LayoutInflater inflater;

    public FieldAdapter(Context context, Field field) {
        this.context = context;
        this.field = field;
    }

    @Override
    public int getCount() {
        return field.getCellCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.grid_item_layout,null);
        }

        ConstraintLayout gridItem = convertView.findViewById(R.id.cellLayout);
        TextView text = convertView.findViewById(R.id.text);
        Cell cell = field.getCellAt(position);
        int bombCount = cell.getSurroundingBombsCount();
        boolean isRevealed = cell.isRevealed() || field.isViewingField();
        boolean isBomb = cell.isBomb();

        if(isRevealed){
            text.setBackgroundColor(context.getColor(R.color.colorRevealed));
            if(isBomb){
                text.setText("*");
                text.setTextColor(context.getColor(R.color.colorBombText));
                text.setBackgroundColor(context.getColor(R.color.colorBombBackground));
            }
            else if(bombCount > 0){
                text.setText(String.format("%d",bombCount));
                text.setTextColor(getTextColor(bombCount));
            }
            else{
                text.setText("");
            }
        }
        else{
            text.setText("");
            text.setBackgroundColor(context.getColor(R.color.colorUnrevealed));
        }

        return gridItem;
    }

    private int getTextColor(Integer bombCount){
        switch (bombCount) {
            case 1:
                return context.getColor(R.color.color1);
            case 2:
                return context.getColor(R.color.color2);
            case 3:
                return context.getColor(R.color.color3);
            case 4:
                return context.getColor(R.color.color4);
            case 5:
                return context.getColor(R.color.color5);
            case 6:
                return context.getColor(R.color.color6);
            case 7:
                return context.getColor(R.color.color7);
            case 8:
                return context.getColor(R.color.color8);
        }
        return Color.parseColor("FF000000");
    }
}
