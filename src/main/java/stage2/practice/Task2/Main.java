package stage2.practice.Task2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static int diskCount;
    private static final ArrayList<Stack<Integer>> towers = new ArrayList<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите количество дисков:");
        diskCount = in.nextInt();
        for (int i = 0; i <= 4; i++) {
            towers.add(new Stack<>());
        }
        for (int i = diskCount; i > 0; i--) {
            towers.get(1).push(i);
        }
        int stepCount = (int) (Math.pow(2, diskCount) - 1);
        System.out.println("Чем больше значение,тем шире диск.");
        System.out.println("Количество шагов - " + stepCount);
        diskMove(diskCount, 1, 2, 3);
        printTowers();
    }

    public static void diskMove(int count, int initial, int additional,int destination) {
        if (count > 0) {
            diskMove(count - 1, initial, destination, additional);
            printTowers();
            int num = towers.get(initial).pop();
            towers.get(destination).push(num);
            diskMove(count - 1, additional, initial,destination );
        }
    }

    public static void printTowers() {
        String[] arrayA = getStackAsStringArr(towers.get(1));
        String[] arrayB = getStackAsStringArr(towers.get(2));
        String[] arrayC = getStackAsStringArr(towers.get(3));
        System.out.println("\n-----------");
        System.out.println(" A\t B\t C");
        for (int i = 0; i < diskCount; i++) {
            System.out.println(arrayA[i] + "\t" + arrayB[i] + "\t" + arrayC[i] + "\t");
        }
    }


    public static String[] getStackAsStringArr(Stack<Integer> stack) {
        String[] str = new String[diskCount];
        for (int i = 0; i < diskCount; i++) {
            if (i > stack.size() - 1) {
                str[diskCount - i - 1] = "[ ]";
            } else {
                str[diskCount - i - 1] = ("[" + stack.get(i) + "]");
            }
        }
        return str;
    }
}


