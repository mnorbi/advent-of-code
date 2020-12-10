import java.util.*;
import java.io.*;
public class Day10{
  public static void main(String[]args) throws Exception {
    var list = new ArrayList<Long>();
    list.add(0L);
    try(var br = new BufferedReader(new InputStreamReader(Day10.class.getResourceAsStream(args[0])))){
      for(var s = br.readLine();s!=null;s=br.readLine()){
        list.add(Long.parseLong(s));
      }
    }
    Collections.sort(list);
    list.add(list.get(list.size()-1)+3);
    var arr = list.toArray(new Long[0]);
    System.out.println(part1(arr));
    System.out.println(part2(arr));
  }
  static long part2(Long[]arr){
    int n = arr.length;
    var dp = new long[n];
    dp[0] = 1;
    for(int i = 0; i < n;++i){
      for(int j = i-1; j >= 0 && arr[i]-arr[j] <= 3;--j){
        dp[i] += dp[j];
      }
    }
    return dp[n-1];
  }
  static long part1(Long[]arr){
    int diff1=0, diff3=0;
    for(int i = 1; i < arr.length; ++i){
      var diff = arr[i]-arr[i-1];
      diff1 += diff == 1 ? 1 : 0;
      diff3 += diff == 3 ? 1 : 0;
    }
    
    return diff1*diff3;
  }
}
