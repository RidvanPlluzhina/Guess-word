import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadtheDefinition {
    private static final String fileName = "words-and-defs.txt";
    private ArrayList<String> definitions = new ArrayList<>();
    private int currentIndex = 0;

    public ReadtheDefinition() {
        try (InputStream in = getClass().getResourceAsStream(fileName);
             BufferedReader bf = new BufferedReader(new InputStreamReader(in))) {

            String line;
            int lineCount = 1;
            while ((line = bf.readLine()) != null) {
                if (lineCount % 2 == 0) { 
                    definitions.add(line);
                }
                lineCount++;
            }
        } catch (Exception e) {
            System.out.println("Couldn't find/read file: " + fileName);
            System.out.println("Error message: " + e.getMessage());
        }
    }

    public String getNextDefinition() {
        if (definitions.isEmpty()) return "No definitions available.";

        if (currentIndex >= definitions.size()) {
            currentIndex = 1; 
        }

        String definition = definitions.get(currentIndex);
        currentIndex += 1; 
        return definition;
    }

    public String getDefinitionByIndex(int index) {
        if (definitions.isEmpty()) return "No definitions available.";

        if (index >= 0 && index < definitions.size()) {
            return definitions.get(index);
        }

        return "Invalid definition index.";
    }
}
