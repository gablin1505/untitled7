import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input ");
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println(" Output " + result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static String calculate(String input) {
        String[] parts = input.split("[+\\-*/]");

        if (isMixed(parts[0], parts[1])) {
            throw new IllegalArgumentException("используются одновременно разные системы счисления");
        }

        if (isRoman(parts[0])) {
            int num1 = romanToArabic(parts[0]);
            if (num1 < 1 || num1 > 10) {
                throw new IllegalArgumentException("Римское число должно быть от I до X");
            }
        } else {
            int num1 = Integer.parseInt(parts[0]);
            if (num1 < 1 || num1 > 10) {
                throw new IllegalArgumentException("Число должно быть от 1 до 10");
            }
        }

        if (isRoman(parts[1])) {
            int num2 = romanToArabic(parts[1]);
            if (num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Римское число должно быть от I до X");
            }
        } else {
            int num2 = Integer.parseInt(parts[1]);
            if (num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Число должно быть от 1 до 10");
            }
        }

        int num1 = isRoman(parts[0]) ? romanToArabic(parts[0]) : Integer.parseInt(parts[0]);
        int num2 = isRoman(parts[1]) ? romanToArabic(parts[1]) : Integer.parseInt(parts[1]);

        char operation = input.replaceAll("[0-9IVXivx]", "").charAt(0);

        int result;
        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Недопустимая операция");
        }

        if (isRoman(parts[0])) {
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("[IVXivx]+");
    }

    private static boolean isMixed(String part1, String part2) {
        return (isRoman(part1) && !isRoman(part2)) || (!isRoman(part1) && isRoman(part2));
    }

    private static int romanToArabic(String romanNumber) {
        int result = 0;
        for (int i = 0; i < romanNumber.length(); i++) {
            char currentChar = romanNumber.charAt(i);
            if (currentChar == 'I') {
                result += 1;
            } else if (currentChar == 'V') {
                result += 5;
            } else if (currentChar == 'X') {
                result += 10;
            }
        }
        return result;
    }

    private static String arabicToRoman(int number) {
        if (number < 1) {
            return "Отрицательный результат нельзя представить римскими цифрами";
        }

        String[] romanSymbols = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI","XX","XL","L","XC","C"};
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,11,20,40,50,90,100};

        StringBuilder result = new StringBuilder();
        while (number > 0) {
            int i = values.length - 1;
            while (values[i] > number) {
                i--;
            }
            result.append(romanSymbols[i]);
            number -= values[i];
        }

        return result.toString();
    }
}