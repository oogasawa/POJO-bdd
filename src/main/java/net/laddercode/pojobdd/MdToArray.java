package net.laddercode.pojobdd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class MdToArray {

    public static void main(String[] args) {

        System.out.println(mdToArray(args[1]));

    }

    public static List<String> mdToArray(BufferedReader br) {
            return br.lines()
                .map(line->{
                        return "\"" + line + "\",";
                    })
                .collect(toList());
    }



    public static List<String> mdToArray(String filename) {

        List<String> result = new ArrayList<>();

        try (var br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {

            return mdToArray(br);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;

    }


}
