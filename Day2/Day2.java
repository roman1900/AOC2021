package Day2;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 

public class Day2 {
    public static void main(String[] args) {
        try {
            Part1("Day2/input.txt");
            Part2("Day2/input.txt");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private static void Part1(String path) throws Exception
    {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            int depth = 0;
            int horiz = 0;
            while (myReader.hasNextLine()) {
              String line = myReader.nextLine();
              String[] movement = line.split(" ");
              switch(movement[0])
              {
                  case "forward":
                    horiz += Integer.parseInt(movement[1]);
                    break;
                  case "down":
                    depth += Integer.parseInt(movement[1]);
                    break;
                  case "up":
                    depth -= Integer.parseInt(movement[1]);
                    break;
                  default:
                    throw new Exception(String.format("unknown command: %s", line));
              }
            }
            myReader.close();
            System.out.println(String.format("Part 1 product: %d", horiz * depth));
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    private static void Part2(String path) throws Exception
    {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            long depth = 0;
            long horiz = 0;
            long aim = 0;
            while (myReader.hasNextLine()) {
              String line = myReader.nextLine();
              String[] movement = line.split(" ");
              switch(movement[0])
              {
                  case "forward":
                    int v = Integer.parseInt(movement[1]);
                    horiz += v;
                    depth += v * aim;
                    break;
                  case "down":
                    aim += Integer.parseInt(movement[1]);
                    break;
                  case "up":
                    aim -= Integer.parseInt(movement[1]);
                    break;
                  default:
                    throw new Exception(String.format("unknown command: %s", line));
              }
            }
            myReader.close();
            System.out.println(String.format("Part 2 product: %d", horiz * depth));
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}
