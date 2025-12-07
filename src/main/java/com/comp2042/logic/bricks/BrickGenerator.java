package com.comp2042.logic.bricks;
import java.util.List;

/**
 * Interface responsible for generating game bricks. It provides methods to retrieve current brick,
 * next brick and upcoming bricks.
 */
public interface BrickGenerator {

    /**
     * @return The current {@link Brick} object that is in play.
     */
    Brick getBrick();

    /**
     * @return The next {@link Brick} object to be spawned.
     */
    Brick getNextBrick();

    /**
     * @return A list of 2D integer matrices representing the next three shapes in the game.
     */
//next 3 bricks
    List<int[][]> getNextThreeShapes();


}
