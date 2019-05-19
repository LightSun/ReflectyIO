package com.heaven7.java.yaml;

import org.junit.Assert;
import org.junit.Test;

public class YamlLineTest {

   @Test
   public void test1() {
      String[] strs = CONTENT.split("\n");
      YamlLine line = YamlLine.parse(strs[0]);
      Assert.assertEquals(line.type, YamlLine.TYPE_PAIR);
      Assert.assertEquals(line.name, "age");
      Assert.assertEquals(line.value, "28");

      line = YamlLine.parse(strs[1]);
      Assert.assertEquals(line.type, YamlLine.TYPE_PAIR);
      Assert.assertEquals(line.name, "name");
      Assert.assertEquals(line.value, "heaven7");

      line = YamlLine.parse(strs[2]);
      Assert.assertEquals(line.type, YamlLine.TYPE_NAME);
      Assert.assertEquals(line.name, "list");
      Assert.assertEquals(line.value, null);

      line = YamlLine.parse(strs[3]);
      Assert.assertEquals(line.type, YamlLine.TYPE_ARRAY);
      Assert.assertEquals(line.name, null);
      Assert.assertEquals(line.value, "h123");

      line = YamlLine.parse(strs[4]);
      Assert.assertEquals(line.type, YamlLine.TYPE_ARRAY);
      Assert.assertEquals(line.name, null);
      Assert.assertEquals(line.value, "h456");

      line = YamlLine.parse(strs[5]);
      Assert.assertEquals(line.type, YamlLine.TYPE_ARRAY);
      Assert.assertEquals(line.name, null);
      Assert.assertEquals(line.value, "h789");

      line = YamlLine.parse(strs[11]);
      Assert.assertEquals(line.type, YamlLine.TYPE_ARRAY_EMPTY);
      Assert.assertEquals(line.name, null);
      Assert.assertEquals(line.value, null);

      line = YamlLine.parse("#sdjfsjfsjfdjs");
      Assert.assertTrue(line == null);
   }

   static final String CONTENT = "  age: 28\n" +
            "  name: heaven7\n" +
            "  list: \n" +
            "    - h123\n" +
            "    - h456\n" +
            "    - h789\n" +
            "  map: \n" +
            "    h1: 1\n" +
            "    h2: 2\n" +
            "    h3: 3\n" +
            "  listMap: \n" +
            "      - \n" +
            "        h1: 1\n" +
            "        h2: 2\n" +
            "        h3: 3\n" +
            "      - \n" +
            "        h1: 1\n" +
            "        h2: 2\n" +
            "        h3: 3\n" +
            "  mapList: \n" +
            "    h1: \n" +
            "      - 1\n" +
            "      - 2\n" +
            "      - 3\n" +
            "    h2: \n" +
            "      - 4\n" +
            "      - 5\n" +
            "      - 6\n" +
            "  infoList: \n" +
            "      - \n" +
            "        addr: addr1\n" +
            "        phone: 12345\n" +
            "      - \n" +
            "        addr: addr1\n" +
            "        phone: 12345\n" +
            "  infoMap: \n" +
            "    info1: \n" +
            "      addr: addr1\n" +
            "      phone: 12345\n" +
            "    info2: \n" +
            "      addr: addr1\n" +
            "      phone: 12345\n" +
            "  info: \n" +
            "    addr: addr1\n" +
            "    phone: 12345\n";
}
