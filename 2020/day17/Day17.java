import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
public class Day17{
  public static void main(String[]args) throws Exception {
    var list = new ArrayList<String>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day17.class.getResourceAsStream(args[0])))){
	for(var s = br.readLine();s != null; s = br.readLine()){
	  list.add(s);
	}
    }
    var world = toWorld(list);
    System.out.println(part1(world,6));
    System.out.println(part2(world,6));
  }
  static Set<List<Integer>> toWorld(List<String>list){
    var world = new HashSet<List<Integer>>();
    for(int i = 0; i < list.size(); ++i){
      for(int j = 0; j < list.get(i).length(); ++j){
        if (list.get(i).charAt(j) == '#'){
		world.add(List.of(j,i,0,0));
	}
      }
    }
    return world;
  }
  static int part2(Set<List<Integer>> world,int runCount){
    return simulate(world,-1,1,6);
  }
  static int part1(Set<List<Integer>> world,int runCount){
    return simulate(world,0,0,6);
  }
  static int simulate(Set<List<Integer>> world,int wLo, int wHi,int runCount){
    for(int i = 0; i < runCount; ++i){
      var nextWorld = new HashSet<List<Integer>>();
      var candidates = new HashMap<List<Integer>,Integer>();
      for(var p : world){
        int setnei = -1;
        for(int x = -1; x <= 1; ++x){
	  for(int y = -1; y <= 1; ++y){
	    for(int z = -1; z <=1; ++z){
	      for(int w = wLo; w <= wHi; ++w){
		      var cand = List.of(p.get(0)+x,p.get(1)+y,p.get(2)+z,p.get(3)+w);
		      if(!world.contains(cand)){
			      candidates.merge(cand,1,Integer::sum);
			} else { ++setnei; }
              }
 	    }
  	  }
        }
        if (2<= setnei && setnei <= 3){
	  nextWorld.add(p);
        }
      }
      for(var cand : candidates.entrySet()) if (cand.getValue() == 3) nextWorld.add(cand.getKey());
      world = nextWorld;
    }
    return world.size();
  }
}
