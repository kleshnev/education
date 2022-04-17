package stage2.Tasks1.practice.Task1;

import java.util.Scanner;
import java.util.Stack;

public class Task1 {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        char[] userInput = String.join("", in.nextLine()
                        .replace(" ", "")
                        .split(""))
                .toCharArray();
        System.out.println(checkBracketsAreCorrect(userInput)
                ? "Структура скобок правильная" : "Ошибка в скобочной структуре");

    }

    public static boolean checkBracketsAreCorrect(char[] array) {
        Stack<Character> stack = new Stack<>();
        for (Character chr : array) {
            switch (chr) {
                case '(' -> stack.push(')');
                case '{' -> stack.push('}');
                case '[' -> stack.push(']');
                case ')', '}', ']' -> {
                    if (!stack.empty() && stack.peek() != chr) {
                        return false;
                    }
                    stack.pop();
                }
            }
        }
        return stack.empty();
    }
}


