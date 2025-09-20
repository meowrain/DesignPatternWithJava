package org.designpatter;

import org.junit.jupiter.api.Test;

import java.util.*;

public class MapHelper {
    @Test
    void test() {
        Map<String, List<String>> hashMap = new HashMap<>();
        hashMap.put("fruit",new ArrayList<String>(Arrays.asList("apple","orange","banana")));
        hashMap.computeIfAbsent("fruit",k -> new ArrayList<>()).add("apple");
        System.out.println(hashMap);
    }
}
