import java.util.*;
import java.io.*;
import java.math.*;
import java.util.regex.*;
import java.util.stream.*;

public class Day23 {

    public static void main(String[] args) throws Exception {
        var in = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Day23.class.getResourceAsStream(args[0])))) {
            for (var s = br.readLine(); s != null; s = br.readLine()) {
                in = s;
            }
        }
        System.out.println(part1(in, Integer.valueOf(args[1])));
        System.out.println(part2(in, Integer.valueOf(args[2])));
    }

    static String part1(String in, int rounds) {
        var cups = new ArrayList<>(Arrays.asList(in.chars().map(c -> c - '0').boxed().toArray(Integer[]::new)));
        var cur = run(cups, rounds);
        var sb = new StringBuilder();
        cur = cur.nxt;
        for (; cur.val != 1; cur = cur.nxt) {
            sb.append("" + cur.val);
        }
        return sb.toString();
    }

    static long part2(String in, int rounds) {
        var cups = new ArrayList<>(Arrays.asList(in.chars().map(c -> c - '0').boxed().toArray(Integer[]::new)));
        cups.addAll(IntStream.range(Collections.max(cups) + 1, 1_000_000 + 1).boxed().collect(Collectors.toList()));
        var cur = run(cups, rounds);
        long a = cur.nxt.val, b = cur.nxt.nxt.val;
        return a * b;
    }

    static Node run(List<Integer> cups, int rounds) {
        var head = new Node(cups.get(0));
        var idx = new HashMap<Integer, Node>();
        idx.put(head.val, head);
        for (int i = 1; i < cups.size(); ++i) {
            var node = new Node(cups.get(i));
            head.pre.add(node);
            idx.put(node.val, node);
        }
        var cupIds = new TreeSet<>(cups);
        var cur = head;
        for (int cnt = rounds; cnt > 0; --cnt) {
            var picks = cur.nxt.del(3);
            var des = cupIds.lower(cur.val);
            while (des == null || picks.vals().contains(des)) {
                des = des == null ? cupIds.last() : cupIds.lower(des);
            }
            var ins = idx.get(des);
            ins.add(picks);
            cur = cur.nxt;
        }
        cur = head;
        while (cur.val != 1) {
            cur = cur.nxt;
        }
        return cur;
    }

    static class Node {

        Node pre = this, nxt = this;
        int val;

        Node(int v) {
            val = v;
        }

        void add(Node i) {
            i.pre.nxt = nxt;
            nxt.pre = i.pre;
            nxt = i;
            i.pre = this;
        }

        Node del(int len) {
            var cur = this;
            while (--len > 0) {
                cur = cur.nxt;
            }
            pre.nxt = cur.nxt;
            cur.nxt.pre = pre;
            pre = cur;
            cur.nxt = this;
            return this;
        }

        Set<Integer> vals() {
            var vals = new LinkedHashSet<Integer>();
            for (var i = this; ; ) {
                vals.add(i.val);
                i = i.nxt;
                if (i == this) {
                    break;
                }
            }
            return vals;
        }
    }
}

