package utils;

public class Utils {
    public static String formatSearchQueryForUrl(String query) {
        if (query == null || query.isEmpty()) {
            return "";
        }

        return query.trim()
                .toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9\\-]", "");
    }
}
