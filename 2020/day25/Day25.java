import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day25{

        public static void main(String[] args) throws Exception {
		var card = ""; var door = "";
                try (BufferedReader br = new BufferedReader(new InputStreamReader(Day25.class.getResourceAsStream(args[0])))) {
			door = (br.readLine());
			card = (br.readLine());	
                }
		long dpub = Long.parseLong(door), cpub = Long.parseLong(card);
		long dl = 1;
		for(long d = 1;d != dpub;++dl){
			d = transform(7,d,1);
			if (d == dpub) break;
		}
		System.out.println(transform(cpub,1,dl));
        }
	static long transform(long base, long acc,long loop){
		while(loop-->0){
			acc = (base*acc)%20201227;
		}
		return acc;
	}
}
