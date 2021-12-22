package Day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
	
    private static class Floor{
        private int[][] grid;
		private int gridx_size;
		private int gridy_size;

		public Floor(List<String> input){
			List<Integer> xs = new ArrayList<>();
			List<Integer> ys = new ArrayList<>();
			input.forEach(line -> {
			String[] coords = line.split(" -> ");
			int x1 = Integer.parseInt(coords[0].split(",")[0]);
			int y1 = Integer.parseInt(coords[0].split(",")[1]);
			int x2 = Integer.parseInt(coords[1].split(",")[0]);
			int y2 = Integer.parseInt(coords[1].split(",")[1]);
			if (x1 == x2 || y1 == y2){
				xs.add(x1);
				xs.add(x2);
				ys.add(y1);
				ys.add(y2);
			}
			});
			gridx_size = xs.stream().max(Integer::compare).get()+1;
			gridy_size = ys.stream().max(Integer::compare).get()+1;
			grid = new int[gridx_size][gridy_size];
		}

        public void mapToFloor(String line,boolean diagonals){
            String[] coords = line.split(" -> ");
			int x1 = Integer.parseInt(coords[0].split(",")[0]);
			int y1 = Integer.parseInt(coords[0].split(",")[1]);
			int x2 = Integer.parseInt(coords[1].split(",")[0]);
			int y2 = Integer.parseInt(coords[1].split(",")[1]);
			if( x1 == x2 ) {
				for(int y = Math.min(y1,y2); y <= Math.max(y1,y2); ++y)
				{
					grid[x1][y]++;
				}
			}
			else if( y1 == y2 ) {
				for(int x = Math.min(x1,x2); x <= Math.max(x1,x2); ++x)
				{
					grid[x][y1]++;
				}
			}
			else if(diagonals){
				int x = x1 > x2 ? -1 : 1;
				int y = y1 > y2 ? -1 : 1;

				while (x1 != x2 && y1 != y2) {
						grid[x1][y1]++;
						x1 += x;
						y1 += y;
				}
				grid[x2][y2]++;
			}
        }

		public long countOverlaps() {
			return Arrays.asList(grid).stream().flatMapToInt(Arrays::stream).filter(x -> x >= 2).count();
		}

		public void printBoard() {
			for(int y = 0; y<gridx_size; ++y) {
				for(int x = 0; x<gridy_size; ++x) {
					if (grid[x][y]==0) System.out.print(" .");
					else System.out.printf("%2d",grid[x][y]);
				}
				System.out.println();
			}
		}
    }
    public static void Part1(String path) throws Exception {
        
        List<String> lines = Files.lines(Paths.get(path))
                    .collect(Collectors.toList());
		Floor floor = new Floor(lines);
        lines.forEach(x -> floor.mapToFloor(x,false));
		System.out.printf("Part 1: %d\n",floor.countOverlaps());
    }
    public static void Part2(String path) throws Exception {
		List<String> lines = Files.lines(Paths.get(path))
                    .collect(Collectors.toList());
		Floor floor = new Floor(lines);
		lines.forEach(x -> floor.mapToFloor(x,true));
		System.out.printf("Part 2: %d\n",floor.countOverlaps());
    }
    
    public static void main(String[] args) {
        try {
            Part1("Day5/input.txt");
            Part2("Day5/input.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
