import java.util.*;
import java.io.*;
public class Day1{
  public static void main(String[]args)throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day1.class.getResourceAsStream(args[0])))){
        var list = new ArrayList<Integer>();
	for(String s = br.readLine(); s != null; s = br.readLine()){
	  list.add(Integer.valueOf(s));
        }
	Collections.sort(list);
        System.out.println(part1(list, 0, list.size()-1, 2020));
	System.out.println(part2(list));
    }
  }
  private static Long part1(List<Integer>list, int lo, int hi, int sum){
	for(int i = lo, j = hi; i < j;){
	  if (list.get(i)+list.get(j) > sum){
	    --j; 
          } else if (list.get(i)+list.get(j) < sum){
            ++i;
          } else {
	    return ((long)list.get(i)*(long)list.get(j));
	  }
	}
        return null;
  }
  private static Long part2(List<Integer>list){
    for(int i = 0; i+2 < list.size(); ++i){
      var ans = (part1(list,i+1,list.size()-1,2020-list.get(i)));;
       if (ans != null) return ((long)list.get(i)*ans);
    }
    return null;
  }
}
