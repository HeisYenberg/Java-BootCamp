package ex04;

public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private Integer userId = 0;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public Integer generateId() {
        return ++userId;
    }
}