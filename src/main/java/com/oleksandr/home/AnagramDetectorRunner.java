package com.oleksandr.home;

import java.io.BufferedReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Oleksandr Ivanov
 * @since 0.0.1
 */
public class AnagramDetectorRunner {

    public static String testString = "csa\nsac\nwert\nertw\nsca";

    public static final String DELIMITER_WHITESPACE = " ";

    public static void main(String[] args) {
        Map<String, String> resultMap = new ConcurrentHashMap<>();

        try (StringReader sr = new StringReader(testString); BufferedReader br = new BufferedReader(sr)) {
            long startTime = System.currentTimeMillis();
            br.lines().forEach(line -> {
//1        try {
//1            Files.lines(Paths.get("sample.txt"), Charset.forName("UTF-8")).forEach(line -> {

                String keyToAdd = getSortedCharSequenceString(line);
                resultMap.merge(keyToAdd, line, (oldValue, newValue) -> String.join(DELIMITER_WHITESPACE, oldValue, newValue));
            });
            long difference = System.currentTimeMillis() - startTime;
            System.out.printf("time to solve: %d %n", difference);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        resultMap.forEach((key, value) -> System.out.println(key + " " + value));
    }

    // need to check which method is faster
    private static String getSortedCharSequenceString(String toConvert) {
        return toConvert.chars().sorted()
                .collect(StringBuilder::new, (stringBuilder, item) -> stringBuilder.append((char) item), StringBuilder::append)
                .toString();
    }

    private static String getSortedCharSequenceStringNative(String toConvert) {
        char[] charArray = toConvert.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }
}
