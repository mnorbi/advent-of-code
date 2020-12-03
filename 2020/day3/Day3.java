import java.util.*;
import java.io.*;
public class Day3{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day3.class.getResourceAsStream(args[0])))){
      var list = new ArrayList<String>();
      for(var s = br.readLine(); s != null; s = br.readLine()){
	list.add(s);
      }
      var arr = list.toArray(new String[list.size()]); 
      System.out.println(part1(arr,1,3)); 
      System.out.println(part2(arr));
    }
  }
  private static int part1(String[]arr,int dr, int dc){
    int cnt = 0, r = arr.length, c = arr[0].length(), i = 0, j = 0;
    while(i+dr<arr.length){
      j = (j+dc)%c;
      i += dr;
      cnt += arr[i].charAt(j) == '#'?1:0;
    }
    return cnt;
  }
  private static long part2(String[]arr){
      var vals = new long[]{part1(arr,1,1), part1(arr,1,3), part1(arr,1,5),part1(arr,1,7),part1(arr,2,1)};
      long mul = 1L;
      for(long v:vals) mul *= v; 
      return mul;
  }
}
