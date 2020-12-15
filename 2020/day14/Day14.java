import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
public class Day14{
  public static void main(String[]args) throws Exception {
    var list = new ArrayList<String>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day14.class.getResourceAsStream(args[0])))){
      for(var s = br.readLine();s!=null;s = br.readLine()){
        list.add(s);
      }
    }
    System.out.println(part1(list));
    System.out.println(part2(list));
  }
  static long part2(List<String>list){
    var memory = new HashMap<Long,Long>();
    var andOr = new long[]{(1L<<36)-1,0};
    var floatAddrs = new long[1];
    var mask = "";
    for(var s : list){
      if (s.startsWith("mask")){
        mask = getMask(s); 
        andOr = new long[]{
          Long.parseLong(mask.replaceAll("0","1").replaceAll("X","0"),2),
          Long.parseLong(mask.replaceAll("X","0"),2)
        }; 
	floatAddrs = getFloatAddrs(mask);
      } else {
        var addrVal = getAddrVal(s);
        for(var addr : floatAddrs){
          long finalAddr = addrVal[0]&andOr[0]|andOr[1]|addr;
          memory.put(finalAddr,addrVal[1]);
        }
      }
    }
    return sumOf(memory);
  }
  static long[] getFloatAddrs(String mask){
        var shifter = getShifter(mask);
	int n = shifter.length;
        var ans = new long[1<<n];
        for(int i = 0; i < 1<<n; ++i){
          for(int j = 0; j < n; ++j){
            ans[i] |= (((long)i&(1<<j))>>j)<<shifter[n-1-j]; 
          }
        }
	return ans;
  }
  static String pad(long addr){
    var sa = Long.toBinaryString(addr);
    return "0".repeat(36-sa.length())+sa;
  }
  static int[] getShifter(String s){
    int cnt = 0; char[]chrs = s.toCharArray();
    for(var c : chrs) cnt += c == 'X'?1:0;
    int[] shifter = new int[cnt];
    for(int i = 0,j = 0; i < chrs.length;++i){
      if (chrs[i] == 'X') shifter[j++] = 35-i; 
    }
    return shifter; 
  }
  static long part1(List<String>list){
    var memory = new HashMap<Long,Long>();
    var andXor = new long[]{(1L<<36)-1,0};
    for(var s : list){
      if (s.startsWith("mask")){
   	    var mask = getMask(s);
  	    andXor = new long[]{
    	      Long.parseLong(mask.replaceAll("1","0").replaceAll("X","1"),2),
    	      Long.parseLong(mask.replaceAll("X","0"),2)};
      } else {
        long[] addrVal = getAddrVal(s);
 	      memory.put(addrVal[0],addrVal[1]&andXor[0]^andXor[1]);
      }
    }
    return sumOf(memory);
  }
  static long sumOf(Map<Long,Long>memory){
    long ans = 0;
    for(var v : memory.values()) ans += v;
    return ans;
  }
  static String getMask(String s){
    return s.substring(s.lastIndexOf(" ")+1);
  }
  static final Pattern pat = Pattern.compile("mem\\[([0-9]+)\\] = ([0-9]+)");
  static long[] getAddrVal(String s){
        var mtchr = pat.matcher(s);
        mtchr.find();
        long addr = Long.parseLong(mtchr.group(1));
	long val = Long.parseLong(mtchr.group(2));
	return new long[]{addr,val};
  }
}
