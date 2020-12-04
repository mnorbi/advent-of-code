import java.util.*;
import java.io.*;
public class Day4{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day4.class.getResourceAsStream(args[0])))){
      var list = new ArrayList<String>();
      int part1Valid = 0, cnt = 0, part2Valid = 0;
      for(var s = br.readLine();; s = br.readLine()){
 	if (s == null || "".equals(s)){
          var map = parse(list);
	  int valid = part1(map, List.of("byr","iyr","eyr","hgt","hcl","ecl","pid"/*,"cid"*/));
	  part1Valid += valid;
	  part2Valid += valid*part2(map);
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
      switch(e.getKey()){
	case "byr":valid*= byr(e.getValue()); break; 
	case "iyr":valid*= iyr(e.getValue()); break; 
	case "eyr":valid*= eyr(e.getValue()); break; 
	case "hgt":valid*= hgt(e.getValue()); break; 
	case "hcl":valid*= hcl(e.getValue()); break; 
	case "ecl":valid*= ecl(e.getValue()); break; 
	case "pid":valid*= pid(e.getValue()); break; 
        default:
      }
    }
    return valid;
  }
  static int pid(String s){
    return s.matches("[0-9]{9}") ? 1 : 0;
  }
  static int ecl(String s){
    return s.matches("amb|blu|brn|gry|grn|hzl|oth") ? 1 : 0;
  }
  static int hcl(String s){
    return s.matches("#[0-9a-f]{6}") ? 1 : 0;
  }
  static int hgt(String s){
    if (s.endsWith("in")){
      var v = parse(s.substring(0,s.length()-2));
      return v != null && 59 <= v && v <= 76 ? 1 : 0;
    } else if (s.endsWith("cm")){
      var v = parse(s.substring(0,s.length()-2));
      return v != null && 150 <= v && v <= 193 ? 1 : 0;
    } else return 0;
  }
  static int byr(String s){
     var v =  parse(s);
     return s.length() == 4 && v != null && 1920 <= v && v <= 2002 ? 1 : 0;
  }
  static int iyr(String s){
     var v = parse(s);
     return s.length() == 4 && v != null && 2010 <= v && v <= 2020 ? 1 : 0;
  }
  static int eyr(String s){
     var v = parse(s);
     return s.length() == 4 && v != null && 2020 <= v && v <= 2030 ? 1 : 0;
  }
  static Long parse(String s){
    try{
	return Long.valueOf(s);
    }catch(Exception e){ 
      return null;
    }
  }
  private static int part1(Map<String,String>map, List<String>ids){
    for(var id : ids){
	if (!map.containsKey(id)) return 0;
    }
    return 1;
  }
  private static Map<String,String> parse(List<String>kv){
    var map = new HashMap<String,String>();
    for(var s : kv){
	for(var token : s.split("\\s+")){
	  var arr = token.split("\\:");
	  map.put(arr[0],arr[1]);
	}
    }
    return map;
  }
}
