package com.comp2042.model;

import java.io.*;

public class HighScore {

    private static final String FILE_NAME = "highscore.txt";

 //load high score from file
    public static int load() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return 0; // no high score yet
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null) return 0;
            return Integer.parseInt(line.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    //Save new score only if it's higher than old high score.
    public static void saveIfHigher(int newScore) {
        int oldHigh = load();

        if (newScore > oldHigh) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
                pw.println(newScore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}





