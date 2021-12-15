
package Day1;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner; 
public class Day1 {
    
    public static void main(String[] args) {
      Part1("Day1/input.txt");
      Part2("Day1/input.txt");
    }
    public static void Part1(String path)
    {
      try {
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        int last = Integer.MAX_VALUE;
        int count = 0;
        while (myReader.hasNextLine()) {
          int depth = Integer.parseInt(myReader.nextLine());
          if (depth > last) count++; 
          last = depth;
        }
        myReader.close();
        System.out.println(String.format("Part 1 Increases: %d", count));
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
    public static void Part2(String path)
    {
      try {
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        int[] depths = new int[2001];
        int count = 0;
        int result = 0;
        while (myReader.hasNextLine()) {
          depths[count++] = Integer.parseInt(myReader.nextLine());
        }
        myReader.close();
        for (int i = 0; i < count - 3; i++)
        {
          if (depths[i]+depths[i+1]+depths[i+2]<depths[i+1]+depths[i+2]+depths[i+3]) result++;
        }
        System.out.println(String.format("Part 2 Increases: %d", result));
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
}
