#!/bin/bash

mvn site:site javadoc:javadoc
rm -Rf ~/public_html/javadoc/POJO-bdd
mv target/site ~/public_html/javadoc/POJO-bdd
