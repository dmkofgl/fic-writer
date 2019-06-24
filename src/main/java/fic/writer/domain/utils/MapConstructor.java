package fic.writer.domain.utils;

import java.util.HashMap;
import java.util.Map;

public class MapConstructor<K, V> {
    private Map<K, V> map = new HashMap<>();

    public static <K, V> MapConstructor<K, V> getNew() {
        return new MapConstructor<>();
    }

    public MapConstructor<K, V> put(K k, V v) {
        map.put(k, v);
        return this;
    }

    public Map<K, V> getMap() {
        return map;
    }
}
