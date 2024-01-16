package ex02;

public class UsersArrayList implements UsersList {
    private int size;
    private int numberOfUsers;
    private User[] array;

    public UsersArrayList() {
        size = 10;
        numberOfUsers = 0;
        array = new User[size];
    }

    @Override
    public void addUser(User user) {
        if (numberOfUsers == size) {
            size *= 1.5;
            User[] temp = new User[size];
            System.arraycopy(array, 0, temp, 0, array.length);
            array = temp;
        }
        array[numberOfUsers++] = user;
    }

    @Override
    public User getUserByID(int id) {
        for (int i = 0; i < numberOfUsers; ++i) {
            if (array[i].getIdentifier() == id) {
                return array[i];
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUserByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new UserNotFoundException();
        }
        return array[index];
    }

    @Override
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public static class UserNotFoundException
            extends IndexOutOfBoundsException {
    }
}