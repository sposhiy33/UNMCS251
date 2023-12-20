## BASIC GAME MECHANICS: ##
The game is played using the arrow keys to control the snake. The premise of
the game is to navigate the snake around the level to collect as many pellets
of food possible (red blocks) while making sure not to bump into any walls or 
travel out of map. Each time the snake eats a pellet of food, its size
increases, making it harder to manage the snake the board.

## IMPLEMENTATION DETAILS: ##

In the game manager, everything is managed used array lists that contain the
coordinates of the snake, walls, and food (each index is represented as
(row, col)). When the snake is moved in a certain direction, a new index is 
added to the snake array list in the intended direction. If the snake is at
its max length, the last index (representing the tail of the snake) is removed
from the list. The list of wall indicies is created within the constructor 
method upon intialization. An array of the empty indicies is created (after the
walls and the initial snake have been placed on the board). A random index from
this list is used to place the initial food block (position of which is added
to the food array list). 

Collision detection is done by seeing if the corrdinates of the head of the 
snake are contained within either the list of wall coordintes or the food
coordinates. If the snake head is contained in the wall indicies, the game is
over, and if the snake head is contained within food indicies, the score
is incremented

A 2d array of Tiles is also intialized within the game manager. When ever
a new coordinate is added to each of the array lists, a corresponding "tile"
is placed on the board. This 2d array is only used in the toString() method
to generate a string (by simply iterating over each cell in the array). It
also provides a double function by detecting when the snake goes out of the 
map. If the new coordinate of the snake is not within the bounds of the 2d
array, an ArrayIndexOutOfBounds exception will be thrown when the program
attempts to add a snake tile at that location (which doesn't exist). The 
implementation catches this exception and will ends the game.

One bug that I faced early on was that since the KeyBoard listener could take
inputs faster than the snake game was updating, sometimes, if the player was,
fast enough, could direct the snake into itself, ending the game. The game
should not allow the snake to go in the opposite direction from the current
direction. Thus the keyboard listner uses the snakes current direction (which
is only updated once per update step) and bases the valid input on this.

the update speed is determined by the following formula:
    time (ms) = 1000 / ln(row*col)

## TO DO ##
- add a custom slider for the user to control the speed of the game
