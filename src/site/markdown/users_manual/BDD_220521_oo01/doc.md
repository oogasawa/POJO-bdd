
## What is BDD?

BDD consists of the following four steps

1. Discussing
    - Clarify the specification by giving concrete examples. (Specification by Example: SBE)
2. Documenting
    - Documenting what has been discussed.
3. instrumenting
    - Make the document executable.
4. Coding
    - Writing the actual program.

BDD is a process downstream from the use case analysis.


### Discussing and Documenting


We're here to discuss specifications.

1. Instead of discussing abstract specifications, concrete examples are given. (Specification by Example: SBE)
    - For each example, clarify the context ("Given"), action ("When"), and outcome ("Then"). for each example.
2. Next, categorize the specific Examples and determine acceptance criteria.


![](https://concordion.org/img/discuss-3-levels.png)

The structure of the document to be created will be, for example


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


The cucumber documentation describes the following example.

```
Feature: Is it Friday yet?
  Everybody wants to know when it's Friday

  Scenario: Sunday isn't Friday
    Given today is Sunday
    When I ask whether it's Friday yet
    Then I should be told "Nope"

```



## References

- [Getting Started: The basics of creating living documents using Concordion](https://concordion.org/tutorial/java/markdown/)
- [Write a Scenario | Cucumber 10 minutes tutorial](https://cucumber.io/docs/guides/10-minute-tutorial/#write-a-scenario)
- [5 BDD Testing Frameworks to Consider | Perfecto (2021)](https://www.perfecto.io/blog/bdd-testing-frameworks)
    - A concise, easy-to-understand explanation of what BDD is.
- [Getting Started: The basics of creating living documents using Concordion](https://concordion.org/tutorial/java/markdown/)
    - An easy-to-understand explanation of the steps involved in BDD.
- [Test-Driven Java Development | Packet Publishing (2018)](https://learning.oreilly.com/library/view/test-driven-java-development/9781788836111/)
    - Comprehensive explanation of BDD including the differences between TDD and BDD.
