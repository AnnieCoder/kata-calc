package ru.kata.calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    private static ArrayList<String> arabicNumbers = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        initialArabicNumbers();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String result = calc(reader.readLine());
            System.out.println(result);
        } catch (CalcException err) {
            System.out.println(err);
        }

    }

    public static void initialArabicNumbers() {
        for (int i = 1; i <= 10; i++) {
            arabicNumbers.add(Integer.toString(i));
        }
    }

    public static String calc(String input) throws CalcException {

        String[] arrStrings = input.split(" ");

        if (arrStrings.length < 3) {
            throw new CalcException("строка не является математической операцией");
        }

        if (arrStrings.length > 3) {
            throw new CalcException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        int result = 0;
        int firstNumber = 0;
        int secondNumber = 0;
        boolean isArabicNumbers = true;
        String mathOperation = arrStrings[1];

        if (isArabicNumber(arrStrings[0]) && isRomeNumber(arrStrings[2])) {
            throw new CalcException("используются одновременно разные системы счисления");
        }

        if (isArabicNumber(arrStrings[2]) && isRomeNumber(arrStrings[0])) {
            throw new CalcException("используются одновременно разные системы счисления");
        }

        try {

            firstNumber = Integer.parseInt(arrStrings[0]);
            secondNumber = Integer.parseInt(arrStrings[2]);

        } catch (Exception err) {

            isArabicNumbers = false;

        }

        if (isArabicNumbers) {

            result = calculateArabicRomeNumbers(firstNumber, secondNumber, mathOperation);
            return Integer.toString(result);

        } else {

            try {
                firstNumber = RomeNumber.valueOf(arrStrings[0].toUpperCase()).getArabicNumber();
                secondNumber = RomeNumber.valueOf(arrStrings[2].toUpperCase()).getArabicNumber();
                isArabicNumbers = false;
            } catch (Exception err) {
                throw new CalcException("одно из введенных чисел не соответствует формату");
            }

            result = calculateArabicRomeNumbers(firstNumber, secondNumber, mathOperation);

            if (result <= 0) throw new CalcException("в римской системе нет отрицательных чисел");

            return getRomeNumber(result);
        }

    }

    public static boolean isArabicNumber(String number) {

        boolean numberIsArabic = true;

        try {
            int arabicNumber = Integer.parseInt(number);
        } catch (Exception err) {
            numberIsArabic = false;
        }

        return numberIsArabic;

    }

    public static boolean isRomeNumber(String number) {

        boolean numberIsRome = true;

        try {
            int romeNumber = RomeNumber.valueOf(number.toUpperCase()).getArabicNumber();
        } catch (Exception err) {
            numberIsRome = false;
        }

        return numberIsRome;

    }

    public static int calculateArabicRomeNumbers(int firstNumber, int secondNumber, String mathOperation) throws CalcException{

        if (firstNumber == 0 || secondNumber == 0) {
            throw new CalcException("одно из введенных чисел не соответствует формату");
        }

        if (firstNumber < 1 || firstNumber > 10 || secondNumber < 1 || secondNumber > 10) {
            throw new CalcException("числа в выражении должны быть целочисленными и располагаться в диапазоне от 1 (I) до 10 (X)");
        }

        return calculateExpression(firstNumber, secondNumber, mathOperation);

    }

    public static int calculateExpression(int firstNumber, int secondNumber, String mathOperation) throws CalcException {

        int result = 0;

        switch (mathOperation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                result = firstNumber / secondNumber;
                break;
            default:
                throw new CalcException("Не удалось вычислить выражение");
        }
        return result;
    }

    public static String getRomeNumber(int number) {

        for (RomeNumber element : RomeNumber.values()) {
            if (element.getArabicNumber() == number) {
                return element.name();
            }
        }

        return "";
    }
}
