package com.github.oogasawa.pojobdd;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.oogasawa.pojobdd.util.StringUtilSpec;

public class AppTest {

    public void testAll() {

        Logger.getGlobal().setLevel(Level.OFF);

        assert StringUtilSpec.exec();
        assert DiffSpec.exec();
        assert AllTrueSpec.exec();
        assert ReadSnippetSpec.exec();


    }

}
