package ex05;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] names = new String[10];
        String buffer;
        for (int i = 0; i < 10 && !(buffer = in.next()).equals("."); ++i) {
            if (buffer.length() > 10) {
                printError();
            }
            names[i] = buffer;
        }
        String[][] timetable = new String[7][6];
        for (int i = 0; i < 10 && !(buffer = in.next()).equals("."); ++i) {
            fillTimetable(timetable, buffer, in.next());
        }
        int[][] attendances = new int[10][4];
        for (int i = 0; i < 10 && !(buffer = in.next()).equals("."); ++i) {
            attendances[i][0] = getNameId(names, buffer);
            attendances[i][1] = getHour(in.next()) - 1;
            attendances[i][2] = getDay(in);
            attendances[i][3] = getAttendanceStatus(in.next());
        }
        in.close();
        printHeader(timetable);
        printTimetable(timetable, names, attendances);
    }

    public static void fillTimetable(String[][] timetable, String time,
                                     String day) {
        int hour = time.toCharArray()[0] - '0';
        if (time.length() > 1 || hour < 1 || hour > 6) {
            printError();
        }
        if ("MO".equals(day)) {
            timetable[0][hour - 1] = day;
        } else if ("TU".equals(day)) {
            timetable[1][hour - 1] = day;
        } else if ("WE".equals(day)) {
            timetable[2][hour - 1] = day;
        } else if ("TH".equals(day)) {
            timetable[3][hour - 1] = day;
        } else if ("FR".equals(day)) {
            timetable[4][hour - 1] = day;
        } else if ("SA".equals(day)) {
            timetable[5][hour - 1] = day;
        } else if ("SU".equals(day)) {
            timetable[6][hour - 1] = day;
        } else {
            printError();
        }
    }

    public static int getNameId(String[] names, String name) {
        int id = 0;
        boolean status = false;
        for (; id < names.length; ++id) {
            if (names[id] != null && names[id].equals(name)) {
                status = true;
                break;
            }
        }
        if (!status) {
            printError();
        }
        return id;
    }

    public static int getHour(String time) {
        int hour = time.toCharArray()[0] - '0';
        if (time.length() > 1 || hour < 1 || hour > 6) {
            printError();
        }
        return hour;
    }

    public static int getDay(Scanner in) {
        if (!in.hasNextInt()) {
            printError();
        }
        int day = in.nextInt();
        if (day < 1 || day > 30) {
            printError();
        }
        return day;
    }

    public static int getAttendanceStatus(String status) {
        int result = 0;
        if (status.equals("HERE")) {
            result = 1;
        } else if (status.equals("NOT_HERE")) {
            result = -1;
        } else {
            printError();
        }
        return result;
    }

    public static void printHeader(String[][] timetable) {
        System.out.printf("%10s|", "");
        for (int j = 1, i = 0; j <= 30 && i < 10; ++j) {
            int day = j % 7;
            for (int k = 0; k < timetable[j % 7].length && i < 10; ++k) {
                if (timetable[day][k] == null) {
                    continue;
                }
                System.out.printf("%d:00 %s%3d|", k + 1, timetable[day][k], j);
                ++i;
            }
        }
        System.out.println();
    }

    public static void printTimetable(String[][] timetable, String[] names,
                                      int[][] attendances) {
        for (int l = 0; l < 10; ++l) {
            String name = names[l];
            if (name == null) {
                continue;
            }
            System.out.printf("%10s|", name);
            for (int j = 1, i = 0; j <= 30 && i < 10; ++j) {
                for (int k = 0; k < timetable[j % 7].length && i < 10; ++k) {
                    if (timetable[j % 7][k] != null) {
                        printStatus(attendances, l, k, j);
                        ++i;
                    }
                }
            }
            System.out.println();
        }
    }

    public static void printStatus(int[][] attendances, int name, int hour,
                                   int day) {
        int i = 0;
        for (; i < attendances.length; ++i) {
            if (attendances[i][0] == name && attendances[i][1] == hour &&
                    attendances[i][2] == day) {
                System.out.printf("%10d|", attendances[i][3]);
                break;
            }
        }
        if (i == attendances.length) {
            System.out.printf("%10s|", "");
        }
    }

    public static void printError() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }
}
