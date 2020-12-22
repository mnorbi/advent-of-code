import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
import java.util.stream.*;
public class Day22{
  public static void main(String[]args) throws Exception {
	var p0 = new ArrayDeque<Long>(); var p1 = new ArrayDeque<Long>();
	try(BufferedReader br = new BufferedReader(new InputStreamReader(Day22.class.getResourceAsStream(args[0])))){
	    var p = p0;
    	    for(var s = br.readLine();s != null; s = br.readLine()){
		if (s.contains("Player 1")){
			p = p0;
			continue;
		} else if (s.contains("Player 2")){
			p = p1;
			continue;
		}
	        if ("".equals(s)) continue;
	        p.addLast(Long.parseLong(s));
    	    }
    	}
	System.out.println(part1(new ArrayDeque<>(p0),new ArrayDeque<>(p1)));
	System.out.println(part2(p0,p1));
  }
  static long part2(Deque<Long>p0,Deque<Long>p1){
	game(p0,p1, new HashSet<>());
	return p0.isEmpty() ? score(p1) : score(p0);
  }
  static long game(Deque<Long>p0,Deque<Long>p1,Set<String>seen){
	var key =List.of(p0,p1).toString();
	if (seen.contains(key)) return 0;
	if (p0.isEmpty() || p1.isEmpty()) seen.add(key);
	while(!(p0.isEmpty() || p1.isEmpty())){
		key = List.of(p0,p1).toString();
		if (!seen.contains(key)){
			seen.add(key);
			win(p0,p1,seen);
		} else return 0;
	}
	return p0.isEmpty() ? 1 : 0;
  }
  static long win(Deque<Long>p0,Deque<Long>p1,Set<String>seen){
	long ans = p0.isEmpty() ? 1 : p1.isEmpty() ? 0 : -1;
	if (ans == -1){
		if (p0.getFirst() > p0.size()-1	|| p1.getFirst() > p1.size()-1){
			ans = p0.getFirst() > p1.getFirst() ? 0 : 1;
		} else {
			var c1 = new ArrayDeque<>(p0);
			var c2 = new ArrayDeque<>(p1);
			var a = c1.removeFirst();
			while(c1.size() > a) c1.removeLast();
			var b = c2.removeFirst();
			while(c2.size() > b) c2.removeLast();
			ans = game(c1,c2,new HashSet<String>());
		}
	}
	var a =p0.removeFirst();
	var b =p1.removeFirst();
	if (ans == 0){
		p0.addLast(a);
		p0.addLast(b);
	} else {
		p1.addLast(b);
		p1.addLast(a);
	}
	return ans;
  }
  static long part1(Deque<Long>p0,Deque<Long>p1){
	while(!(p0.isEmpty() || p1.isEmpty())){
		var a = p0.removeFirst();
		var b = p1.removeFirst();
		if (a > b){ p0.addLast(a); p0.addLast(b);}
		else { p1.addLast(b); p1.addLast(a); }
	}
	return p0.isEmpty() ? score(p1) : score(p0);
  }
  static long score(Deque<Long>p){
	long ans = 0L;
	for(int n = p.size(), i = 1; i <= n;++i){
		ans += i*p.removeLast();
	}
	return ans;
  }
}
