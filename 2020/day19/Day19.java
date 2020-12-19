import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
public class Day19{
  public static void main(String[]args) throws Exception {
    var rules = new ArrayList<String>(); var text = new ArrayList<String>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day19.class.getResourceAsStream(args[0])))){
	var list = rules;
	for(var s = br.readLine();s != null; s = br.readLine()){
	  if ("".equals(s)){ list = text; continue; }
	  list.add(s);
	}
    }
    var ruleMap = toMap(rules);
    var ruleStrs = new HashMap<String,Set<String>>();
    var allStrs = generate("0",ruleMap,new StringBuilder(),ruleStrs);
    System.out.println(part1(allStrs,text));
    System.out.println(part2(allStrs,ruleStrs,text));
  }
  static long part2(Set<String>allStrs,Map<String,Set<String>>ruleStrs,List<String>text){
	long ans = 0;
  	for(var t : text) ans += allStrs.contains(t) || checkChangedRule8And11(ruleStrs,t)?1:0;
	return ans;
  }
  static boolean checkChangedRule8And11(Map<String,Set<String>>ruleStrs,String t){
	int n = t.length();
        var strs42 = ruleStrs.get("42");
	var dp42 = new int[n+1];
	dp42[0] = 1;
	for(int i = 0; i < n; ++i) if (dp42[i] != 0) for(var s : strs42){
		int m = s.length();
		if (i+m<=n && t.substring(i,i+m).equals(s))dp42[i+m] = Math.max(dp42[i+m],dp42[i]+1);
	}
	var strs31 = ruleStrs.get("31");
	var dp31 = new int[n+1];
	dp31[n] = 1;
	for(int i = n; i >= 0; --i) if (dp31[i] != 0) for(var s: strs31){
		int m = s.length();
		if (i-m >= 0 && t.substring(i-m,i).equals(s))dp31[i-m] = dp31[i-m] == 0 ? dp31[i]+1 : Math.min(dp31[i-m],dp31[i]+1);
	}
	for(int i = 1; i < n; ++i) if(dp42[i] > 1 && dp31[i] > 0 && dp42[i]-1>=dp31[i]){
		return true;
	}
	return false;
  }
  static long part1(Set<String>allStrs,List<String>text){
	long ans = 0;
	for(var t : text) ans += allStrs.contains(t)?1:0;
	return ans;
  }
  static Set<String> generate(String ruleId,Map<String,String>rules,StringBuilder sb,Map<String,Set<String>>memo){
    if(memo.containsKey(ruleId)) return memo.get(ruleId);
    var rule = rules.get(ruleId.trim()).trim();
    Set<String> res = new HashSet<String>();
    if (rule.indexOf("|")>-1 || rule.indexOf(" ")>-1) {
      for(var s : rule.split("\\|")){
	  var split = s.trim().split(" ");
          var left = generate(split[0],rules,sb,memo);
          var right = split.length == 1 ? Set.of(""):generate(split[1],rules,sb,memo);
          for(var l : left){
            for(var r : right){
              res.add(l+r);
            }
          }
      }
    } else if (rule.indexOf("\"")>-1){
      res.add(sb.toString()+rule.substring(1,2));
    } else {
      res = generate(rule,rules,sb,memo);
    }
    memo.put(ruleId,res);
    return res;
  }
  static Map<String,String> toMap(List<String> list){
    var map = new HashMap<String,String>();
    for(var s : list){
      map.put(s.split(":")[0],s.split(":")[1].trim());
    }
    return map;
  }
}
