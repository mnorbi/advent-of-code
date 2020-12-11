import java.util.*;
import java.io.*;
public class Day11{
  public static void main(String[]args) throws Exception {
    var list = new ArrayList<String>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Day11.class.getResourceAsStream(args[0])))){
	for(var s = br.readLine();s!=null;s=br.readLine()){
	  list.add(s);
        }
    }
    System.out.println(part1(toArray(list)));
    System.out.println(part2(toArray(list)));
  }
  static char[][] toArray(List<String>list){
    var arr = new char[list.size()][list.get(0).length()];
    int i = 0;
    for(var s : list) {
      int j = 0;
      for(char c : s.toCharArray()){
        arr[i][j++] = c;
      }
      ++i;
    }
    return arr;
  } 
  static int part2(char[][]arr){
    int r = arr.length, c = arr[0].length;
    while(true){
      boolean change = false;
      var idxs = index(arr);
      //for(var idx : idxs) System.out.println(Arrays.toString(idx));
      var nextArr = new char[r][c];
      for(int i = 0; i < r; ++i){
        for(int j = 0; j < c; ++j){
          nextArr[i][j] = arr[i][j];
          if (arr[i][j] == '.') continue;
          int cnt = 0, dirs[][] = dirs(i,j,-1,c);
          for(int k = 0; k < 4; ++k){
            var lower = idxs.get(k)[dirs[k][0]].lower(dirs[k]);
            var higher = idxs.get(k)[dirs[k][0]].higher(dirs[k]);
            cnt += lower == null ? 0 : ((int[])lower)[2] != (int)'#' ? 0 : 1;  
            cnt += higher == null ? 0 : ((int[])higher)[2] != (int)'#' ? 0 : 1; 
          }
          if (cnt == 0 && arr[i][j] == 'L') { change = true; nextArr[i][j] = '#'; }
          else if (cnt >= 5 && arr[i][j] == '#') { change = true; nextArr[i][j] = 'L';}
        }
      }   
      arr = nextArr;
      print(arr);
      if (!change) break;
    }
    return count(arr);
  }
  static List<TreeSet[]> index(char[][]arr){
    int r = arr.length, c = arr[0].length;
    var idxs = new ArrayList<TreeSet[]>();
    for(int i = 1; i <= 4; ++i) {
      var set = new TreeSet[r+c];
      Arrays.asList(set).replaceAll(v->new TreeSet((a,b) -> ((int[])a)[1]-((int[])b)[1]));
      idxs.add(set);
    }
    for(int i = 0; i < arr.length; ++i){
      for(int j = 0; j < arr[0].length; ++j){
        if (arr[i][j] == '.') continue;
        var dirs = dirs(i,j,(int)arr[i][j],c);
	for(int k = 0; k < 4; ++k) idxs.get(k)[dirs[k][0]].add(dirs[k]);
      }
    }
    return idxs;
  }
  static int[][] dirs(int i, int j, int v, int c){
    return new int[][]{{i,j,v},{j,i,v},{c+i-j,j,v},{i+j,i,v}};
  }
  static int part1(char[][]arr){
    int r = arr.length, c = arr[0].length;
    loop: while(true){
      boolean change = false;
      var nextArr = new char[r][c];
      for(int i = 0; i < r;++i){
        for(int j = 0; j < c; ++j){
          nextArr[i][j] = arr[i][j];
          if (arr[i][j] == '.') continue;
          int cnt = 0;
	  for(int dr = -1; dr <= 1; ++dr){
            for(int dc = -1; dc <= 1; ++dc){
	      if (i+dr < 0 || i+dr >= r || j+dc < 0 || j+dc >= c || dc==0 && dr==0) continue;
              cnt += arr[i+dr][j+dc] == '#'?1:0;
            }
          } 
          if (cnt == 0 && arr[i][j] == 'L') { change = true; nextArr[i][j] = '#';}
          else if (cnt >= 4 && arr[i][j] == '#') { change = true; nextArr[i][j] = 'L';}
        }
      }
      arr = nextArr;
      if (!change) break;
    }
    return count(arr);
  }
  static int count(char[][]arr){
    int cnt = 0;
    for(var a : arr) for(var b : a) cnt += b=='#'?1:0;
    return cnt;    
  }
  static void print(char[][]arr){
    for(var a : arr) System.out.println(new String(a));
    System.out.println("---");
  }
}
