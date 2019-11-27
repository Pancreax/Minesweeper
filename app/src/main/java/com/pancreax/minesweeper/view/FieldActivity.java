package com.pancreax.minesweeper.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.pancreax.minesweeper.R;
import com.pancreax.minesweeper.game.Field;
import com.pancreax.minesweeper.game.Cell;

/**
 * Minesweeper - Guilherme Durães Versiani de Andrade - https://github.com/Pancreax - 2019
 *
 * A lógica do jogo é composta por um objeto da classe {@link Field} que representa o campo minado e
 * contém um objeto da classe {@link Cell}  para representar cada quadrado do campo.
 *
 * A classe "Cell" contém as informações referentes a cada quadrado: se ele é uma bomba, quantas
 * bombas tem ao seu redor, quais são seus vizinhos, se ele está revelado e sua posição no campo.
 * Também contém métodos para ser revelada (e revelar os vizinhos), ser reiniciada, e os métodos
 * de acesso aos atributos.
 *
 * A classe "Field" armazena todas os quadrados, o tamanho do campo (linhas e colunas), variáveis
 * sobre o estado do campo (se está populado, se o jogo terminou, se o campo está sendo visualizado)
 * e contém métodos para obter os vizinhos de um quadrado, para tratar quando algum quadrado for
 * clicado, para popular ou limpar o campo, para acessar um determinado quadrado e acessar os atributos.
 *
 * A parte visual possui uma activity {@link FieldActivity} e um presenter {@link FieldPresenter}.
 * A activity trata de interagir com os elementos da tela enquanto o presenter interage com o lógica
 * do jogo. Eles também interagem um com o outro de acordo com a interface {@link ViewPresenterContract}.
 * O campo é representado na tela por um elemento GridView, e cada quadrado é um elemento desde
 * GridView, representado por um ConstraintLayout e um TextView. Os quadrados do campo são mapeados
 * para elementos do GridView utilizando o {@link FieldAdapter}.
 *
 * Foram implementados na AppBar os botões de visualizar campo e reiniciar o jogo. Também foi
 * implementada a funcionalidade de alterar o tamanho do jogo a partir de uma lista que pode ser
 * acessada pela AppBar.
 */
public class FieldActivity extends AppCompatActivity implements ViewPresenterContract.View{

    private Menu menu;
    private Field field;
    private ViewPresenterContract.Presenter presenter = new FieldPresenter();
    private FieldAdapter fieldAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        presenter.onCreate(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar, menu);
        this.menu = menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem visibleBt = menu.findItem(R.id.action_visible);
        MenuItem invisibleBt = menu.findItem(R.id.action_invisible);
        switch (item.getItemId()) {
            case R.id.action_8x8:
                this.restartGame();
                presenter.setFieldSize(8,8);
                return true;

            case R.id.action_13x10:
                this.restartGame();
                presenter.setFieldSize(13,10);
                return true;

            case R.id.action_16x12:
                this.restartGame();
                presenter.setFieldSize(16,12);
                return true;

            case R.id.action_20x15:
                this.restartGame();
                presenter.setFieldSize(20,15);
                return true;

            case R.id.action_replay:
                this.restartGame();
                presenter.restartGame();
                return true;

            case R.id.action_visible:
                presenter.setViewingField(true);
                visibleBt.setVisible(false);
                invisibleBt.setVisible(true);
                return true;

            case R.id.action_invisible:
                presenter.setViewingField(false);
                visibleBt.setVisible(true);
                invisibleBt.setVisible(false);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void setField(Field field) {
        this.field = field;
        GridView gridView = findViewById(R.id.mineField);
        fieldAdapter = new FieldAdapter(this,field);
        gridView.setNumColumns(this.field.getColumns());
        gridView.setAdapter(fieldAdapter);
        gridView.setOnItemClickListener(((parent, view, position, id) -> presenter.onCellClicked(this.field.getCellAt(position))));
    }

    @Override
    public void renderField() {
        if(fieldAdapter != null)
            fieldAdapter.notifyDataSetChanged();
    }

    @Override
    public void showVictory() {
        Button tryAgainButton = findViewById(R.id.resetButton);
        tryAgainButton.setVisibility(View.VISIBLE);
        TextView winText = findViewById(R.id.messageText);
        winText.setText(R.string.victory);
        winText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLost() {
        Button tryAgainButton = findViewById(R.id.resetButton);
        tryAgainButton.setVisibility(View.VISIBLE);
        TextView winText = findViewById(R.id.messageText);
        winText.setText(R.string.lost);
        winText.setVisibility(View.VISIBLE);
    }

    public void resetButtonClick(View view) {
        this.restartGame();
        presenter.restartGame();
    }

    public void restartGame(){
        Button tryAgainButton = findViewById(R.id.resetButton);
        TextView winText = findViewById(R.id.messageText);
        winText.setVisibility(View.GONE);
        tryAgainButton.setVisibility(View.GONE);
    }
}