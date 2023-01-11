#!/bin/bash

export LANG=en_US.UTF-8

mvn site:site javadoc:javadoc
rm -Rf ~/public_html/javadoc/POJO-bdd
mv target/site ~/public_html/javadoc/POJO-bdd
