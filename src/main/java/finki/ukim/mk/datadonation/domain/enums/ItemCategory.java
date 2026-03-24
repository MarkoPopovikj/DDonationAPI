package finki.ukim.mk.datadonation.domain.enums;

public enum ItemCategory {
    IMAGE("images"),
    AUDIO("audio"),
    TEXT("text");

    private final String folderName;

    ItemCategory(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public static ItemCategory fromMimeType(String mimeType) {
        if (mimeType == null) {
            throw new IllegalArgumentException("File type could not be detected.");
        }

        String lowerMime = mimeType.toLowerCase();

        if (lowerMime.startsWith("image/")) return IMAGE;

        if (lowerMime.startsWith("audio/")) return AUDIO;

        if (lowerMime.startsWith("text/") ||
                lowerMime.contains("json") ||
                lowerMime.contains("csv") ||
                lowerMime.contains("pdf") ||
                lowerMime.contains("msword") ||
                lowerMime.contains("officedocument")) {
            return TEXT;
        }

        throw new IllegalArgumentException("Unsupported file type: " + mimeType);
    }
}