import java.util.*;
import java.io.*;
public class Day8{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day8.class.getResourceAsStream(args[0])))){
      var ops = new ArrayList<String[]>();
      for(var s = br.readLine();s!=null; s = br.readLine()){
	ops.add(s.split("\\s+"));
      }
      System.out.println(part1(ops)[0]);
      System.out.println(part2(ops));
    }
  }
  static long[] part1(List<String[]>ops){
      var seen = new HashSet<>();
      var acc = 0L;
      for(int p = 0;p < ops.size();){
        if (seen.contains(p)) return new long[]{acc,-1};
	seen.add(p);
 	long val = Long.parseLong(ops.get(p)[1]);	
        switch(ops.get(p)[0]){
 	  case "acc": acc += val;
          case "nop": ++p; break;
	  case "jmp": p+=val; break;
          default:
        }	
      }
      return new long[]{acc,1};
  }
  static long part2(List<String[]>ops){
    for(int i = 0; i < ops.size(); ++i){
      for(var pair : List.of(new String[]{"jmp","nop"},new String[]{"nop","jmp"})){
        if (pair[0].equals(ops.get(i)[0])){
          ops.get(i)[0] = pair[1];
          var ans = part1(ops);
          if (ans[1] == 1) return ans[0];
          ops.get(i)[0] = pair[0];
        } 
      }
    }
    return -1;
  }
}
