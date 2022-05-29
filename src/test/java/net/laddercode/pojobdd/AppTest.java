package net.laddercode.pojobdd;

public class AppTest {

    public void testAll() {

        assert BddUtilSpec.exec();
        assert MdToArraySpec.exec();
    }

}
