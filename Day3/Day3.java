package Day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Day3 {

	public static void main(String[] args) {
		try {
			Part1("Day3/input.txt");
			Part2("Day3/input.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void Part1(String path) throws Exception {
		final int bitLength = 12;
		int[] bits = new int[bitLength];
		try {
			int gamma = 0;
			int epsilon = 0;
			List<Integer> input = new ArrayList<>();
			input = Files.lines(Paths.get(path))
					.map(i -> Integer.parseInt(i, 2))
					.collect(Collectors.toList());

			input.forEach(n -> {
				for (int i = 0; i < bitLength; ++i) {
					bits[i] += ((n >> i) & 1) == 1 ? 1 : -1;
				}
			});

			for (int i = 0; i < bitLength; ++i) {
				if (bits[i] > 0)
					gamma += 1 << i;
				else
					epsilon += 1 << i;
			}

			System.out.printf("Part 1:  %d \n", epsilon * gamma);
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	private static final BiFunction<List<Integer>, Integer, Integer> mostCommonBit = (list, bitPosition) -> {
		return list.stream().map(x -> (((x >> bitPosition) & 1) == 1) ? 1 : -1)
				.collect(Collectors.summingInt(Integer::intValue)) >= 0 ? 1 : 0;
	};

	private static final BiFunction<List<Integer>, Integer, Integer> leastCommonBit = (list, bitPosition) -> {
		return list.stream().map(x -> (((x >> bitPosition) & 1) == 1) ? 1 : -1)
				.collect(Collectors.summingInt(Integer::intValue)) >= 0 ? 0 : 1;
	};

	private static void Part2(String path) throws Exception {
		final int bitLength = 12;
		try {
			List<Integer> oxyRating = new ArrayList<Integer>();
			List<Integer> coRating = new ArrayList<Integer>();
			oxyRating = Files.lines(Paths.get(path))
					.map(i -> Integer.parseInt(i, 2))
					.collect(Collectors.toList());
			coRating = oxyRating;
			int bitPos = bitLength - 1;
			while (bitPos >= 0) {
				final int mostCommon = mostCommonBit.apply(oxyRating, bitPos);
				final int leastCommon = leastCommonBit.apply(coRating, bitPos);
				final int fbitPos = bitPos;
				if (oxyRating.size() != 1)
					oxyRating = oxyRating.stream().filter(x -> ((x >> fbitPos) & 1) == mostCommon)
							.collect(Collectors.toList());
				if (coRating.size() != 1)
					coRating = coRating.stream().filter(x -> ((x >> fbitPos) & 1) == leastCommon)
							.collect(Collectors.toList());
				bitPos--;
			}
			if (coRating.size() != 1 || oxyRating.size() != 1)
				throw new Exception();
			else
				System.out.printf("Part 2:  %d\n", coRating.get(0) * oxyRating.get(0));
		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
