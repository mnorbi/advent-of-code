import java.util.*;
import java.io.*;
public class Day7{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day7.class.getResourceAsStream(args[0])))){
      var innerOuterAdj = new HashMap<String,List<String>>();
      var outerInnerAdj = new HashMap<String,List<String>>(); 
      for(var s = br.readLine();s!=null; s = br.readLine()){
	var tokens = s.split("bags contain ");
	for(var v : tokens[1].split(",")){
	  var outer = tokens[0].strip();
	  var inner = v.replaceAll("\\s*[0-9]+ (.+) bag.*","$1").strip();
	  innerOuterAdj.computeIfAbsent(inner,k->new ArrayList<>()).add(outer);
	  var innerCount = v.replaceAll("\\s*([0-9]+) (.+) bag.*","$1 $2").strip();
	  outerInnerAdj.computeIfAbsent(outer,k->new ArrayList<>()).add(innerCount);
	}
      }
      System.out.println(part1(innerOuterAdj,"shiny gold"));
      System.out.println(-1+part2(outerInnerAdj,"shiny gold",1L));
    }
  }
  static int part1(Map<String,List<String>>adj,String start){
    var deq = new ArrayDeque<String>();
    var seen = new HashSet<String>();
    deq.offer(start);
    seen.add(start);
    while(!deq.isEmpty()){
      var cur = deq.poll();
      for(var next : adj.getOrDefault(cur,List.of())){
	if (seen.add(next)) deq.offer(next);
      }
    }
    return seen.size()-1;
  }
  static long part2(Map<String,List<String>> adj, String parentBag, long mul){
    long ans = mul;
    for(var bag : adj.get(parentBag)){
      if ("no other bags.".equals(bag)) continue;
      var tokens = bag.split("\\s+",2);
      long cnt = Long.valueOf(tokens[0]);
      var next = tokens[1];
      ans += part2(adj,next,mul*cnt); 
    }
    return ans;
  }
}
