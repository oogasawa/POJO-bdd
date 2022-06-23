
## BDD とは

BDD は以下の４つのステップからなる。

1. Discussing
    - 具体例を挙げることで仕様を明確化する。(Specification by Example: SBE)
2. documenting
    - 話し合った内容を文書にまとめる
3. instrumenting
    - ドキュメントを実行可能にする。
4. Coding
    - 実際のプログラムを書く。

BDD はユースケースの分析の下流にある工程であると言える。

### Discussing and Documenting

ここでは仕様を決めるために話し合うわけだが、

1. 抽象的な仕様について話し合うのではなく、具体的な Example を挙げる。(Specification by Example: SBE)
    - それぞれの例について、context ("Given"), action ("When"), outcome ("Then") を明確にする。
2. 次に、具体的な Example を分類して acceptance criteria を決める。


![](https://concordion.org/img/discuss-3-levels.png)

作成されるドキュメントの構造は例えば以下のようなものとなる。

```
# Feature title

Feature description

## Business Rules or Acceptance Criteria


- Rule 1
- Rule 2

...

## Additional detail

Any other background info

### Example 1

details of example 1

### Example 2

details of example 2

...

```

cucumber のドキュメントでは以下のような例が書いてある。

```
Feature: Is it Friday yet?
  Everybody wants to know when it's Friday

  Scenario: Sunday isn't Friday
    Given today is Sunday
    When I ask whether it's Friday yet
    Then I should be told "Nope"

```



参考:

- [Getting Started: The basics of creating living documents using Concordion](https://concordion.org/tutorial/java/markdown/)
- [Write a Scenario | Cucumber 10 minutes tutorial](https://cucumber.io/docs/guides/10-minute-tutorial/#write-a-scenario)


## 参考資料

- [5 BDD Testing Frameworks to Consider | Perfecto (2021)](https://www.perfecto.io/blog/bdd-testing-frameworks)
    - BDD とは何かに関する完結でわかりやすい説明がある。
- [Getting Started: The basics of creating living documents using Concordion](https://concordion.org/tutorial/java/markdown/)
    - BDD の手順に関するわかりやすい説明。
- [Test-Driven Java Development | Packet Publishing (2018)](https://learning.oreilly.com/library/view/test-driven-java-development/9781788836111/)
    - TDD と BDD の違いなど包括的な説明が書かれた書籍。
