import java.util.*;

public class LZW {
    HashMap<String, Integer> compressionDict;
    HashMap<Integer, String> decompressionDict;
    int decompressionCounter = 128;
    int compressionCounter = 128;

    public LZW() {
        compressionDict = new HashMap<>();
        decompressionDict = new HashMap<>();
        initializeCompressionDictionary();
        initializeDecompressionDictionary();
    }


    private void initializeCompressionDictionary() {
        compressionDict.clear();
        compressionCounter = 128;
        for (int i = 0; i <= 127; i++) {
            String charStr = Character.toString((char) i);
            compressionDict.put(charStr, i);
        }
    }

    private void initializeDecompressionDictionary() {
        decompressionDict.clear();
        decompressionCounter = 128;
        for (int i = 0; i <= 127; i++) {
            String charStr = Character.toString((char) i);
            decompressionDict.put(i, charStr);
        }
    }

    public List<Integer> compress(String input) {
        List<Integer> compressedTags = new ArrayList();
        for (int i = 0; i < input.length(); i++) {
            // find longest match
            String temp = "";
            String currentSeg = "";
           // ABAABABBAABAABAAAABABBBBBBBB

            for (int j = i; j < input.length(); j++) {
                temp += input.charAt(j);
                if (compressionDict.containsKey(temp)) {
                    currentSeg = temp;
                } else break;
            }
            compressedTags.add(compressionDict.get(currentSeg)); // add the index of the longest match

            // Only add the new sequence if it is not already in the dictionary
            if (!compressionDict.containsKey(temp)) {
                compressionDict.put(temp, compressionCounter);
                compressionCounter++;
            }
            i += currentSeg.length() - 1;
        }
        return compressedTags;
    }

    public List<Map.Entry<String, Integer>> getSortedDictionaryByValue() {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(compressionDict.entrySet());

        // Sort by value
        sortedEntries.sort(Map.Entry.comparingByValue());

        return sortedEntries;
    }

    public String decompress(List<Integer> compressedInput) {
        StringBuilder decompressedOutput = new StringBuilder();
        String previous = decompressionDict.get(compressedInput.get(0));
        decompressedOutput.append(previous);
        for (int i = 1; i < compressedInput.size(); ++i) {
            int currentCode = compressedInput.get(i);
            String curr = "";
            if (decompressionDict.containsKey(currentCode)) {
                curr = decompressionDict.get(currentCode);
            } else {
                curr = previous + previous.charAt(0);
            }
            decompressedOutput.append(curr);
            decompressionDict.put(decompressionCounter++, previous + curr.charAt(0));

            previous = curr;
        }
        return decompressedOutput.toString();
    }

}
