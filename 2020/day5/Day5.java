import java.util.*;
import java.io.*;
public class Day5{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day5.class.getResourceAsStream(args[0])))){
      long maxPart1 = Long.MIN_VALUE;
      var seatIdsPart2 = new ArrayList<Long>();
      for(var s = br.readLine();s != null; s = br.readLine()){
	long row = parse(s,0,7,'F','B');
	long col = parse(s,7,10,'L','R');
	long seatId = row*8+col;
	seatIdsPart2.add(seatId);
	maxPart1 = Math.max(maxPart1,seatId);
	System.out.println(List.of(row*8+col,row,col));
      }
      System.out.println(maxPart1);
      Collections.sort(seatIdsPart2);
      for(int i = 1; i+1 < seatIdsPart2.size(); ++i){
	if (seatIdsPart2.get(i)+1 != seatIdsPart2.get(i+1)){
	  System.out.println(seatIdsPart2.get(i)+1);
	  break;
	}
      }
    }
  }
  static long parse(String s,int fr,int to, char left, char right){
    long lo = 0, hi =(1<<(to-fr))-1;
    for(int i = fr; i < to; ++i){
      long mid = lo+(hi-lo)/2;
      if (s.charAt(i) == left) hi = mid;
      else lo = mid+1;
    }
    return lo;
  }
}
