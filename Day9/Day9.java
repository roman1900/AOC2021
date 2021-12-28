package Day9;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;



public class Day9 {

    private static List<Integer> heightMap = new ArrayList<Integer>();
    private static List<Boolean> visitedMap = new ArrayList<Boolean>();
    private static int base = '0';
    private static int mapWidth = 0;
    private static int riskLevel = 0;

    public static void DFS(int basinPoint) {
        visitedMap.set(basinPoint, true);
        int up = (basinPoint - mapWidth) >= 0 && !visitedMap.get(basinPoint - mapWidth) ? heightMap.get(basinPoint - mapWidth)
                : Integer.MAX_VALUE;
        int down = (basinPoint + mapWidth) < heightMap.size() && !visitedMap.get(basinPoint + mapWidth)
                ? heightMap.get(basinPoint + mapWidth)
                : Integer.MAX_VALUE;
        int left = (basinPoint - 1) >= 0 && (basinPoint / mapWidth) == ((basinPoint - 1) / mapWidth)
                && !visitedMap.get(basinPoint - 1) ? heightMap.get(basinPoint - 1) : Integer.MAX_VALUE;
        int right = (basinPoint + 1) < heightMap.size() && (basinPoint / mapWidth) == ((basinPoint + 1) / mapWidth)
                && !visitedMap.get(basinPoint + 1) ? heightMap.get(basinPoint + 1)
                        : Integer.MAX_VALUE;
        if (up < 9)
            DFS(basinPoint - mapWidth);
        if (down < 9)
            DFS(basinPoint + mapWidth);
        if (left < 9)
            DFS(basinPoint - 1);
        if (right < 9)
            DFS(basinPoint + 1);
    }
    private static long countVisits() {
        return visitedMap.stream().filter(x -> x).count();
    }

    private static void resetVisited() {
        ListIterator<Boolean> i = visitedMap.listIterator();
        while (i.hasNext()) {
            i.next();
            i.set(false);
        }
    }
    public static void Part1(String path) throws Exception {
        List<String> lines = Files.lines(Paths.get(path))
                .collect(Collectors.toList());
        mapWidth = lines.get(0).length();
        lines.forEach(x -> {
            x.chars().forEach(
                    i -> {
                        heightMap.add(i - base);
                        visitedMap.add(false);
                    });
        });
        for (int i = 0; i < heightMap.size(); ++i) {
            int mid = heightMap.get(i);
            int up = (i - mapWidth) >= 0 ? heightMap.get(i - mapWidth) : Integer.MAX_VALUE;
            int down = (i + mapWidth) < heightMap.size() ? heightMap.get(i + mapWidth) : Integer.MAX_VALUE;
            int left = (i - 1) >= 0 && (i / mapWidth) == ((i - 1) / mapWidth) ? heightMap.get(i - 1)
                    : Integer.MAX_VALUE;
            int right = (i + 1) < heightMap.size() && (i / mapWidth) == ((i + 1) / mapWidth) ? heightMap.get(i + 1)
                    : Integer.MAX_VALUE;
            riskLevel += (mid < up && mid < down && mid < left && mid < right) ? mid + 1 : 0;
        }
        System.out.printf("Part 1: %d\n", riskLevel);
    }

    public static void Part2(String path) throws Exception {
        List<String> lines = Files.lines(Paths.get(path))
                .collect(Collectors.toList());
        mapWidth = lines.get(0).length();
        heightMap = new ArrayList<Integer>();
        visitedMap = new ArrayList<Boolean>();
        lines.forEach(x -> {
            x.chars().forEach(
                    i -> {
                        heightMap.add(i - base);
                        visitedMap.add(false);
                    });
        });
        List<Long> basins = new ArrayList<Long>();
        for (int i = 0; i < heightMap.size(); ++i) {
            int mid = heightMap.get(i);
            int up = (i - mapWidth) >= 0 ? heightMap.get(i - mapWidth) : Integer.MAX_VALUE;
            int down = (i + mapWidth) < heightMap.size() ? heightMap.get(i + mapWidth) : Integer.MAX_VALUE;
            int left = (i - 1) >= 0 && (i / mapWidth) == ((i - 1) / mapWidth) ? heightMap.get(i - 1)
                    : Integer.MAX_VALUE;
            int right = (i + 1) < heightMap.size() && (i / mapWidth) == ((i + 1) / mapWidth) ? heightMap.get(i + 1)
                    : Integer.MAX_VALUE;
            if (mid < up && mid < down && mid < left && mid < right) {
                DFS(i);
                basins.add(countVisits());
                resetVisited();
            }
        }
        basins.sort(Comparator.reverseOrder());
        System.out.printf("Part 2: %d", basins.get(0) * basins.get(1) * basins.get(2));
    }

    public static void main(String[] args) {
        try {
            Part1("Day9/input.txt");
            Part2("Day9/input.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
