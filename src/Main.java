import java.util.*;

public class Main {
    private static final TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public static void main(String[] args) {
        // Примеры использования:
        processCalculation("1 + 2"); // Ожидаемый результат: 3
        processCalculation("VI / III"); // Ожидаемый результат: II
        processCalculation("I - II"); // Ожидаемое исключение, т.к. в римской системе нет отрицательных чисел
        processCalculation("I + 1"); // Ожидаемое исключение, т.к. используются одновременно разные системы счисления
        processCalculation("1"); // Ожидаемое исключение, т.к. строка не является математической операцией
        processCalculation("1 + 2 + 3"); // Ожидаемое исключение, т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)
    }

    private static void processCalculation(String input) {
        try {
            calculate(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void calculate(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Неверный ввод");
        }
        int num1;
        int num2;
        String operator = parts[1];
        boolean isRoman = false;
        if (parts[0].matches("[a-zA-Z]+") && parts[2].matches("[a-zA-Z]+")) {
            isRoman = true;
            num1 = romanToArabic(parts[0]);
            num2 = romanToArabic(parts[2]);
        } else if (parts[0].matches("[0-9]+") && parts[2].matches("[0-9]+")) {
            num1 = Integer.parseInt(parts[0]);
            num2 = Integer.parseInt(parts[2]);
        } else {
            throw new IllegalArgumentException("Неверный ввод");
        }
        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Неверный ввод");
        }
        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неверный оператор");
        }
        if (isRoman) {
            if (result < 1) {
                throw new IllegalArgumentException("Результат меньше 1");
            }
            System.out.println(arabicToRoman(result));
        } else {
            System.out.println(result);
        }
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        int i = 0;
        while (i < roman.length()) {
            char ch = roman.charAt(i);
            int number = letterToNumber(ch);
            i++;
            if (i == roman.length()) {
                result += number;
            } else {
                int nextNumber = letterToNumber(roman.charAt(i));
                if (nextNumber > number) {
                    result += (nextNumber - number);
                    i++;
                } else {
                    result += number;
                }
            }
        }
        return result;
    }

    private static String arabicToRoman(int arabic) {
        if (arabic < 1) {
            throw new IllegalArgumentException("Число должно быть больше 0");
        }
        int l =  map.floorKey(arabic);
        if ( arabic == l ) {
            return map.get(arabic);
        }
        return map.get(l) + arabicToRoman(arabic-l);
    }

    private static int letterToNumber(char letter) {
        switch (letter) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return -1;
        }
    }
}



