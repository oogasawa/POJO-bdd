package net.laddercode.pojobdd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;


public class MdToArray {

    public static void main(String[] args) {

        try (var br = new BufferedReader(new InputStreamReader(new FileInputStream(args[1])))) {
            br.lines()
                .forEach(line->{
                        System.out.println("\"" + line + "\",");
                    });
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }


}
