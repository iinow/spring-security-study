package io.security.basicsecurity;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class MapTest {

  @Test
  public void linkedMap() {
    var map = new LinkedHashMap<String, String>();
    map.put("B", "BB");
    map.put("A", "AA");

    var map2 =
        map.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Entry::getValue,
                    Entry::getKey,
                    (x, y) -> {
                      return y;
                    },
                    LinkedHashMap::new));

    System.out.println(map.toString());
    System.out.println(map2.toString());
  }
}
