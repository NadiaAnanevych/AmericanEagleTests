package utils;

public class Utils {
    public static String formatSearchQueryForUrl(String query) {
        if (query == null || query.isEmpty()) {
            return "";
        }

        return query.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // usuń znaki specjalne, zostaw spacje
                .replaceAll("\\s+", "+");       // zamień spacje na +
    }
}
