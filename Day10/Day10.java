package Day10;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {
    private static Map<Character,Character> pairs = Stream.of(new Character[][] {{'{','}'},{'(',')'},{'[',']'},{'<','>'}}).collect(Collectors.toMap(d -> d[0], d -> d[1]));
    private static Map<Character,Integer> score = Stream.of(new Object[][] {{'}',1197},{')',3},{']',57},{'>',25137}}).collect(Collectors.toMap(d -> (Character) d[0], d -> (Integer) d[1]));
    private static Map<Character,Integer> scoreP2 = Stream.of(new Object[][] {{'}',3},{')',1},{']',2},{'>',4}}).collect(Collectors.toMap(d -> (Character) d[0], d -> (Integer) d[1]));
    private static int part1 = 0;
    private static Long lineScore = 0L;
    private static List<Long> part2 = new ArrayList<>();
    private static void scoreLine(String line) {
        List<Character> expectedBrace = new ArrayList<>(); 
        char[] braces = line.toCharArray();
        for(int i = 0; i < braces.length; ++i) {
            if (pairs.containsKey(braces[i])) {expectedBrace.add(pairs.get(braces[i]));}
            else if (expectedBrace.get(expectedBrace.size() - 1) == braces[i]) { expectedBrace.remove(expectedBrace.size() - 1);}
            else {
                part1 += score.get(braces[i]);
                break;
            }
        } 
    }
    private static void scoreIncompleteLine(String line) {
        List<Character> expectedBrace = new ArrayList<>(); 
        char[] braces = line.toCharArray();
        boolean corrupted = false;
        for(int i = 0; i < braces.length; ++i) {
            if (pairs.containsKey(braces[i])) {expectedBrace.add(pairs.get(braces[i]));}
            else if (expectedBrace.get(expectedBrace.size() - 1) == braces[i]) { expectedBrace.remove(expectedBrace.size() - 1);}
            else {
                corrupted = true;
                break;
            }
        } 
        if (!corrupted) {
            Collections.reverse(expectedBrace);
            lineScore = 0L;
            expectedBrace.forEach(x -> lineScore = lineScore * 5 + scoreP2.get(x));
            part2.add(lineScore);
        }
    }
    public static void Part1(String path) throws Exception {
        List<String> lines = Files.lines(Paths.get(path))
        .collect(Collectors.toList());
        lines.forEach(line -> scoreLine(line));
        System.out.printf("Part 1: %d\n",part1);
    }

    public static void Part2(String path) throws Exception {
        List<String> lines = Files.lines(Paths.get(path))
        .collect(Collectors.toList());
        lines.forEach(line -> scoreIncompleteLine(line));
        part2.sort(Comparator.naturalOrder());
        System.out.printf("Part 2: %d\n",part2.get(part2.size() / 2));
    }
    public static void main(String[] args) {
        try {
            Part1("Day10/input.txt");
            Part2("Day10/input.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
