package com.comp2042.logic.bricks;
import java.util.List;

public interface BrickGenerator {

    Brick getBrick();

    Brick getNextBrick();
//next 3 bricks
    List<int[][]> getNextThreeShapes();


}
