package club.lzlbog.calculator;

import java.util.HashMap;
import java.util.LinkedList;

public class ExpressionCache extends LinkedList<String> {
    private int capacity;
    private HashMap<String, String> result;

    ExpressionCache(int capacity) {
        this.capacity = capacity;
        this.result = new HashMap<>();
    }

    public String getResult(String exp) {
        if (result.containsKey(exp)) {
            return result.get(exp);
        }
        return "";
    }

    public void putResult(String exp, String res) {
        result.put(exp, res);
    }

    @Override
    public void addFirst(String s) {
        if (size() >= capacity) {
            String last = removeLast();
            result.remove(last);
        }
        super.addFirst(s);
    }

    @Override
    public void clear() {
        super.clear();
        result.clear();
    }
}
