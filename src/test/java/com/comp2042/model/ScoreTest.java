package com.comp2042.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    private Score score;

    @BeforeEach
    void setUp() {
        score = new Score();
    }

    @Test
    void testInitialScoreIsZero() {
        assertEquals(0, score.get());
    }

    @Test
    void testAddIncreasesScore() {
        score.add(5);
        assertEquals(5, score.get());

        score.add(3);
        assertEquals(8, score.get());
    }

    @Test
    void testResetSetsScoreToZero() {
        score.add(10);
        score.reset();
        assertEquals(0, score.get());
    }

    @Test
    void testScorePropertyReflectsChanges() {
        var property = score.scoreProperty();

        score.add(7);
        assertEquals(7, property.get());

        score.reset();
        assertEquals(0, property.get());
    }
}
