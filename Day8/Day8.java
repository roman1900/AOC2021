package Day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {
	private static final Map<Integer, Integer> display = Stream.of(new Integer[][] {
			{ 0, 6 },
			{ 1, 2 },
			{ 2, 5 },
			{ 3, 5 },
			{ 4, 4 },
			{ 5, 5 },
			{ 6, 6 },
			{ 7, 3 },
			{ 8, 7 },
			{ 9, 6 }
	}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	private static final int[][] digits = new int[][] { {}, {}, { 1 }, { 7 }, { 4 }, { 2, 3, 5 }, { 0, 6, 9 }, { 8 } };
	private static int segment[][] = new int[][] { { 1, 1, 1, 0, 1, 1, 1 },
			{ 0, 0, 1, 0, 0, 1, 0 },
			{ 1, 0, 1, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 1 },
			{ 0, 1, 1, 1, 0, 1, 0 },
			{ 1, 1, 0, 1, 0, 1, 1 },
			{ 1, 1, 0, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 1, 0 },
			{ 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 1, 1 } };
	private static int[] digitBits = new int[10];
	// private static HashMap<Integer, String[]> wires;
	private static String[] loc = new String[7];
	private static Integer part1 = 0;
	private static Integer part2 = 0;

	private static void PopulateWires() {
		for (int s = 0; s < 7; ++s) {
			loc[s] = "abcdefg";
		}
		for (int i = 0; i < 10; ++i) {
			digitBits[i] = segmentsToBits(segment[i]);
		}
	}

	private static boolean IsUnique(String pattern) {
		return Collections.frequency(new ArrayList<Integer>(display.values()), pattern.length()) == 1;
	}

	private static void CountKnownLine(String[] line) {
		List<String> output = Arrays.asList(line[1].split(" "));
		output.forEach(o -> {
			if (IsUnique(o))
				part1++;
		});
	}

	private static int segmentsToBits(int[] segments) {
		int value = 0;
		for (int s = 0; s < 7; s++) {
			value = value + (segments[s] << s);
		}
		return value;
	}

	private static int[] bitsToSegments(int bitSegment) {
		int[] segment = new int[7];
		for (int s = 0; s < 7; s++) {
			segment[s] = (bitSegment & (1 << s)) >> s;
		}
		return segment;
	}

	private static int[] commonWires(String i) {
		int digitSet = i.length();
		int bits = segmentsToBits(segment[digits[digitSet][0]]);
		for (int m = 1; m < digits[digitSet].length; ++m) {
			bits = bits & segmentsToBits(segment[digits[digitSet][m]]);
		}
		return bitsToSegments(bits);
	}

	private static void MapSegments(String i) {
		int digitSet = i.length();
		for (int m = 0; m < digits[digitSet].length; ++m) {
			if (digits[digitSet].length == 1) {
				for (int s = 0; s < 7; ++s) {
					if (segment[digits[digitSet][m]][s] == 1) {
						for (Character wire : loc[s].toCharArray()) {
							if (!i.contains(wire.toString())) {
								loc[s] = loc[s].replace(wire.toString(), "");
							}
						}
					} else {
						for (Character wire : loc[s].toCharArray()) {
							if (i.contains(wire.toString())) {
								loc[s] = loc[s].replace(wire.toString(), "");
							}
						}
					}
				}
			}
		}

		for (int m = 0; m < digits[digitSet].length; ++m) {
			if (digits[digitSet].length > 1) {
				int[] commonWire = commonWires(i);

				for (int s = 0; s < 7; ++s) {
					if (commonWire[s] == 1) {
						for (Character wire : loc[s].toCharArray()) {
							if (!i.contains(wire.toString())) {
								loc[s] = loc[s].replace(wire.toString(), "");
							}
						}
					}
				}

			}
		}

		for (int s = 0; s < 7; s++) {
			if (loc[s].length() == 1) {
				for (int t = 0; t < 7; t++) {
					if (t != s) {
						if (loc[t].contains(loc[s]))
							loc[t] = loc[t].replace(loc[s], "");
					}
				}
			}
		}

	}

	private static int encodeSegments(String wires) {
		int result = 0;

		for (int i = 0; i < 7; ++i) {
			if (wires.contains(loc[i]))
				result = result + (1 << i);
		}

		return result;
	}

	private static void DecodeLine(String[] line) {
		List<String> input = Arrays.asList(line[0].split(" "));
		PopulateWires();
		input.sort(Comparator.comparing(String::length));
		input.forEach(i -> MapSegments(i));
		List<String> output = Arrays.asList(line[1].trim().split(" "));
		String result = "";
		for (String o : output) {
			int f = encodeSegments(o);
			for (Integer i = 0; i < 10; i++) {
				if (digitBits[i] == f)
					result = result + i.toString();
			}
		}
		part2 = part2 + Integer.parseInt(result);
	}

	public static void Part1(String path) throws Exception {
		List<String> lines = Files.lines(Paths.get(path))
				.collect(Collectors.toList());
		lines.forEach(x -> CountKnownLine(x.split(Pattern.quote("|"))));
		System.out.printf("Part 1: %d\n", part1);
	}

	public static void Part2(String path) throws Exception {
		List<String> lines = Files.lines(Paths.get(path))
				.collect(Collectors.toList());
		lines.forEach(x -> DecodeLine(x.split(Pattern.quote("|"))));
		System.out.printf("Part 2: %d\n", part2);
	}

	public static void main(String[] args) {
		try {
			Part1("Day8/input.txt");
			Part2("Day8/input.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
