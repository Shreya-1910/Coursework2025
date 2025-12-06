package com.comp2042.controller;

import com.comp2042.model.HighScore;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreManagerTest {

    private static File tempFile;
    private Label label;
    private HighScoreManager manager;

    @BeforeAll
    static void initJavaFX() {
        // Start JavaFX Toolkit once
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() throws Exception {
        // Create temporary file for high score
        tempFile = File.createTempFile("highscore_test", ".txt");
        tempFile.deleteOnExit();

        // Redirect HighScore to temp file
        HighScore.setFileName(tempFile.getAbsolutePath());

        // Setup Label and manager
        label = new Label();
        manager = new HighScoreManager(label);
    }

    @Test
    void testUpdateHighScoreDisplayShowsCorrectValue() throws Exception {
        // Write initial high score to temp file
        try (PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
            pw.println("42");
        }

        manager.updateHighScoreDisplay();

        assertEquals("High Score: 42", label.getText());
    }

    @Test
    void testSaveIfHigherWritesNewHighScore() {
        manager.saveIfHigher(100);

        assertEquals("High Score: 100", label.getText());
        assertEquals("100", readFile(tempFile));
    }

    @Test
    void testSaveIfHigherDoesNotOverwriteIfLower() throws Exception {
        // Initial high score 200
        try (PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
            pw.println("200");
        }

        manager.saveIfHigher(50);

        assertEquals("High Score: 200", label.getText());
        assertEquals("200", readFile(tempFile)); // File remains unchanged
    }

    // Utility method to read temp file
    private static String readFile(File f) {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            return br.readLine().trim();
        } catch (Exception e) {
            return null;
        }
    }
}
