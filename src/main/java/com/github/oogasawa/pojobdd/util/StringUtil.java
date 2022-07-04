package com.github.oogasawa.pojobdd.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static ArrayList<String> splitByChar(String str, char ch) {

        ArrayList<String> ret = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                ret.add(sb.toString());
                sb.delete(0, sb.length());

                // When a delimiting character is at the last of a string,
                // the last element of the returned list should be an empty string.
                if (i == str.length() - 1) {
                    sb.append("");
                }
            } else {
                sb.append(str.charAt(i));
            }

        }

        ret.add(sb.toString());

        return ret;
    }

    public static ArrayList<String> splitBySpace(String str) {
        return splitByChar(str, ' ');
    }

    public static ArrayList<String> splitByTab(String str) {
        return splitByChar(str, '\t');
    }

    public static ArrayList<String> splitByComma(String str) {
        return splitByChar(str, ',');
    }

    public static ArrayList<String> splitByNewLine(String str) {
        return splitByChar(str, '\n');
    }

    public static ArrayList<String> splitByRegex(String str, Pattern p) {

        Matcher m = p.matcher(str);

        int start = 0;
        ArrayList<String> ret = new ArrayList<String>();

        while (m.find()) {
            ret.add(str.substring(start, m.start()));
            start = m.end();
        }
        ret.add(str.substring(start, str.length()));

        return ret;
    }
}
