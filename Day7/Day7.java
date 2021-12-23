package Day7;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 {
	public static void Part1(String path) throws Exception {
		TreeMap<Integer, Long> crabs = Files.lines(Paths.get(path))
				.map(x -> x.split(","))
				.flatMap(c -> Arrays.stream(c).map(Integer::parseInt))
				.collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
		
		Long leastFuel = Long.MAX_VALUE;

		for (int l = crabs.firstKey(); l <= crabs.lastKey(); ++l) {
			Long currentFuel = 0L;
			int key = crabs.firstKey();
			while (currentFuel < leastFuel) {
				currentFuel +=  Math.abs(key - l) * crabs.get(key);
				if(key == crabs.lastKey()) break;
				key = crabs.higherKey(key);
			}
			if (currentFuel < leastFuel) leastFuel = currentFuel;
		}
		
		System.out.printf("Part 1: %d\n",leastFuel);
	}

	public static void Part2(String path) throws Exception {
		TreeMap<Integer, Long> crabs = Files.lines(Paths.get(path))
				.map(x -> x.split(","))
				.flatMap(c -> Arrays.stream(c).map(Integer::parseInt))
				.collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
		
		Double leastFuel = Double.MAX_VALUE;

		for (int l = crabs.firstKey(); l <= crabs.lastKey(); ++l) {
			Double currentFuel = 0.0;
			int key = crabs.firstKey();
			while (currentFuel < leastFuel) {
				currentFuel +=  (Math.abs(key - l) / 2.0) * (Math.abs(key - l) + 1) * crabs.get(key);
				if(key == crabs.lastKey()) break;
				key = crabs.higherKey(key);
			}
			if (currentFuel < leastFuel) leastFuel = currentFuel;
		}
		
		System.out.printf("Part 2: %.2f\n",leastFuel);
	}

	public static void main(String[] args) {
		try {
			Part1("Day7/input.txt");
			Part2("Day7/input.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
