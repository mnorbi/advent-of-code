import java.util.*;
import java.io.*;
public class Day2{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day2.class.getResourceAsStream(args[0])))){
      int okPart1 = 0, okPart2 = 0;	
      for(var s = br.readLine(); s != null; s = br.readLine()){
	var tokens = s.split("\\s+");
	var limits = tokens[0].split("-");
	int lo = Integer.valueOf(limits[0]), hi = Integer.valueOf(limits[1]);
	char chr = tokens[1].charAt(0);
	var pass = tokens[2];
	okPart1 += part1(pass, lo, hi, chr);
	okPart2 += part2(pass, lo, hi, chr);
      }
      System.out.println(List.of(okPart1,okPart2));
    }
  }
  private static int part1(String pass, int lo, int hi, char chr){

	int cnt = 0, ok = 0;
	for(var c : pass.toCharArray()){
	  if (c == chr) ++cnt;
	}
	if (lo <= cnt && cnt <= hi) ++ok;
	return ok;	
  }
  private static int part2(String pass, int posa, int posb, char chr){
	if (pass.charAt(posa-1) == chr && pass.charAt(posb-1) != chr
            || pass.charAt(posa-1) != chr && pass.charAt(posb-1) == chr){
	  return 1;
	}
	return 0;
  }
}
