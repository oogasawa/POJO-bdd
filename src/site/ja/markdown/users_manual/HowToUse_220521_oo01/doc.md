
## インストール

現時点では Maven Central Repository には登録していないので、以下のようにしてローカルの環境にインストールする。

```
git clone https://github.com/oogasawa/POJO-bdd
mvn compile package
mvn install
```


## POJO-bdd を利用するプロジェクトの作成

Apache Maven の通常の利用手順に従って、以下のようにしてプロジェクトの雛形を作る。

```
$ mvn archetype:generate -DgroupId=net.example -DartifactId=your-project -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```


補足: POJO-bdd は`maven-surefire-plugin`の[POJO test](https://maven.apache.org/surefire/maven-surefire-plugin/examples/pojo-test.html)の機能を使うので以下を`pom.xml`に記述する必要がある。上記のように`maven-archetype-quickstart`などを使って雛形を生成した場合は最初から`pom.xml`にこの記述が書かれている。


```xml
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
```



作成された`pom.xml`に POJO-bdd の依存性を追加する。

```xml
    <dependency>
      <groupId>com.github.oogasawa</groupId>
      <artifactId>POJO-bdd</artifactId>
      <version>1.4.0</version>
      <scope>test</scope>
    </dependency>
```



## テストコードの作成 (1) Hello world example


`maven-surefire-plugin`の[POJO test](https://maven.apache.org/surefire/maven-surefire-plugin/examples/pojo-test.html)では

> A test class should be named `**/*Test` and should contain `test*` methods which will each be executed by Surefire.

となっている。

例えば`AppTest.java`というファイルを 1 個だけ作り、その中に`testAll`というメソッドを１個だけ作っておけば `maven-surfire-plugin` が`mvn test`時にこれを呼び出す。
あとは`testAll`メソッドが順次テストコードを呼び出せばよい。
`assert`により`maven-surefire-plugin`がエラーの有無を検出する。


```
$ tree src/test
src/test
|-- java
|   `-- com
|       `-- example
|           `-- pojobdd
|               `-- StoryTest.java
`-- resources
```


AppTest.java

```java
package com.example.pojobdd;

import com.github.oogasawa.pojobdd.BddUtil;

public class StoryTest {

    public void testAll() {

        assert execAcceptanceCriterion01();
        // assert execAcceptanceCriterion02();
        // ...

    }
    
    public static boolean execAcceptanceCriterion01() {
    
        try (PrintStream out = BddUtil.newPrintStream("Hello_spec.md")) {

            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(description());
            results.add(example01(out));
            // results.add(example02(out));
            // ...
            
            out.flush();
            return BddUtil.allTrue(results);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    
    public static boolean description(PrintStream out) {
        String description = """
        
        ## Acceptance Criterion
        
        Hello world.
        
        """;
        
        out.println(description);
        return true;
    }


    public static boolean example01(PrintStream out) {
        String description = """
        
        ### Example 1
        
        detail of example 1
        
        """;
        
        out.println(description);
        return true;
    }


    // ...


}
```




## テストの実行方法


### テスト結果の標準出力への出力

Maven を用いてテストを実行するには通常通り`mvn test`を実行する。

```
mvn test
```

これを実行すると、結果のレポートが標準出力に表示される。


### テスト結果のファイルへの出力

結果のレポートは一般に長くなるので、指定したディレクトリの中にセーブすると便利である。

これを行うには`pojobdd.basedir`システムプロパティにディレクトリ名を指定する。

```
mvn test -Dpojobdd.basedir=src/site/markdown/pojobdd
```

レポートはマークダウン形式で出力されるように書いておき、
`src/site/markdown/pojobdd`ディレクトリに一連のレポートをセーブすると`mvn site:site`コマンドによりレポート内容を site の一部として利用することが出来る。



## テストコードの作成 (2) 実際の例

テストコードの実例は以下の URL を参照してください。

https://github.com/oogasawa/POJO-bdd/tree/main/src/test/java/com/github/oogasawa/pojobdd


## 各メソッドの紹介 

- Specification by Example
    - [BddUtil.allTrue](https://github.com/oogasawa/POJO-bdd/blob/main/src/site/markdown/pojobdd/AllTrue_spec.md)
    - BddUtil.assertTrue
    - [BddUtil.readSnippet](https://github.com/oogasawa/POJO-bdd/blob/main/src/site/markdown/pojobdd/ReadSnippet_spec.md)

