package finki.ukim.mk.datadonation.util;

public final class AvatarUtil {

    private AvatarUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String generatePlaceholder(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "https://placehold.co/96x96/CCCCCC/FFFFFF?text=U";
        }

        String hexColor = String.format("%06X", (0xFFFFFF & username.hashCode()));
        String firstLetter = username.substring(0, 1).toUpperCase();

        return "https://placehold.co/96x96/" + hexColor + "/FFFFFF?text=" + firstLetter;
    }
}
