
## `allTrue(List<Boolean>)`

- Given a Boolean list
- When the size of the list >= 1
- Then returns true if all the elements of given array are true, otherwise false

````
[true] => true
[true, true] => true
[true, false] => false
````

- Given a Boolean list
- When list is `null` or the size of the list == 0
- Then returns false

````
null => false
[] => false
````

## convertRecursively

- Given a base directory of markdown files
- When the directory contains no less than one markdown files
- Then convert each markdown file to a HTML file, with replacing its extention .md to .html

