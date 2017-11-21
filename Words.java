package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Words {

    private Map<String, HashMap<String, Byte>> nodes = new HashMap<>();

    public void newWord(String word) {
        if (!nodes.keySet().contains(word)) {
            nodes.put(word, new HashMap<>());
        }
    }

    public void init() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("word_map.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("=")) {
                    String[] s = line.split("=\\{");
                    // key = s[0];  value = s[1];
                    HashMap<String, Byte> val = new HashMap<>(); // value
                    String[] kvals = s[1].substring(0, s[1].length() - 1).split(",");
                    for (String kval : kvals) {
                        String[] kv = kval.split("=");
                        if (kv.length > 1) {
                            val.put(kv[0], Byte.valueOf(kv[1]));
                        }
                    }
                    nodes.put(s[0], val);
//                    System.out.println(Arrays.toString(kvals));
//                    System.out.println(nodes.toString());
//                    nodes.put(s[0], );
//                    map.put(strings[0], Integer.parseInt(strings[1]));
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String parseText(String text) {
        text = text.toLowerCase();
        String[] textSentences = text.trim().split("[.?!]");

        for (String seq : textSentences) {
            String[] textPart = seq.trim().split(",");

            for (String part : textPart) {
                String[] textWords = part.trim().split(" ");

                for (String word : textWords) {
//                    word = word.trim();
                    newWord(word);
                }
                for (int i = 0, textWordsLength = textWords.length; i < textWordsLength - 1; i++) {
                    String word = textWords[i];
                    HashMap<String, Byte> weight = nodes.get(word);
                    String nextWord = textWords[i + 1];
                    byte w = weight.get(nextWord) == null ? 0 : weight.get(nextWord);
                    if (w != Byte.MAX_VALUE) {
                        w++;
                        weight.put(nextWord, w);
//                        System.out.println(nodes.toString());
                    }
                }
            }
        }

        try {
            PrintWriter writer = new PrintWriter("word_map.txt", "UTF-8");
            nodes.forEach((key, value) -> {
                final String[] s = {key + "={"};
                if (value.size() != 0) {
                    value.forEach((k, v) -> s[0] += k + "=" + v + ",");
                    s[0] = s[0].substring(0, s[0].length() - 1);
                }
                writer.println(s[0] + "}");
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TreeMap<String, Integer> sizes = new TreeMap<>();
        nodes.forEach((key, value) -> sizes.put(key, value.size()));
        return sizes.toString();
    }
}