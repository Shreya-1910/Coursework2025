package com.comp2042.model;

import java.io.*;

/**
 * Handles high score. It provides methods to load current high score from a file,
 * save a new high score if it is higher than the previous high score.
 */
public class HighScore {

    // Default file path
    private static String fileName = "highscore.txt";

    /**
     * @param path The path to the file where the high score will be stored.
     */
    public static void setFileName(String path) {
        fileName = path;
    }

    /**
     * @return The current high score. If the file doesn't exist or an error occurs, 0 is returned.
     */
    public static int load() {
        File file = new File(fileName);
        if (!file.exists()) return 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null) return 0;
            return Integer.parseInt(line.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @param newScore The new score to be saved.
     */
    public static void saveIfHigher(int newScore) {
        int oldHigh = load();
        if (newScore > oldHigh) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
                pw.println(newScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
