package Day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day12 {
	private static class Node {
		String name;
		Boolean smallCave;
		Boolean visited;
		Integer visitCount = 0;

		Node(String name, Boolean smallCave) {
			this.name = name;
			this.smallCave = smallCave;
			this.visited = false;
		}
	}

	private static List<List<String>> neighbours = new ArrayList<List<String>>();

	private static List<Node> caves = new ArrayList<>();

	private static Integer paths = 0;

	private static boolean containsNode(String name) {
		return caves.stream().filter(node -> node.name.equals(name)).findFirst().isPresent();
	}

	private static Integer getNodeIndex(String name) {
		return caves.indexOf(caves.stream().filter(node -> node.name.equals(name)).findFirst().get());
	}

	private static void decodeLine(String line) throws Exception {
		String[] nodeLink = line.split("-");
		if (nodeLink.length != 2)
			throw (new Exception(String.format("Error encountered decoding line (%s)", line)));
		for (int i = 0; i < 2; ++i) {
			if (!containsNode(nodeLink[i])) {
				caves.add(new Node(nodeLink[i], nodeLink[i].toLowerCase() == nodeLink[i]));
			}
			int index = getNodeIndex(nodeLink[i]);
			if (neighbours.size() < index + 1)
				neighbours.add(index, new ArrayList<>());
			int flip = i ^ 1;
			if (!neighbours.get(index).contains(nodeLink[flip]))
				neighbours.get(index).add(nodeLink[flip]);
		}

	}

	private static void populateCaves(String path) throws Exception {
		paths = 0;
		caves = new ArrayList<>();
		neighbours = new ArrayList<List<String>>();
		List<String> lines = Files.lines(Paths.get(path))
				.collect(Collectors.toList());

		lines.forEach(x -> {
			try {
				decodeLine(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private static void countPaths(int currentNode) {
		if (caves.get(currentNode).name.equals("end")) {
			paths++;
			return;
		}
		for (String neighbour : neighbours.get(currentNode)) {

			if (caves.get(getNodeIndex(neighbour)).smallCave &&
					!caves.get(getNodeIndex(neighbour)).visited) {
				caves.get(getNodeIndex(neighbour)).visited = true;
				countPaths(getNodeIndex(neighbour));
				caves.get(getNodeIndex(neighbour)).visited = false;
			} else if (!caves.get(getNodeIndex(neighbour)).smallCave) {
				countPaths(getNodeIndex(neighbour));
			}
		}

	}

	private static void countDoublePaths(int currentNode, boolean twice) {
		if (caves.get(currentNode).name.equals("end")) {
			paths++;
			return;
		}
		for (String neighbour : neighbours.get(currentNode)) {

			if (caves.get(getNodeIndex(neighbour)).smallCave &&
					caves.get(getNodeIndex(neighbour)).visitCount < 2 && !twice) {
				caves.get(getNodeIndex(neighbour)).visitCount += 1;
				countDoublePaths(getNodeIndex(neighbour), caves.get(getNodeIndex(neighbour)).visitCount == 2);
				caves.get(getNodeIndex(neighbour)).visitCount -= 1;
			} else if (caves.get(getNodeIndex(neighbour)).smallCave
					&& caves.get(getNodeIndex(neighbour)).visitCount == 0 && twice) {
				caves.get(getNodeIndex(neighbour)).visitCount += 1;
				countDoublePaths(getNodeIndex(neighbour), twice);
				caves.get(getNodeIndex(neighbour)).visitCount -= 1;
			} else if (!caves.get(getNodeIndex(neighbour)).smallCave) {
				countDoublePaths(getNodeIndex(neighbour), twice);
			}
		}

	}

	public static void Part1(String path) throws Exception {
		populateCaves(path);
		int start = getNodeIndex("start");
		caves.get(start).visited = true;
		countPaths(start);
		System.out.printf("Part 1: %d\n", paths);
	}

	public static void Part2(String path) throws Exception {
		populateCaves(path);
		int start = getNodeIndex("start");
		caves.get(start).visitCount = 2;
		countDoublePaths(start, false);
		System.out.printf("Part 2: %d\n", paths);
	}

	public static void main(String[] args) {
		try {
			Part1("Day12/input.txt");
			Part2("Day12/input.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
