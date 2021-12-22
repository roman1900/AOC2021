package Day4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {
	
	private static class Board {
		
		public Element[][] grid = new Element[5][5];
		public int[] count = new int[10]; 
		public void markOff(int value){
			for(int r=0; r<5; ++r){
				for(int c=0; c<5;++c){
					if (grid[r][c].value == value){
						grid[r][c].found = true;
						count[r]++;
						count[c+5]++;
						break;
					}
				}
			}
		}
		public boolean winner(){
			return IntStream.of(count).anyMatch(x -> x == 5);
		}
		public int score(){
			int sum = 0;
			for(int r=0; r<5; ++r){
				for(int c=0; c<5;++c){
					if (!grid[r][c].found) sum += grid[r][c].value;
				}
			}
			return sum;
		}
	}
	
	private static class Element {
		int value;
		boolean found;
		public Element(int v,boolean f)
		{
			value = v;
			found = f;
		}
		public String Hilight()
		{
			if (found) return "\u001B[1m";
			else return "";
		}
	}

	private static void printBoard(Board board)
	{
		for (int r =0; r<5; ++r) {
			for (int c=0; c<5; ++c) {
				System.out.printf("%s%2d  ",board.grid[r][c].Hilight(),board.grid[r][c].value);
			}
			System.out.println();
		}
		System.out.println();
	}

	private static List<Board> populateBoards(List<String> lines) {
		int row = 0;
		Board currentBoard = new Board();
		List<Board> boards = new ArrayList<>();
		for (int i = 2; i<lines.size();++i){
			String currentLine = lines.get(i).strip();
			if (!currentLine.equals("")) {
				currentBoard.grid[row] = Arrays.asList(currentLine.split("\s+")).stream()
				.map(x -> new Element(Integer.parseInt(x),false)).collect(Collectors.toList()).toArray(new Element[0]);
				row++;
			} 
			else {
				boards.add(currentBoard);
				currentBoard = new Board();
				row = 0;
			}
		}
		return boards;
	}

	public static void main(String[] args) {
		try {
			Part1("Day4/input.txt");
			Part2("Day4/input.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Part1(String path) throws Exception {
		List<String> lines = Files.lines(Paths.get(path))
					.collect(Collectors.toList());
		List<Integer> draw = Arrays.asList(lines.get(0).split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		List<Board> boards = populateBoards(lines);
		//boards.forEach(x -> printBoard(x));
		for (Integer b : draw) {
			boards.forEach(x -> x.markOff(b));
			int w = boards.stream().filter(x -> x.winner()).mapToInt(x -> x.score()).sum();
			if (w > 0)
			{
				System.out.printf("Part 1: %d\n", w * b);
				break;
			}
		}

	}
	public static void Part2(String path) throws Exception {
		List<String> lines = Files.lines(Paths.get(path))
					.collect(Collectors.toList());
		List<Integer> draw = Arrays.asList(lines.get(0).split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		List<Board> boards = populateBoards(lines);
		List<Board> winners = new ArrayList<Board>();
		//boards.forEach(x -> printBoard(x));
		for (Integer b : draw) {
			boards.forEach(x -> x.markOff(b));
			if (boards.size() > 1)
				boards.removeIf(x -> x.winner());
			else {
				if (boards.stream().allMatch(x -> x.winner())){
					System.out.printf("Part 2: %d", boards.get(0).score() * b);
					break;
				}
			}
		}
	}
}
