import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day24 {

        public static void main(String[] args) throws Exception {
                var list = new ArrayList<String>();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(Day24.class.getResourceAsStream(args[0])))) {
                        for (var s = br.readLine(); s != null; s = br.readLine()) {
                                list.add(s);
                        }
                }
                var f = init(list);
                System.out.println(part1(f));
                System.out.println(part2(f,100));
        }
        static List<String>DIRNAME = List.of("e","se","sw","w","nw","ne");
        static int[][] DIRS = {{-1,0},{0,-1},{1,-1},{1,0},{0,1},{-1,1}};
        static long part2(Set<List<Integer>> f,int runs){
                while(runs-->0){
                        Map<List<Integer>,Integer>[] bw = new Map[]{new HashMap<>(),new HashMap<>()};
                        for(var t: f){
                                for(var d : DIRS){
                                        var nei = List.of(t.get(0) + d[0], t.get(1) + d[1]);
                                        if (f.contains(nei)){
                                                bw[0].merge(t,1,Integer::sum);
                                        } else {
                                                bw[1].merge(nei,1,Integer::sum);
                                        }
                                }
                        }
                        f.clear();
                        for(var b : bw[0].entrySet()){
                                if(!(b.getValue() == 0 || b.getValue() > 2)){
                                        f.add(b.getKey());
                                }
                        }
                        for(var w : bw[1].entrySet()){
                                if(w.getValue()==2){
                                        f.add(w.getKey());
                                }
                        }
                }
                return f.size();
        }
        static long part1(Set<List<Integer>> f){
                return f.size();
        }
        static Set<List<Integer>> init(List<String> l){
                var f = new HashSet<List<Integer>>();
                for(var s : l){
                        int x = 0, y = 0;
                        for(int i = 0;i < s.length();){
                                int j = i+1;
                                var c = s.charAt(i);
                                if(c=='s' || c=='n'){
                                        ++j;
                                }
                                var dir = DIRS[DIRNAME.indexOf(s.substring(i,j))];
                                x += dir[0]; y+= dir[1];
                                i = j;
                        }
                        var key= List.of(x, y);
                        if (f.contains(key)) f.remove(key);
                        else f.add(key);
                }
                return f;
        }
}
