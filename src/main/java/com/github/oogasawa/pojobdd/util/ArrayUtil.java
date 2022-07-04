package com.github.oogasawa.pojobdd.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ArrayUtil {

    public static String join(String[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter);

        for (int i=0; i<array.length; i++) {
            joiner.add(array[i]);
        }

        return joiner.toString();
    }

}
