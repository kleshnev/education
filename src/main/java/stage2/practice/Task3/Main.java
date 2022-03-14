package stage2.practice.Task3;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static int num;
    static Map<Character, String> map;
    static Map<String, Character> reverseMap;

    public static void main(String[] args) {
        map = getMap();
        reverseMap = getReverseMap();
        Scanner in = new Scanner(System.in);
        System.out.println("Выберите: \n1. Compress(упаковка)\n2. Decompress(распаковка)");
        int choice = in.nextInt();
        in.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.println("Введите строку используя {A,C,T,G}:");
                String input = in.nextLine();
                num = input.length() *2;
                System.out.println(bitSetToString(compress(input)));
            }
            case 2 -> {
                System.out.println("Введите число в битовом представлении:");
                String input = in.nextLine();
                num = input.length();
                System.out.println(decompress(stringToBitSet(input)));
            }
        }

    }

    public static BitSet compress(String userInput) {
        char[] arr = userInput.toCharArray();
        BitSet bitSet = new BitSet(arr.length * 2);
        for (int i = 0, j = 0; i < arr.length; i++, j += 2) {
            if (map.containsKey(arr[i])) {
                bitSet.set(j, map.get(arr[i]).charAt(0) == '1');
                bitSet.set(j + 1, map.get(arr[i]).charAt(1) == '1');
            }
        }
        return bitSet;

    }

    public static String decompress(BitSet bitSet) {
        StringBuilder stringBuilder = new StringBuilder();
        String str = bitSetToString(bitSet);
        String[] array = str.split("(?<=\\G..)");
        for (String key : array) {
            if (reverseMap.containsKey(key)) {
                stringBuilder.append(reverseMap.get(key));
            }
        }
        return stringBuilder.toString();
    }

    private static BitSet stringToBitSet(String binary) {
        BitSet bitset = new BitSet(binary.length());
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                bitset.set(i);
            }
        }
        return bitset;
    }

    public static String bitSetToString(BitSet bitSet) {
        StringBuilder str = new StringBuilder();
        str.append("0".repeat(Math.max(0, num)));
        for (int id = bitSet.nextSetBit(0); id >= 0; id = bitSet.nextSetBit(id + 1)) {
            str.replace(id,id+1, "1");
        }
        return str.toString();
    }

    public static Map<Character, String> getMap() {
        HashMap<Character, String> map = new HashMap<>();
        map.put('A', "00");
        map.put('C', "01");
        map.put('G', "10");
        map.put('T', "11");
        return map;
    }

    public static Map<String, Character> getReverseMap() {
        HashMap<String, Character> map = new HashMap<>();
        map.put("00", 'A');
        map.put("01", 'C');
        map.put("10", 'G');
        map.put("11", 'T');
        return map;
    }
}
