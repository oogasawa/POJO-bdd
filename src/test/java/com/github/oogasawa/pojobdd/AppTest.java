package com.github.oogasawa.pojobdd;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.github.oogasawa.pojobdd.util.StringUtilSpec;

public class AppTest {

    public void testAll() {

        try {
            LogManager.getLogManager()
                    .readConfiguration(App.class.getClassLoader().getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert StringUtilSpec.exec();
        assert DiffSpec.exec();
        assert AllTrueSpec.exec();
        assert ReadSnippetSpec.exec();
        assert YamlHeaderSpec.exec();

    }

}
