
## `BddUtil.allTrue(List<Boolean>)`

### Scenario 01: a non-empty boolean list

- Given a Boolean list
- When the size of the list >= 1
- Then returns true if all the elements of given array are true, otherwise false.

Code:

```
// src/test/java/com/github/oogasawa/pojobdd/AllTrueSpec.java
// %begin snippet : allTrueSpec01
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
String result = answers.toString();
// %end snippet : allTrueSpec01


```

Result:


````
[true] => true
[true, true] => true
[true, false] => false
````

### Scenario 02: a `null` or an empty list

- Given a Boolean list
- When list is `null` or the size of the list == 0
- Then returns false


Code:

```
// src/test/java/com/github/oogasawa/pojobdd/AllTrueSpec.java
// %begin snippet : allTrueSpec02
ArrayList<ArrayList<Boolean>> examples = new ArrayList<>();
ArrayList<Boolean> row1 = null;
examples.add(row1);
ArrayList<Boolean> row2 = new ArrayList<>();
examples.add(row2);


StringJoiner results = new StringJoiner("\n");

results.add(String.format("%s => %s", "null", BddUtil.allTrue(examples.get(0))));
for (int i=1; i<examples.size(); i++) {
    ArrayList<Boolean> row = examples.get(i);
    results.add(String.format("%s => %s",
                              row.toString(),
                              BddUtil.allTrue(examples.get(i))));
}
String result = results.toString();
// %end snippet : allTrueSpec02


```

Result:



````
null => false
[] => false
````
