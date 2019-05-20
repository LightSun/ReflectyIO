# ReflectyIO
这是一个序列化和反序列化数据的一个框架。支持任意数据格式的扩展(以插件的形式)。

## 特征
- 1, 支持对象序列化为任意格式数据。同样支持反序列化数据为对象。
- 2, 支持任意java数据类型。比如8大基本类型，string, collection集合系列。map集合系列。任意自定义对象系列。 
甚至不继承collection的集合，以及不实现map接口的map. 
- 3, 标准化调用接口。使用非常简单。
  - 比如写json.
  ```java
  new ReflectyIo().json()
                  .typeToken(tt)
                  .build()
                  .write(mWriter, obj);
  ``` 
  - 又比如读json.
  ```java
   Object obj = new ReflectyIo()
                  .json()
                  .typeToken(tt)
                  .build()
                  .read(new StringReader(mWriter.toString()));
  ```
   - xml, yaml 数据同理
- 4, 内置3种数据的序列化和反序列化。即json,yaml,xml
- 5, 支持自行扩展插件。核心方法如下:
```java
ReflectyPluginManager.getDefault().registerReflectyPlugin(int type, ReflectyPlugin plugin);
```

## 优缺点
- 优势：
  - 1, 统一的调用接口。 使用起来非常简单。只需要记住一个对象即可'ReflectyIo'.
  - 2, 扩展性很强，并且支持以插件的形式扩展你所需要的数据格式。
  
- 劣势：
  - 1, 关于json的序列化和解析，我是偷懒的。直接用的Gson库的JsonWriter和JsonReader.
  - 2, 内置xml支持的数据类型，其中泛型不能嵌套太深。而且最外层对象一定是自定义对象。不能是集合类型。
  
## 使用步骤
- 1, gradle配置(其中版本号可根据release版本修改).android平台将compile改为implementation即可。
```java
    //主库
    compile "com.heaven7.java.reflectyio:ReflectyIo:1.0.7"
    //可选依赖库。
    compile "com.heaven7.java.xml:Xml:1.0.0"   //xml 数据格式支持
    compile "com.heaven7.java.yaml:Yaml:1.0.1" //yaml 数据格式支持
    compile "com.heaven7.java.json:Json:1.0.0" //json 数据格式支持
```
- 2, 配置完成后即可快速开始。
  - 写数据
  ```java
  new ReflectyIo()
                .yaml()
                .typeToken(tt)
                .build()
                .write(mWriter, obj);
  ```
  - 读数据
  ```java
   Object obj = new ReflectyIo()
                .yaml()
                .typeToken(tt)
                .build()
                .read(new StringReader(mWriter.toString()));
  ```
## 完整的demo
- 1, 定义数据结构
```java
@XmlElement("Google")
public class Person {

    private int age;
    private String name;
    private List<String> list;
    private Map<String, Integer> map;
    @XmlElement("list_map")
    private List<Map<String, Integer>> listMap; //用注解，映射child element name.
    private Map<String, List<Integer>> mapList;
    private Map<String, List<Info>> mapListInfo;

    private  List<Info> infoList;
    private  Map<String, Info> infoMap;
    private  Info info;

    @XmlBody
    private String text;
    //....get 和set, equals,hashCode方法省略
}

public class Info {
    private String addr;
    private String phone;
    //....get 和set, equals,hashCode方法省略
}    
```
- 2, 测试类
```java
public class PluginTest {

    private final StringWriter mWriter = new StringWriter();

    private void clean(){
        StringBuffer buffer = mWriter.getBuffer();
        buffer.delete(0, buffer.length());
    }

    @Test
    public void testObject() throws Exception{
        Person p = new Person();
        p.setAge(28);
        p.setName("heaven7");
        p.setList(createList());
        p.setMap(createMap());
        p.setMapList(createMapList());
        p.setListMap(createListMap());
        p.setInfo(createInfo());
        p.setInfoList(createInfoList());
        p.setInfoMap(createInfoMap());
        p.setText("fgkfdgfdlg8df89g8fd9g89fd99999999999gklal;l;d;lasd7as7d5asd5as56ddddddas56ddddd5s6ad");

        p.setMapListInfo(createMapListInfo());

        testJson(new TypeToken<Person>(){}, p);
        testYaml(new TypeToken<Person>(){}, p);
        testXml(new TypeToken<Person>(){}, p);
    }

    private void testXml(TypeToken<?> tt, Object raw) throws IOException {
        new ReflectyIo()
                .xml()
                .typeToken(tt)
                .build()
                .write(mWriter, raw);
        //System.out.println(mWriter.toString());

        Object obj = new ReflectyIo()
                .xml()
                .typeToken(tt)
                .build()
                .read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }
    private void testJson(TypeToken<?> tt, Object raw) throws IOException {
        new ReflectyIo()
                .json()
                .typeToken(tt)
                .build()
                .write(mWriter, raw);
        //System.out.println(mWriter.toString());

        Object obj = new ReflectyIo()
                .json()
                .typeToken(tt)
                .build()
                .read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }
    private void testYaml(TypeToken<?> tt, Object raw) throws IOException {
        new ReflectyIo()
                .yaml()
                .typeToken(tt)
                .build()
                .write(mWriter, raw);
        //System.out.println(mWriter.toString());

        Object obj = new ReflectyIo()
                .yaml()
                .typeToken(tt)
                .build()
                .read(new StringReader(mWriter.toString()));
        clean();

        Assert.assertEquals(obj, raw);
    }

    ... 此处省略各种对象的创建。。。
}

```
- 3, 更多详细[点击此处](https://github.com/LightSun/ReflectyIO/blob/master/ReflectyIO/src/test/java/com/heaven7/java/reflectyio/PluginTest.java)
  
## 混淆
```java
-keepnames class * implements com.heaven7.java.reflectyio.plugin.ReflectyPlugin
```

## 感谢
- [Google/Gson](https://github.com/google/gson). 设计思想来源于Gson而且json序列化和反序列化用的gson.
- [libgdx/libgdx](https://github.com/libgdx/libgdx) 非常优秀的开源游戏引擎，关于xml的序列化和反序列化来源于此。

  
 ## License

    Copyright 2019  
                    heaven7(donshine723@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
