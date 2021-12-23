package Day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {

	public static Integer Age(Integer a) {
		return a == 0 ? 6 : --a;
	}

	public static void Part1(String path) throws Exception {
		//The dumb way
		List<Integer> fish = Files.lines(Paths.get(path))
				.map(x -> x.split(","))
				.flatMap(c -> Arrays.stream(c).map(Integer::parseInt))
				.collect(Collectors.toList());

		for (int day = 0; day < 80; ++day) {
			Stream<Integer> newFish = fish.stream().filter(x -> x == 0).map(x -> 8);
			fish = Stream.concat(fish.stream().map(x -> Age(x)), newFish)
					.collect(Collectors.toList());
		}
		System.out.printf("%10d\n", fish.size());
	}

	public static void Part2(String path) throws Exception {
		//The concise way
		Map<Integer,Long> fish = Files.lines(Paths.get(path))
				.map(x -> x.split(","))
				.flatMap(c -> Arrays.stream(c).map(Integer::parseInt))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		for (Integer i = 0; i < 9; i++) {
			if (!fish.containsKey(i)) fish.put(i,(long)0);		
		}
		for (int day = 0; day < 256; ++day) {
			Long newFish = fish.get(0);
			for (int d = 1; d < 9; ++d) {
				fish.replace(d - 1,fish.get(d));
			}
			fish.replace(8,newFish);
			fish.replace(6, fish.get(6) + newFish);
		}
		System.out.printf("%20d\n", fish.values().stream().mapToLong(Long::longValue).sum());
	}

	public static void main(String[] args) {
		try {
			Part1("Day6/input.txt");
			Part2("Day6/input.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
