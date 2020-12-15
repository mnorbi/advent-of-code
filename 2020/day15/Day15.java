import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
public class Day15{
  public static void main(String[]args) throws Exception {
    var list = new ArrayList<Long>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day15.class.getResourceAsStream(args[0])))){
      for(var s : br.readLine().split(",")){
	list.add(Long.parseLong(s));
      }
    }
    System.out.println(part1(list,2020));
    System.out.println(part1(list,30000000));
  }
  static long part1(List<Long>list,int limit){
    var last = new HashMap<Long,Long>();
    long spoken = -1, nextSpoken = -1;
    for(int i = 1; i < limit; ++i){
      if (i <= list.size()){
        spoken = list.get(i-1);
      } else {
        spoken = nextSpoken;
      }
      nextSpoken = i-last.getOrDefault(spoken,(long)i);
      last.put(spoken, (long)i);
    }
    return nextSpoken;
  }
}
