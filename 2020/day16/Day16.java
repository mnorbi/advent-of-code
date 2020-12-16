import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
public class Day16{
  public static void main(String[]args) throws Exception {
    List<String>[]in = new List[3];
    Arrays.asList(in).replaceAll(i->new ArrayList<>());
    int i = 0;
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day16.class.getResourceAsStream(args[0])))){
	for(var s = br.readLine();s != null; s = br.readLine()){
          if("".equals(s)) { ++i; continue; }
	  in[i].add(s);
	}
    }
    var invalids = new HashSet<Integer>();
    System.out.println(part1(in, invalids));
    System.out.println(part2(in,invalids));
  }
  static long part2(List<String>[]in, Set<Integer> invalidTickets){
    var ranges = ranges(in[0]);
    var tickets = parseTickets(in);
    var compat = findCompatibility(invalidTickets,ranges,tickets); 
    var mapping = findMapping(compat);
    long ans = 1;
    {int i = 0; for(var name : in[0]){
      if (name.startsWith("departure")){
        ans *= tickets[tickets.length-1][mapping[i]];
      }
      ++i;
    }}
    return ans;
  } 
  static int[] findMapping(Set[]compat){
    var seen = new HashSet<Integer>();
    while(seen.size() < compat.length){
      {int i = 0; for(var s : compat){
        if (s.size() == 1 && !seen.contains(i)){
          seen.add(i);
	  {int j = 0; for(var ss : compat){
	    if (i == j++) continue;
            ss.removeAll(s);
          }}
        }
        ++i;
      }}
    }
    int[] mapping = new int[compat.length];
    for(int i = 0; i < compat.length; ++i){
      mapping[(int)compat[i].toArray()[0]] = i;
    }
    return mapping;
  }
  static Set[] findCompatibility(Set<Integer>invalidTickets,long[][][]ranges,long[][]tickets){
    int fieldCnt = tickets[0].length;
    Set<Integer>[] compat = new Set[fieldCnt];
    for(int i = 0; i < fieldCnt; ++i){
      compat[i] = new HashSet<>();
      for(int j = 0; j < fieldCnt; ++j){
        compat[i].add(j);
      }
    }
    for(int tryField = 0; tryField < fieldCnt; ++tryField){
      for(int t = 0; t < tickets.length; ++t){
        if (invalidTickets.contains(t)) continue;
        var ticket = tickets[t]; 
        {int aField = 0; for(var range : ranges){
          if (!inRange(range,ticket[tryField])){
            compat[tryField].remove(aField); 
          }
          ++aField;
        }}
      }
    }
    return compat;
  }
  static long[][] parseTickets(List<String>[]in){
    in[2].add(in[1].get(1));
    var stickets = in[2].subList(1,in[2].size());
    int fieldCnt = stickets.get(0).split(",").length;
    long[][] tickets = new long[stickets.size()][fieldCnt];
    {int i = 0; for(var ticket : stickets){
	{int j = 0; for(var v : ticket.split(",")){
          tickets[i][j] = Long.parseLong(v);
	  ++j;
	}}
        ++i;	
    }}
    return tickets;
  }
  static Pattern pat = Pattern.compile("[^:]+: ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)");  
  static long[][][] ranges(List<String>in){
    var ranges = new long[in.size()][][];
    {int i = 0; for(var s : in){
      var mtchr = pat.matcher(s);
      mtchr.find();
      ranges[i] = new long[2][];
      ranges[i][0] = new long[]{Long.parseLong(mtchr.group(1)), Long.parseLong(mtchr.group(2))};
      ranges[i][1] = new long[]{Long.parseLong(mtchr.group(3)), Long.parseLong(mtchr.group(4))};
      ++i;
    }}
    return ranges;
  }
  static Long part1(List<String>[]in,Set<Integer>invalids){
    var ranges = ranges(in[0]);
    var ans = new ArrayList<Long>();
    long errorRate = 0L;
    {int i = 0; for(var s : in[2].subList(1,in[2].size())){
      for(var v : s.split(",")){
        long val = Long.parseLong(v);
        boolean ok = false;
        for(var rr : ranges){
            if (inRange(rr,val)){
	      ok = true;
	      break;
            }
        }
        if(!ok) {
          errorRate += val;
	  invalids.add(i);
        }
      }
      ++i;
    }}
    return errorRate;
  }
  static boolean inRange(long[][]range, long val){
    boolean inRange = false;
    for(var r : range){
      if (r[0] <= val && val <= r[1]){
        inRange = true;
        break;
      }
    }
    return inRange;
  }
}
