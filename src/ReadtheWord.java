import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadtheWord {
    private static final String fileName = "words-and-defs.txt";
    private ArrayList<String> words = new ArrayList<>();
    private int currentIndex = 0;

    public ReadtheWord() {
        try (InputStream in = getClass().getResourceAsStream(fileName);
                BufferedReader bf = new BufferedReader(new InputStreamReader(in))) {

            String line;
            int lineCount = 1;
            while ((line = bf.readLine()) != null) {
                if (lineCount % 2 != 0) { // Check if line number is odd
                    words.add(line);
                }
                lineCount++;
            }
        } catch (Exception e) {
            System.out.println("Couldn't find/read file: " + fileName);
            System.out.println("Error message: " + e.getMessage());
        }
    }

    public String getNextWord() {
        if (words.isEmpty())
            return " ";

        if (currentIndex >= words.size()) {
            currentIndex = 0; // Reset index if it exceeds the word count
        }

        String word = words.get(currentIndex);
        currentIndex += 1; // Increment index for the next word
        return word;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

}