import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
import java.util.stream.*;
public class Day21{
  public static void main(String[]args) throws Exception {
	var list = new ArrayList<List<List<String>>>();
	try(BufferedReader br = new BufferedReader(new InputStreamReader(Day21.class.getResourceAsStream(args[0])))){
    	    for(var s = br.readLine();s != null; s = br.readLine()){
    	    	list.add(parse(s));
    	    }
    	}
	System.out.println(part12(list));	
  }
  static long part12(List<List<List<String>>> list){
	var a2i = new HashMap<String,List<List<String>>>();
	for(var pair : list){
		for(var allerg : pair.get(1)){
			a2i.computeIfAbsent(allerg,v->new ArrayList<>()).add(pair.get(0));
		}
	}
	for(var lists: a2i.values()){
		intersect(lists);	
	}
	var deq = new ArrayDeque<String>();
	var i2a = new HashMap<String,String>();
	for(var e: a2i.entrySet()){
		var ingrs = e.getValue().get(0);
		if (ingrs.size() == 1){
			deq.offer(ingrs.get(0));
			i2a.put(ingrs.get(0),e.getKey());
		}
	}
	while(!deq.isEmpty()){
		var item = deq.poll();
		for(var e : a2i.entrySet()){
			var ingrs = e.getValue().get(0);
			if (ingrs.size() > 1 && ingrs.contains(item)){
				e.getValue().set(0,new ArrayList<>(ingrs));
				ingrs = e.getValue().get(0);
				ingrs.remove(item);
				if (ingrs.size() == 1){
					deq.offer(ingrs.get(0));
					i2a.put(ingrs.get(0),e.getKey());
				}
			}
		}	
	}
	long ans = 0;
	for(var pair : list){
		for(var ingr : pair.get(0)){
			if (!i2a.containsKey(ingr)){
				++ans;
			}
		}
	}	

	var ingOrd = new TreeMap<String,String>();
	for(var e : i2a.entrySet()){
		ingOrd.put(e.getValue(),e.getKey());
	}
	System.out.println(ingOrd.values().toString().replaceAll(" ",""));
	return ans;
  }
  static void intersect(List<List<String>>lists){
	for(int n = lists.size();n > 1;--n){
		var a = new HashSet<>(lists.remove(n-1));
		var b = new HashSet<>(lists.remove(n-2));	
		a.retainAll(b);
		lists.add(new ArrayList<>(a));
	}
  }
  static List<List<String>> parse(String item){
	var tokens = item.split("\\(");
	tokens[1] = tokens[1].substring("contains ".length(),tokens[1].length()-1);
	var allerg = tokens[1].split(",");
	for(int i = 0; i < allerg.length;++i) allerg[i] = allerg[i].trim();
 	var ingrs = tokens[0].trim().split(" ");
	return List.of(List.of(ingrs),List.of(allerg));
  }
}
