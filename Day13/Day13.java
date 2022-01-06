package Day13;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Day13 {

	private static boolean[][] dots;
	private static int xRange = 0;
	private static int yRange = 0;
	private static List<String> folds;

	private static void markDots(String path) throws Exception {
		List<String> lines = Files.lines(Paths.get(path))
				.collect(Collectors.toList());

		xRange = lines.stream().filter(line -> !line.startsWith("fold")).filter(x -> !x.isBlank())
				.mapToInt(x -> Integer.parseInt(x.split(",")[0])).max().orElseThrow(NoSuchElementException::new) + 1;
		yRange = lines.stream().filter(line -> !line.startsWith("fold")).filter(x -> !x.isBlank())
				.mapToInt(x -> Integer.parseInt(x.split(",")[1])).max().orElseThrow(NoSuchElementException::new) + 1;

		dots = new boolean[xRange][yRange];
		lines.stream().filter(line -> !line.startsWith("fold")).filter(x -> !x.isBlank()).forEach(line -> {
			String[] coords = line.split(",");
			dots[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])] = true;
		});
		folds = lines.stream().filter(line -> line.startsWith("fold")).map(x -> x.replace("fold along ", ""))
				.collect(Collectors.toList());
	}

	private static void foldPaper(String folding) {
		String[] f = folding.split("=");
		Integer foldPoint = Integer.parseInt(f[1]);
		if (f[0].equals("x")) {		
			for (int x =  foldPoint + 1; x < xRange; ++x) {
				for (int y = 0; y < yRange; ++y) {
					if (dots[x][y]) 
					{
						dots[foldPoint- (x - foldPoint)][y] = true;
						dots[x][y] = false;
					}
				}
			}
			xRange = foldPoint;
		}
		else {
			for (int y =  foldPoint + 1; y < yRange; ++y) {
				for (int x = 0; x < xRange; ++x) {
					if (dots[x][y]) 
					{
						dots[x][foldPoint - (y - foldPoint)] = true;
						dots[x][y] = false;
					}
				}
			}
			yRange = foldPoint;
		}
	}
	private static int countDots() {
		int result = 0;
		for(int x=0;x<xRange;++x) {
			for(int y=0;y<yRange;++y) {
				if (dots[x][y]) result++;
			}
		}
		return result;
	}
	private static void printPaper() {
		for(int y=0;y<yRange;++y) {
			for(int x=0;x<xRange;++x) {
				if (dots[x][y]) System.out.print("#"); else System.out.print(".");
			}
			System.out.println();
		}
	}
	private static void Part1(String path) throws Exception {
		markDots(path);
		foldPaper(folds.get(0));
		System.out.printf("Part 1: %d\n",countDots());
	}

	private static void Part2(String path) throws Exception {
		markDots(path);
		folds.forEach(f -> foldPaper(f));
		printPaper();
	}

	public static void main(String[] args) {
		try {
			Part1("Day13/input.txt");
			Part2("Day13/input.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
