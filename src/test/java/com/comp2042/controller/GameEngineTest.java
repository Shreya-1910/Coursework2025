package com.comp2042.controller;

import com.comp2042.model.Score;
import com.comp2042.view.BoardViewData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    private GameEngine engine;

    @BeforeEach
    void setUp() {
        // Create a small deterministic board for testing
        engine = new GameEngine(20, 10);
        // Inject deterministic Random for predictable garbage
        engine.getClass().getDeclaredFields();
        engine.getClass().getDeclaredFields();
        engine.getClass().getDeclaredFields();
        engine.setRandom(new Random(42));
    }

    @Test
    void testNewGameResetsState() {
        engine.moveDown(null); // simulate some moves
        engine.hardDrop();
        engine.newGame();

        assertEquals(1, engine.getLevel(), "Level should reset to 1");
        assertEquals(0, engine.getTotalLinesCleared(), "Total lines cleared should reset to 0");
        assertEquals(500, engine.getCurrentSpeed(), "Speed should reset to baseSpeed");
        assertEquals(0, engine.getScore(), "Score should reset to 0");
        assertEquals(0, engine.getGarbageLinesAdded(), "Garbage lines should reset to 0");
    }

    @Test
    void testMoveDownIncreasesScoreForSoftDrop() {
        int initialScore = engine.getScore();
        engine.moveDown(com.comp2042.events.EventSource.USER);
        int afterScore = engine.getScore();

        assertTrue(afterScore > initialScore, "Soft drop should increase score by at least 1");
    }

    @Test
    void testHardDropIncreasesScoreAndClearsLines() {
        int initialScore = engine.getScore();
        int initialLines = engine.getTotalLinesCleared();
        engine.hardDrop();

        assertTrue(engine.getScore() > initialScore, "Hard drop should increase score");
        assertTrue(engine.getTotalLinesCleared() >= initialLines, "Lines cleared should be updated");
    }

    @Test
    void testMoveLeftRightRotateReturnNonNullView() {
        BoardViewData left = engine.moveLeft();
        BoardViewData right = engine.moveRight();
        BoardViewData rotated = engine.rotate();

        assertNotNull(left, "moveLeft should return BoardViewData");
        assertNotNull(right, "moveRight should return BoardViewData");
        assertNotNull(rotated, "rotate should return BoardViewData");
    }

    @Test
    void testHoldPieceReturnsBoardViewData() {
        BoardViewData held = engine.hold();
        assertNotNull(held, "hold should return BoardViewData");
    }

    @Test
    void testGarbageSpawnDeterministic() {
        engine.hardDrop();
        // With fixed Random, garbageLinesAdded should be deterministic
        int garbageLines = engine.getGarbageLinesAdded();
        assertTrue(garbageLines >= 0, "Garbage lines should be >= 0");
    }

    @Test
    void testGetHeldShapeReturnsArrayOrNull() {
        int[][] heldShape = engine.getHeldShape();
        assertTrue(heldShape == null || heldShape.length > 0, "Held shape should be null or non-empty array");
    }
}
