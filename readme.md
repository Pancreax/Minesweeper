# Minesweeper 

**A simple Minesweeper game for Android**
(I'll write a better readme soon...)

----

## Game description

Comming soon...

## About the code

The game logic consists of an object of the [Field](./app/src/main/java/com/pancreax/minesweeper/game/Field.java) class that represents the mine field and, for each square in the game, a object of the [Cell](./app/src/main/java/com/pancreax/minesweeper/game/Cell.java) class.

The Cell class contains information about a specific square: if its a bomb, how many bombs there are in the surroundings, if it's revealed and it's position in the field. It also contais metgods to be revealed (and reveal the neighbors), to be reinitialized and the getter/seters for the attributes.

The Field class stores all the squares (Array of Cell objects), the size of the field (rows and columns), if the field is poppulated, if the game is finished, if the field is being visualized and contains the methods to handle when some cell is clicked, to poppulate or clean the field, to access a given cell and the getter/setter for the attributes.

The visual part has an activity [FieldActivity](./app/src/main/java/com/pancreax/minesweeper/view/FieldActivity.java) and a Presenter [FieldPresenter](./app/src/main/java/com/pancreax/minesweeper/view/FieldPresenter.java). The activity handles the screen elements while the presenter interact with the game's logic. They also interact with each other according to the interface [ViewPresenterContract](./app/src/main/java/com/pancreax/minesweeper/view/ViewPresenterContract.java). The field is represented in screen by an gridView element and each cell is an element of the gridView represented by a ConstrantLayout and a TextView. The cells of the field are mapped to GridView elements by the [FieldAdapter](./app/src/main/java/com/pancreax/minesweeper/view/FieldAdapter.java).

It was implemented in the AppBar the buttons to visualize the field and reset the game. Also, there is the functionality to change the size of the game based on a list accessed by the AppBar.