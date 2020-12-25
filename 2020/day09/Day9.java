import java.util.*;
import java.io.*;
public class Day9{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day9.class.getResourceAsStream(args[0])))){
      var ops = new ArrayList<String[]>();
      var nums = new ArrayList<Long>();
      for(var s = br.readLine();s!=null; s = br.readLine()){
	nums.add(Long.parseLong(s));
      }
      long sum;
      System.out.println(sum = part1(nums, Integer.parseInt(args[1])));
      System.out.println(part2(nums,sum));
    }
  }
  static long part1(List<Long>list, int len){
    var cnts = new HashMap<Long,Integer>();
    for(int i = 0; i < list.size(); ++i){
      if (i >= len){
        boolean match = false;
        for(var k : cnts.keySet()){
	  if (cnts.containsKey(list.get(i)-k) && list.get(i)-k != k){
	    match = true;
	    break;
	  }
        }
        if (!match) return list.get(i);
        cnts.merge(list.get(i-len),-1,Integer::sum);
        cnts.remove(list.get(i-len),0);	
      } 
      cnts.merge(list.get(i),1,Integer::sum);
    }
    return -1;
  }
  static long part2(List<Long>list,long sum){
    var pfxsum = new long[list.size()+1];
    for(int i = 0; i < list.size(); ++i){
      pfxsum[i+1] = pfxsum[i]+list.get(i);       
    }
    for(int i = 2; i < pfxsum.length; ++i){
      int j = Arrays.binarySearch(pfxsum,0,i,pfxsum[i]-sum);
      if (j >= 0){
        var sub = list.subList(j,i);
        return Collections.min(sub)+Collections.max(sub);
      }
    }
    return -1;
  }
}
