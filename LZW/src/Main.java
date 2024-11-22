import java.io.*;
import java.util.List;

public class Main {
    private static String readFile(String fileName) {
        // Read the input from a file
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return null;
        }
    }

    private static void writeFile(List<Integer> compressedTags, String decompressedString, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Compressed Tags: \n");
            for (Integer tag : compressedTags) {
                String tagString = '<' + Integer.toString(tag) + "> ";
                writer.write(tagString);
            }
            writer.newLine();

            writer.write("Decompressed String: \n");
            writer.write(decompressedString);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        LZW lzw = new LZW();
        String input = readFile("input.txt");
        if (input == null) {
            System.err.println("No input file");
            return;
        }


        List<Integer> compressedTags = lzw.compress(input);
        System.out.println(compressedTags);
        String decompressedString = lzw.decompress(compressedTags);
        writeFile(compressedTags, decompressedString, "output.txt");
    }
}