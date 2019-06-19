package fic.writer.domain.utils;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class MapConstructorTest {

    @Test
    public void getNew() {
        Map<String, Object> map = MapConstructor.<String, Object>getNew()
                .put("success", true)
                .put("a", "true")
                .getMap();
        assertTrue(map.containsKey("success"));
        assertTrue(map.containsKey("a"));
    }
}