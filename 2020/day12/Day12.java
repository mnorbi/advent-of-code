import java.util.*;
import java.io.*;
public class Day12{
  public static void main(String[]args) throws Exception {
    var list = new ArrayList<String>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day12.class.getResourceAsStream(args[0])))){
	for(var s = br.readLine();s!=null;s=br.readLine()){
	  list.add(s);
        }
    }
    System.out.println(part1(list));
    System.out.println(part2(list));
  }
  static int[][] DIRS = {{1,0},{0,1},{-1,0},{0,-1}};
  static String DIRNAME = "ENWS";
  static int[][][] ROTS = {
	{{1,0},{0,1}},
	{{0,-1},{1,0}},
	{{-1,0},{0,-1}},
	{{0,1},{-1,0}}};
  static int part2(List<String>list){
    int[] wp = new int[]{10,1}, ship = new int[2];
    for(var s : list){
      var op = s.substring(0,1);
      int move = Integer.valueOf(s.substring(1));
      if ("LR".contains(op)){
        int rot = op.equals("L") ? move/90 : 4-move/90;
        wp = new int[]{
          ROTS[rot][0][0]*wp[0]+ROTS[rot][0][1]*wp[1],
          ROTS[rot][1][0]*wp[0]+ROTS[rot][1][1]*wp[1]
        };
      }
      int i = DIRNAME.indexOf(op);
      if (i > -1) { wp[0] += DIRS[i][0]*move; wp[1] += DIRS[i][1]*move; }
      if (op.equals("F")) { ship[0] += move*wp[0]; ship[1] += move*wp[1]; }
    }
    
    return Math.abs(ship[0])+Math.abs(ship[1]);
  }
  static int part1(List<String>list){
    int ship[] = new int[2], dir = 0;
    for(var s : list){
      var op = s.substring(0,1);
      int move = Integer.valueOf(s.substring(1));
      if ("LR".contains(op)){
        dir = op.equals("L") ? (dir+move/90)%4 : (4+dir-move/90)%4;
      }
      int i = DIRNAME.indexOf(op);
      i = i < 0 ? dir : i;
      if("NSEWF".contains(op)) {ship[0]+= move*DIRS[i][0]; ship[1]+= move*DIRS[i][1]; }
    }
    
    return Math.abs(ship[0])+Math.abs(ship[1]);
  }
}
