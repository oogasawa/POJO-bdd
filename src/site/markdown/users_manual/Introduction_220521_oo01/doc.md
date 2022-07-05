

## POJO-BDD : Introduction 

POJO-BDD is a super simple framework for the Behavior Driven Development (BDD) and executable documentation.


- No dependence on other libraries.
    - Use of older libraries (e.g. JUnit4 and so on) is not forced because of the dependencies.
- No special notation or syntax is required when writing specification documents.
- There are almost no special conventions or restrictions when writing test code; it is simply written as a POJO (Plain Old Java Object).


In the usual BDD framework, the BDD specification document is written independently, and when the test is executed, the test result values are inserted into the document to complete the document. In other words, the results of program execution are inserted into the document.


In POJO-BDD (at least for now), the BDD specification document is written in the POJO code. In other words, the documentation is inserted into the program, and the program generates the documentation. There are advantages and disadvantages to this, but it is done in anticipation of the case where the report of the execution results when the test becomes complex cannot be handled by simply filling in holes in a template, and the structure of the report itself needs to be changed according to the situation.



