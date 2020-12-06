import java.util.*;
import java.io.*;
public class Day6{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day6.class.getResourceAsStream(args[0])))){
      var groupVotesPart1 = new HashSet<Character>();
      var groupVotesPart2 = new HashMap<Character,Integer>();
      int countSumPart1 = 0, groupSize = 0, countSumPart2 = 0;
      for(var s = br.readLine();; s = br.readLine()){
	if (s == null || "".equals(s)){
	  countSumPart1 += groupVotesPart1.size();
	  for(var v : groupVotesPart2.values()){
	    countSumPart2 += v == groupSize ? 1 : 0;
	  }
	  groupVotesPart1.clear();
	  groupVotesPart2.clear();
	  groupSize = 0;
	  if (s == null) break;
	} else {
	  ++groupSize;
	  var userVotes = new HashSet<Character>();
	  for(char c : s.toCharArray()) { 
	    groupVotesPart1.add(c);
	    userVotes.add(c);
  	  }
	  for(var c : userVotes){
	    groupVotesPart2.merge(c,1,Integer::sum);
	  }
	}
      }
      System.out.println(List.of(countSumPart1, countSumPart2));
    }
  }
}
