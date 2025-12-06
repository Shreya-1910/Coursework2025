package com.comp2042.controller;

import com.comp2042.events.MoveEvent;
import javafx.animation.Timeline;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class GameLoopTest {
    private TestGuiController mockController;
    private GameLoop gameLoop;

    @BeforeAll
    static void initJavaFX() {
        try {
            javafx.application.Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Already initialized
        }
    }

    @BeforeEach
    void setUp() {
        mockController = new TestGuiController();
        gameLoop = new GameLoop(mockController, 400);
    }

    @AfterEach
    void tearDown() {
        if (gameLoop != null) gameLoop.stop();
    }

    @Test
    void start_resetsSpeedAndTimeline() throws Exception {
        gameLoop.setSpeed(300); // change speed
        gameLoop.start();

        assertTrue(gameLoop.isRunning());
        assertEquals(400, getPrivateField(gameLoop, "currentSpeed")); // initialSpeed
        Timeline timeline = (Timeline) getPrivateField(gameLoop, "timeline");
        assertNotNull(timeline);
        assertEquals(Timeline.Status.RUNNING, timeline.getStatus());
    }

    @Test
    void stop_stopsTimeline() throws Exception {
        gameLoop.start();
        gameLoop.stop();

        assertFalse(gameLoop.isRunning());
        Timeline timeline = (Timeline) getPrivateField(gameLoop, "timeline");
        assertNull(timeline); // timeline set to null in stop()
    }

    @Test
    void pause_and_resume() throws Exception {
        gameLoop.start();

        gameLoop.pause();
        Timeline timeline = (Timeline) getPrivateField(gameLoop, "timeline");
        assertEquals(Timeline.Status.PAUSED, timeline.getStatus());
        assertFalse(gameLoop.isRunning());

        gameLoop.resume();
        timeline = (Timeline) getPrivateField(gameLoop, "timeline");
        assertEquals(Timeline.Status.RUNNING, timeline.getStatus());
        assertTrue(gameLoop.isRunning());
    }

    @Test
    void setSpeed_updatesSpeedAndTimeline() throws Exception {
        gameLoop.start();
        gameLoop.setSpeed(250);

        assertEquals(250, getPrivateField(gameLoop, "currentSpeed"));
        Timeline timeline = (Timeline) getPrivateField(gameLoop, "timeline");
        assertNotNull(timeline);
        assertEquals(Timeline.Status.RUNNING, timeline.getStatus());
    }

    @Test
    void tick_triggersMoveDown() throws Exception {
        // Directly invoke tick via reflection to avoid timing issues
        Method tickMethod = GameLoop.class.getDeclaredMethod("tick");
        tickMethod.setAccessible(true);
        tickMethod.invoke(gameLoop);

        assertEquals(1, mockController.moveDownCount);

        // Call tick again to confirm multiple ticks
        tickMethod.invoke(gameLoop);
        assertEquals(2, mockController.moveDownCount);
    }

    @Test
    void isRunning_reflectsTimelineStatus() throws Exception {
        assertFalse(gameLoop.isRunning());

        gameLoop.start();
        assertTrue(gameLoop.isRunning());

        gameLoop.pause();
        assertFalse(gameLoop.isRunning());

        gameLoop.resume();
        assertTrue(gameLoop.isRunning());

        gameLoop.stop();
        assertFalse(gameLoop.isRunning());
    }

    // ===== Helper classes =====
    private static class TestGuiController extends GuiController {
        int moveDownCount = 0;

        @Override
        public void moveDown(MoveEvent event) {
            moveDownCount++;
        }
    }

    private Object getPrivateField(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
