

## プロジェクトの雛形の作成

Apache Maven の通常の利用手順に従って、以下のようにしてプロジェクトの雛形を作る。

```
$ mvn archetype:generate -DgroupId=net.example -DartifactId=your-project -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```


生成される `pom.xml` ファイル中に以下のように `maven-surfire-plugin`が指定される。POJO-bdd は`maven-surefire-plugin`の[POJO test](https://maven.apache.org/surefire/maven-surefire-plugin/examples/pojo-test.html)の機能を使うので、pom.xml で必要な指定はこれだけである。(JUnit などには依存しない)


```xml
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
```


## テストの実行方法

Maven を用いてテストを実行するには通常通り`mvn test`を実行する。

```
mvn test
```

これを実行すると、結果のレポートが標準出力に表示される。

結果のレポートは一般に長くなるので、指定したディレクトリの中にセーブすると便利である。

これを行うには`pojobdd.basedir`システムプロパティにディレクトリ名を指定する。

```
mvn test -Dpojobdd.basedir=src/site/markdown/pojobdd
```

レポートはマークダウン形式で出力されるように書いておき、
`src/site/markdown/pojobdd`ディレクトリに一連のレポートをセーブすると`mvn site:site`コマンドによりレポート内容を site の一部として利用することが出来る。



## BDD 文書およびテストコードの作成

`maven-surefire-plugin`の[POJO test](https://maven.apache.org/surefire/maven-surefire-plugin/examples/pojo-test.html)では

> A test class should be named `**/*Test` and should contain `test*` methods which will each be executed by Surefire.

となっている。

例えば`AppTest.java`というファイルを 1 個だけ作り、その中に`testAll`というメソッドを１個だけ作っておけば surfire が`mvn test`時にこれを呼び出す。

```
$ tree src/test
src/test
|-- java
|   `-- net
|       `-- laddercode
|           `-- pojobdd
|               |-- AppTest.java
|               |-- BddUtilSpec.java
|               `-- MdToArraySpec.java
`-- resources
    `-- testdoc.md
```


AppTest.java

```java
package net.laddercode.pojobdd;

public class AppTest {

    public void testAll() {

        assert BddUtilSpec.exec();
        assert MdToArraySpec.exec();
    }

}
```

あとは`testAll`メソッドが順次テストコードを呼び出せばよいだけである。

`assert`により`surefire`プラグインがエラーの有無を検出する。



それぞれの Feature (=Story)について以下のようなクラスを書く。

このクラスは、以下のメソッドからなる。

1. Example を順次呼び出すメソッド`exec`
    - このメソッドの中で出力するファイル名を決める。
2. 個々の example を実行するメソッド`*Spec` (surfire  POJO Test に直接実行されないよう名前に注意。)
    - markdown に出力する説明(description)を
    - 


```java

public class BddUtilSpec {


    public static boolean exec() {
        // PrintStream out = new PrintStream(System.out);
        try (PrintStream out = BddUtil.newPrintStream("BddUtil_spec.md")) {
            // Checks if all the tests are succeeded.
            List<Boolean> results = new ArrayList<Boolean>();
            results.add(allTrueSpec01(out));
            results.add(allTrueSpec02(out));
            // results.add(convertRecursivelySpec(out));

            out.flush();
            return BddUtil.allTrue(results);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static boolean allTrueSpec01(PrintStream out) {

        // Description
        String[] description = {
            "",
            "## `allTrue(List<Boolean>)`",
            "",
            "- Given a Boolean list",
            "- When the size of the list >= 1",
            "- Then returns true if all the elements of given array are true, otherwise false",
            "",
            };

        Arrays.stream(description)
            .forEach(out::println);

        // Reality
        ArrayList<ArrayList<Boolean>> examples = new ArrayList<>();
        ArrayList<Boolean> row1 = new ArrayList<>();
        row1.add(true);
        examples.add(row1);
        ArrayList<Boolean> row2 = new ArrayList<>();
        row2.add(true);
        row2.add(true);
        examples.add(row2);
        ArrayList<Boolean> row3 = new ArrayList<>();
        row3.add(true);
        row3.add(false);
        examples.add(row3);


        StringJoiner answers = new StringJoiner("\n");
        for (int i=0; i<examples.size(); i++) {
            ArrayList<Boolean> row = examples.get(i);
            answers.add(String.format("%s => %s",
                                      row.toString(),
                                      BddUtil.allTrue(examples.get(i))));
        }
        String answer = answers.toString();


        // Expectations
        String[] expectations = {
            "[true] => true",
            "[true, true] => true",
            "[true, false] => false",
        };
        String expectation = String.join("\n",expectations);



        // Check the answer.
        boolean result = BddUtil.assertTrue(out, expectation, answer);
        assert result;
        return result;


        //return true;
    }

... (以下略)
```
