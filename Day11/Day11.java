package Day11;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {
    private static List<Integer> map = new ArrayList<>();
    private static Integer mapWidth = 0;
    private static List<Integer> flashers = new ArrayList<>();
    private static Long totalFlashes = 0L;

    private static void buildMap(String path) throws Exception {
        List<String> lines = Files.lines(Paths.get(path))
                .collect(Collectors.toList());
        mapWidth = lines.get(0).length();
        map = new ArrayList<>();
        lines.forEach(line -> Stream.of(line.split("(?!^)")).forEach(n -> map.add(Integer.parseInt(n))));
        totalFlashes = 0L;
    }

    private static void incrementMap() {
        map.replaceAll(x -> x = x == 9 ? 0 : x + 1);
        flashers.clear();
        for (int i = 0; i < map.size(); ++i) {
            if (map.get(i) == 0) {
                flashers.add(i);
                totalFlashes++;
            }
        }
    }

    private static void incrementOcto(int index) {
        int energy = map.get(index);
        if (energy != 0) {
            energy = energy == 9 ? 0 : energy + 1;
            if (energy == 0) {
                flashers.add(index);
                totalFlashes++;
            }
            map.set(index, energy);
        }
    }

    private static void printMap() {
        for (int i = 0; i < map.size(); ++i) {
            if (i % mapWidth == 0) {
                System.out.println();
            }
            System.out.printf("%3d", map.get(i));
        }
        System.out.println();
    }

    public static void Part1(String path) throws Exception {
        buildMap(path);
        for (int i = 0; i < 100; ++i) {
            incrementMap();
            boolean flashing = flashers.size() > 0;
            while (flashing) {
                int currentOcto = flashers.remove(0);
                if (currentOcto + mapWidth < map.size()) {
                    incrementOcto(currentOcto + mapWidth);
                    if ((currentOcto + mapWidth - 1) / mapWidth == (currentOcto / mapWidth) + 1) {
                        incrementOcto(currentOcto + mapWidth - 1);
                    }
                    if (currentOcto + mapWidth + 1 < map.size()
                            && (currentOcto + mapWidth + 1) / mapWidth == (currentOcto / mapWidth) + 1) {
                        incrementOcto(currentOcto + mapWidth + 1);
                    }
                }
                if (currentOcto - mapWidth >= 0) {
                    incrementOcto(currentOcto - mapWidth);
                    if (currentOcto - mapWidth - 1 >= 0
                            && (currentOcto - mapWidth - 1) / mapWidth == (currentOcto / mapWidth) - 1) {
                        incrementOcto(currentOcto - mapWidth - 1);
                    }
                    if ((currentOcto - mapWidth + 1) / mapWidth == (currentOcto / mapWidth) - 1) {
                        incrementOcto(currentOcto - mapWidth + 1);
                    }
                }
                if (currentOcto + 1 < map.size() && (currentOcto + 1) / mapWidth == currentOcto / mapWidth) {
                    incrementOcto(currentOcto + 1);
                }
                if (currentOcto - 1 >= 0 && (currentOcto - 1) / mapWidth == currentOcto / mapWidth) {
                    incrementOcto(currentOcto - 1);
                }
                flashing = flashers.size() > 0;
            }
        }
        printMap();
        System.out.printf("Part 1: %d\n", totalFlashes);
    }

    public static void Part2(String path) throws Exception {
        buildMap(path);
        int step = 0;
        while (map.stream().reduce(0, Integer::sum) != 0) {
            
            incrementMap();
            boolean flashing = flashers.size() > 0;
            while (flashing) {
                int currentOcto = flashers.remove(0);
                if (currentOcto + mapWidth < map.size()) {
                    incrementOcto(currentOcto + mapWidth);
                    if ((currentOcto + mapWidth - 1) / mapWidth == (currentOcto / mapWidth) + 1) {
                        incrementOcto(currentOcto + mapWidth - 1);
                    }
                    if (currentOcto + mapWidth + 1 < map.size()
                            && (currentOcto + mapWidth + 1) / mapWidth == (currentOcto / mapWidth) + 1) {
                        incrementOcto(currentOcto + mapWidth + 1);
                    }
                }
                if (currentOcto - mapWidth >= 0) {
                    incrementOcto(currentOcto - mapWidth);
                    if (currentOcto - mapWidth - 1 >= 0
                            && (currentOcto - mapWidth - 1) / mapWidth == (currentOcto / mapWidth) - 1) {
                        incrementOcto(currentOcto - mapWidth - 1);
                    }
                    if ((currentOcto - mapWidth + 1) / mapWidth == (currentOcto / mapWidth) - 1) {
                        incrementOcto(currentOcto - mapWidth + 1);
                    }
                }
                if (currentOcto + 1 < map.size() && (currentOcto + 1) / mapWidth == currentOcto / mapWidth) {
                    incrementOcto(currentOcto + 1);
                }
                if (currentOcto - 1 >= 0 && (currentOcto - 1) / mapWidth == currentOcto / mapWidth) {
                    incrementOcto(currentOcto - 1);
                }
                flashing = flashers.size() > 0;
            }
            step++;
        }
        printMap();
        System.out.printf("Part 2: %d\n", step);
    }

    public static void main(String[] args) {
        try {
            Part1("Day11/input.txt");
            Part2("Day11/input.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
