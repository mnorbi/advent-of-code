import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day20 {

    static int[] DIRS = {0, 1, 0, -1, 0};

    public static void main(String[] args) throws Exception {
        main1(new String[]{"day20.in", "monster.in"});
    }

    public static void main1(String[] args) throws Exception {
        var tiles = parseTiles(args[0]);
        var monster = parseMonster(args[1]);
        var adj = adjMap(tiles);
        System.out.println(part1(adj));
        System.out.println(part2(adj, tiles, monster));
    }

    static long part1(Map<String, Set<String>> adj) {
        long ans = 1L;
        for (var e : adj.entrySet()) {
            if (e.getValue().size() == 2) {
                ans *= Long.parseLong(e.getKey());
            }
        }
        return ans;
    }

    static long part2(Map<String, Set<String>> adj, Map<String, char[][]> tiles, char[][] monster) {
        var tileIdLayout = reconstructTileIdLayout(adj);
        var layout = reconstructLayout(tileIdLayout, tiles);
        var maxMatch = new int[1];
        rotateTiles(Collections.singletonList(layout),
                () -> (maxMatch[0] = Math.max(maxMatch[0], monsterMatch(layout, monster))) == -1);
        int all = 0;
        for(var row : layout) for(var c : row) all += c == '#'?1:0;
        return all-maxMatch[0];
    }

    static int monsterMatch(char[][] layout, char[][] monster) {
        int n = layout.length, m = layout[0].length, p = monster.length, q = monster[0].length;
        var matches = new boolean[n][m];
        for (int r = 0; r + p <= n; ++r) {
            for (int c = 0; c + q <= m; ++c) {
                boolean match = true;
                search:
                for (int i = 0; i < p; ++i) {
                    for (int j = 0; j < q; ++j) {
                        if (monster[i][j] == ' ') continue;
                        if (layout[r + i][c + j] != '#') {
                            match = false;
                            break search;
                        }
                    }
                }
                if (match) {
                    for (int i = 0; i < p; ++i) {
                        for (int j = 0; j < q; ++j) {
                            matches[r + i][c + j] = monster[i][j] == '#';
                        }
                    }
                }
            }
        }
        int cnt = 0;
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < m; ++j){
                cnt += matches[i][j] && layout[i][j] == '#' ? 1 : 0;
            }
        }
        return cnt;
    }

    static char[][] reconstructLayout(Map<List<Integer>, String> tileIdLayout, Map<String, char[][]> tiles) {
        fixTileRotations(tileIdLayout, tiles);
        var layout = new ArrayList<List<char[][]>>();
        var more = true;
        y:
        for (int y = 0; more; ++y) {
            more = false;
            layout.add(new ArrayList<>());
            for (int x = 0; ; ++x) {
                var pos = List.of(x, y);
                if (!tileIdLayout.containsKey(pos)) {
                    continue y;
                }
                var tile = tiles.get(tileIdLayout.get(pos));
                layout.get(layout.size() - 1).add(cut(tile, 1, 1, tile[0].length - 1, tile.length - 1));
                more = true;
            }
        }
        layout.remove(layout.size() - 1);
        var tile = layout.get(0).get(0);
        int h = tile.length, w = tile[0].length;
        var res = new char[h * layout.size()][w * layout.get(0).size()];
        for (int r = 0; r < res.length; ++r) {
            for (int c = 0; c < res[0].length; ++c) {
                res[r][c] = layout.get(r / h).get(c / w)[r % h][c % w];
            }
        }
        return res;
    }

    static void fixTileRotations(Map<List<Integer>, String> tileIdMap, Map<String, char[][]> tiles) {
        var rotate = true;
        y:
        for (int y = 0; rotate; y += 1) {
            rotate = false;
            for (int x = 0; ; ++x) {
                var tilea = tiles.get(tileIdMap.get(List.of(x, y)));
                var tileb = tiles.get(tileIdMap.get(List.of(x, y + 1)));
                var tilec = tiles.get(tileIdMap.get(List.of(x + 1, y)));
                if (tileb == null) {
                    continue y;
                }
                if (!rotateTiles(tilec != null ? List.of(tilea, tileb, tilec) : List.of(tilea, tileb),
                        () ->
                                Arrays.equals(tilea[tilea.length - 1], tileb[0])
                                        && (tilec == null || Arrays
                                        .equals(vborder(tilea, tilea.length - 1), vborder(tilec, 0))))) {
                    throw new RuntimeException("Mismatch at " + List.of(x, y));
                }
                rotate = true;
            }
        }
    }

    static boolean rotateTiles(List<char[][]> tiles, Supplier<Boolean> callback) {
        int a = 2 * 4, m = tiles.size(), v[] = new int[m];
        v[m - 1] = 1;
        for (int i = m - 2; i >= 0; --i) {
            v[i] = v[i + 1] * a;
        }
        for (int i = 0, pre[] = new int[2 * m]; i < a * v[0]; ++i) {
            int[] act = new int[2 * m];
            for (int j = 0, k = i; j < m; ++j) {
                act[2 * j] = k / v[j] / 4;
                act[2 * j + 1] = k / v[j] % 4;
                vflip(tiles.get(j), pre[2 * j] != act[2 * j]);
                rotate(tiles.get(j), pre[2 * j + 1] != act[2 * j + 1]);
                k %= v[j];
            }
            if (callback.get()) {
                return true;
            }
            pre = act;
        }
        return false;
    }

    static Map<List<Integer>, String> reconstructTileIdLayout(Map<String, Set<String>> adj) {
        var adjCnt = adj.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
        var head = adjCnt.entrySet().stream().filter(e -> e.getValue() == 2).findFirst().get().getKey();
        var border = new ArrayDeque<String>();
        border.addLast(head);
        loop:
        for (String pre = null; ; ) {
            for (var nei : adj.get(border.getLast())) {
                if (pre != nei && (adjCnt.get(nei) == 2 || adjCnt.get(nei) == 3)) {
                    if (head == nei) {
                        break loop;
                    }
                    pre = border.getLast();
                    border.addLast(nei);
                    break;
                }
            }
        }
        var posMap = new HashMap<List<Integer>, String>();
        int r = 0, c = 0;
        for (; !border.isEmpty(); ++c) {
            var nextBorder = new ArrayDeque<String>();
            for (int d = 1; !border.isEmpty(); ++d) {
                posMap.put(List.of(c, r), border.getFirst());
                while (true) {
                    for (var nei : adj.get(border.removeFirst())) {
                        adjCnt.merge(nei, -1, Integer::sum);
                        if (adjCnt.get(nei) == 3) {
                            nextBorder.addLast(nei);
                        }
                    }
                    if (d < DIRS.length) {
                        r += DIRS[d - 1];
                        c += DIRS[d];
                    }
                    if (border.isEmpty() || adjCnt.get(border.getFirst()) <= 1) {
                        break;
                    }
                    if (posMap.put(List.of(c, r), border.getFirst()) != null) {
                        throw new RuntimeException("Duplicate at " + List.of(c, r));
                    }
                }
            }
            border = nextBorder;
        }
        return posMap;
    }

    static char[][] parseMonster(String file) throws IOException {
        var res = new ArrayList<char[]>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day20.class.getResourceAsStream(file)))) {
            for (var s = br.readLine(); s != null; s = br.readLine()) {
                res.add(s.toCharArray());
            }
        }
        return res.toArray(new char[0][0]);
    }

    static HashMap<String, char[][]> parseTiles(String file) throws IOException {
        var tiles = new HashMap<String, char[][]>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day20.class.getResourceAsStream(file)))) {
            char[][] arr = null;
            int i = 0;
            for (var s = br.readLine(); s != null; s = br.readLine()) {
                if ("".equals(s)) {
                    continue;
                }
                if (s.startsWith("Tile")) {
                    arr = new char[10][10];
                    var id = s.split(" ")[1];
                    id = id.substring(0, id.length() - 1);
                    tiles.put(id, arr);
                    i = 0;
                    continue;
                }
                arr[i++] = s.toCharArray();
            }
        }
        return tiles;
    }

    static Map<String, Set<String>> adjMap(Map<String, char[][]> tiles) {
        var idBorders = idBorders(tiles);
        var adj = new HashMap<String, Set<String>>();
        borderToIdIdx(idBorders).forEach((b, ids) -> {
            for (String id : ids) {
                adj.computeIfAbsent(id, i -> new HashSet<>()).addAll(ids);
                adj.get(id).remove(id);
            }
        });
        return adj;
    }

    static Map<String, List<String>> idBorders(Map<String, char[][]> tiles) {
        var bMap = new HashMap<String, List<String>>();
        tiles.forEach((k, arr) -> {
            bMap.put(k, new ArrayList<>());
            for (var b : List.of(arr[0], arr[9], vborder(arr, 0), vborder(arr, 9))) {
                bMap.get(k).add(new String(b));
                bMap.get(k).add(new String(vflip(Arrays.copyOf(b, b.length))));
            }
        });
        return bMap;
    }

    static Map<String, Set<String>> borderToIdIdx(Map<String, List<String>> bMap) {
        var res = new HashMap<String, Set<String>>();
        bMap.forEach((id, bs) -> {
            for (var b : bs) {
                res.computeIfAbsent(b, i -> new HashSet<>()).add(id);
            }
        });
        return res;
    }


    static char[][] cut(char[][] tile, int xa, int ya, int xb, int yb) {
        var res = new char[yb - ya][xb - xa];
        for (int r = ya; r < yb; ++r) {
            res[r - ya] = Arrays.copyOfRange(tile[r], xa, xb);
        }
        return res;
    }

    static void rotate(char[][] arr, boolean b) {
        if (!b) {
            return;
        }
        transpose(arr, b);
        vflip(arr, b);
    }

    static void transpose(char[][] arr, boolean b) {
        if (!b) {
            return;
        }
        for (int i = 0, n = arr.length; i < n; ++i) {
            for (int j = 0; i + j < n; ++j) {
                char tmp = arr[i][i + j];
                arr[i][i + j] = arr[i + j][i];
                arr[i + j][i] = tmp;
            }
        }
    }

    static void vflip(char[][] arr, boolean b) {
        if (!b) {
            return;
        }
        for (char[] chars : arr) {
            vflip(chars);
        }
    }

    static char[] vborder(char[][] arr, int c) {
        int n = arr.length;
        char[] res = new char[n];
        for (int r = 0, i = 0; r < n; ++r) {
            res[i++] = arr[r][c];
        }
        return res;
    }

    static char[] vflip(char[] arr) {
        for (int i = 0, n = arr.length; i < n - i - 1; ++i) {
            char tmp = arr[i];
            arr[i] = arr[n - i - 1];
            arr[n - i - 1] = tmp;
        }
        return arr;
    }
}
