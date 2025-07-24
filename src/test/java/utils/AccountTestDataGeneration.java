package utils;

import com.github.javafaker.Faker;

import java.util.Random;

public class AccountTestDataGeneration {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARS = LETTERS + DIGITS;

    public static String generateEmail() {
        return faker.name().firstName().toLowerCase() + "." +
                faker.name().lastName().toLowerCase() +
                faker.number().digits(3) + "@test.com";
    }

    public static String generatePassword() {
        int length = 8 + random.nextInt(18); // 8-25 signs

        char letter = LETTERS.charAt(random.nextInt(LETTERS.length()));
        char digit = DIGITS.charAt(random.nextInt(DIGITS.length()));

        StringBuilder password = new StringBuilder();
        password.append(letter);
        password.append(digit);

        for (int i = 2; i < length; i++) {
            password.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
        }

        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int j = random.nextInt(chars.length);
            char tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;
        }
        return new String(chars);
    }

    /*public static String generatePassword() {
        int length = random.nextInt(18) + 8;
        StringBuilder password = new StringBuilder();
        password.append((char) ('A' + random.nextInt(26)));
        password.append((char) ('0' + random.nextInt(10)));
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String allChars = letters + digits;
        for (int i = 2; i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        return password.toString();
    }*/

    public static String generateFirstName() {

        return faker.name().firstName();
    }

    public static String generateLastName() {

        return faker.name().lastName();
    }

    public static String generateMobileNumber() {

        return "+48" + faker.number().digits(9); // PL-format
    }

    public static String generateZipCode() {

        return faker.address().zipCode().replaceAll("\\D+", "").substring(0, 5);
    }

    public static int generateBirthMonth() {

        return faker.number().numberBetween(1, 13); // 1–12
    }

    public static int generateBirthDay() {

        return faker.number().numberBetween(1, 29);
    }


    public static TestUser generateTestUser() {
        return TestUser.builder()
                .email(generateEmail())
                .password(generatePassword())
                .firstName(generateFirstName())
                .lastName(generateLastName())
                .zipCode(generateZipCode())
                .build();
    }
}
