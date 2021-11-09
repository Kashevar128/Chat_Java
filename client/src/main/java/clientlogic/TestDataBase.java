package clientlogic;

import java.util.ArrayList;
import java.util.List;

public class TestDataBase {
    private List<String> users;
    private static TestDataBase testDataBase;
    private static int count = 0;

    private TestDataBase() {
        users = new ArrayList<>();
        users.add("Слава");
        users.add("Серега");
        users.add("Егор");
        users.add("Клава");
        users.add("Маша");
    }

    public static synchronized TestDataBase getInstance() {
        if (testDataBase == null) {
            testDataBase = new TestDataBase();
        }
        return testDataBase;
    }

    public String getName() {
        String name = users.get(count);
        count++;
        if(count > users.size()) {
            count = 0;
        }
        return name;
    }
}
