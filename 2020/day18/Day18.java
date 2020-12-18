import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
public class Day18{
  public static void main(String[]args) throws Exception {
    var list = new ArrayList<String>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day18.class.getResourceAsStream(args[0])))){
	for(var s = br.readLine();s != null; s = br.readLine()){
	  list.add(s);
	}
    }
    System.out.println(part1(list));
    System.out.println(part2(list));
  }
    static long part1(List<String>list){
      long ans = 0L;
      var prio = "1100"; var ops = "+*()";
      for(var s : list){
        long val = eval("("+s+")",ops, (opa,opb) -> {
          if (opa == '(' && opb == '(') return 0;
          return prio.charAt(ops.indexOf(opa)) - prio.charAt(ops.indexOf(opb)) < 0 ? -1 : 1;
        });
        ans += val;
        System.out.println(List.of(s,"=",val));
      }
      return ans;
    }
    static long part2(List<String>list){
      long ans = 0L;
      var prio = "2100"; var ops = "+*()";
      for(var s : list){
        long val = eval("("+s+")", ops, Comparator.comparingInt(opa -> prio.charAt(ops.indexOf(opa))));
        ans += val;
        System.out.println(List.of(s,"=",val));
      }
      return ans;
    }
    static long eval(String s,String ops,Comparator<Character> cmp){
      var opStack = new ArrayDeque<Character>();
      var valStack = new ArrayDeque<Long>();
      for(int i = 0; i < s.length(); ++i){
        char c = s.charAt(i);
        if (c == ' ') continue;
        int j = ops.indexOf(c);
        if (j < 0){
          long val = c-'0';
          while('0'<=(c=s.charAt(i+1))&&c<='9'){
            val *= 10L; val += c-'0';
          }
          valStack.push(val);
        } else if (c == '('){
          opStack.push(c);
        } else if (c == ')'){
          while(!opStack.isEmpty() && opStack.peek() != '('){
            eval(opStack,valStack);
          }
          opStack.pop();
        } else {
          while(!opStack.isEmpty() && cmp.compare(opStack.peek(), c) > 0){
            eval(opStack,valStack);
          }
          opStack.push(c);
        }
      }
      return valStack.pop();
    }
    static void eval(Deque<Character>opStack,Deque<Long>valStack){
      char op = opStack.pop();
      long v1 = valStack.pop(), v2 = valStack.pop();
      if (op == '*') valStack.push(v1*v2);
      else valStack.push(v1+v2);
    }
  }
