package ex04;

public class UsersArrayList implements UsersList {
    private int size;
    private int numberOfUsers;
    private User[] users;

    public UsersArrayList() {
        size = 10;
        numberOfUsers = 0;
        users = new User[size];
    }

    public UsersArrayList(int size) {
        this.size = size;
        numberOfUsers = 0;
        users = new User[size];
    }

    @Override
    public void addUser(User user) {
        if (numberOfUsers == size) {
            size *= 1.5;
            User[] temp = new User[size];
            System.arraycopy(users, 0, temp, 0, users.length);
            users = temp;
        }
        users[numberOfUsers++] = user;
    }

    @Override
    public User getUserByID(int id) {
        for (int i = 0; i < numberOfUsers; ++i) {
            if (users[i].getIdentifier() == id) {
                return users[i];
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUserByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new UserNotFoundException();
        }
        return users[index];
    }

    @Override
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public static class UserNotFoundException
            extends IndexOutOfBoundsException {
    }
}