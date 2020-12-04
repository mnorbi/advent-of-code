import java.util.*;
import java.io.*;
public class Day4{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day4.class.getResourceAsStream(args[0])))){
      var list = new ArrayList<String>();
      int part1Valid = 0, cnt = 0, part2Valid = 0;
      for(var s = br.readLine();; s = br.readLine()){
 	if (s == null || "".equals(s)){
          var keyValue = parse(list);
	  var expectedFields = List.of("byr","iyr","eyr","hgt","hcl","ecl","pid"/*,"cid"*/);
	  int valid = keyValue.keySet().containsAll(expectedFields) ? 1 : 0;
	  part1Valid += valid;
	  part2Valid += valid*part2(keyValue);
 	  ++cnt;
	  list.clear();
        }
        if (s==null) break;
        if (!"".equals(s))list.add(s);
      }
      System.out.println(List.of(cnt, part1Valid, part2Valid));
    }
  }
  private static int part2(Map<String,String>map){
    int valid = 1;
    for(var e : map.entrySet()){
      var s = e.getValue();
      switch(e.getKey()){
	case "byr":valid*= inRange(s,4,1920,2002); break; 
	case "iyr":valid*= inRange(s,4,2010,2020); break; 
	case "eyr":valid*= inRange(s,4,2020,2030); break; 
	case "hgt":valid*= 
    		s.endsWith("in") ? inRange(s.substring(0,s.length()-2),-1,59,76)
	        : s.endsWith("cm") ? inRange(s.substring(0,s.length()-2),-1,150,193) : 0;
		break; 
	case "hcl":valid*= s.matches("#[0-9a-f]{6}") ? 1 : 0; break; 
	case "ecl":valid*= s.matches("amb|blu|brn|gry|grn|hzl|oth") ? 1 : 0; break;
	case "pid":valid*= s.matches("[0-9]{9}") ? 1 : 0; break; 
        default:
      }
    }
    return valid;
  }
  static int inRange(String s, int length, int lo, int hi){
     var v = parse(s);
     return (length == -1 || s.length() == length) && v != null && lo <= v && v <= hi ? 1 : 0;
  }
  static Long parse(String s){
    try{
	return Long.valueOf(s);
    }catch(Exception e){ 
      return null;
    }
  }
  private static Map<String,String> parse(List<String>input){
    var keyValue = new HashMap<String,String>();
    for(var s : input){
	for(var token : s.split("\\s+")){
	  var arr = token.split("\\:");
	  keyValue.put(arr[0],arr[1]);
	}
    }
    return keyValue;
  }
}
