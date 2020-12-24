import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day24TypeAlias<LI extends List<Integer>, LLI extends List<LI>, LS extends List<String>, LLS extends List<List<String>>> {

        public static void main(String[] args) throws Exception {
                var list = new ArrayList<String>();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(Day24TypeAlias.class.getResourceAsStream(args[0])))) {
                        for (var s = br.readLine(); s != null; s = br.readLine()) {
                                list.add(s);
                        }
                }
                var sol = new Day24TypeAlias<>();
                var f = sol.init(list);
                System.out.println(sol.part1(f));
                System.out.println(sol.part2(f,100));
        }
        LS DIRNAME = (LS)List.of("e","se","sw","w","nw","ne");
        static int[][] DIRS = {{-1,0},{0,-1},{1,-1},{1,0},{0,1},{-1,1}};
        long part2(Set<LI> f,int runs){
                while(runs-->0){
                        var bs = new HashMap<LI,Integer>(); var ws = new HashMap<LI,Integer>();
                        for(var t: f){
                                for(var d : DIRS){
                                        var nei = (LI)List.of(t.get(0) + d[0], t.get(1) + d[1]);
                                        if (f.contains(nei)){
                                                bs.merge(t,1,Integer::sum);
                                        } else {
                                                ws.merge(nei,1,Integer::sum);
                                        }
                                }
                        }
                        f.clear();
                        for(var b : bs.entrySet()){
                                if(!(b.getValue() == 0 || b.getValue() > 2)){
                                        f.add(b.getKey());
                                }
                        }
                        for(var w : ws.entrySet()){
                                if(w.getValue()==2){
                                        f.add(w.getKey());
                                }
                        }
                }
                return f.size();
        }
        long part1(Set<LI> f){
                return f.size();
        }
        Set<LI> init(LS l){
                var f = new HashSet<LI>();
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
                        var key = (LI)List.of(x, y);
                        if (f.contains(key)) f.remove(key);
                        else f.add(key);
                }
                return f;
        }
}
