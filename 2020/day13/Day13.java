import java.util.*;
import java.io.*;
import java.math.*;
public class Day13{
  public static void main(String[]args) throws Exception {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day13.class.getResourceAsStream(args[0])))){
	int start = Integer.valueOf(br.readLine());
	var bus = br.readLine().split(",");
	System.out.println(part1(start,bus));
 	System.out.println(part2(bus));
    }
  }
  static BigInteger part2(String[]buses){
    var num = new ArrayList<BigInteger>(); var rem = new ArrayList<BigInteger>();
    var mul = BigInteger.ONE;
    for(int i = 0; i < buses.length; ++i){
	if ("x".equals(buses[i])) continue;
	num.add(new BigInteger(buses[i]));
	mul = mul.multiply(num.get(num.size()-1));
	rem.add(new BigInteger(""+i));
    }
    var ans = BigInteger.ZERO;
    for(int i = 0; i < num.size(); ++i){
      var part = mul.divide(num.get(i));
      var inv = part.modInverse(num.get(i));
      ans = ans.add(inv.multiply(part).multiply(num.get(i).subtract(rem.get(i))));
    }
    while(ans.subtract(mul).compareTo(BigInteger.ZERO) > 0){
      ans = ans.subtract(mul);
    }
    return ans;
  }
  static int part1(int start,String[]buses){
    int time = start, bus = -1;
    for(var b : buses){
      if ("x".equals(b)) continue;
      int i = Integer.valueOf(b);
      if ((i-start%i)%i < time){ time = (i-start%i)%i; bus = i; }
    }
    return time*bus;
  }
}
