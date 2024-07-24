package org.example.utils;

public class ColorUtil {

    /**
     * Translates color codes in the given text.
     * <p>
     * Scans the input text for the '&' character followed by a valid color code character (0-9, a-f | A-F) and
     * replaces '&' with '§' and converts the following character to lowercase to ensure proper formatting.
     * </p>
     *
     * @param text Input text containing color codes to be translated.
     * @return Translated text with Minecraft color codes.
     */
    public static String translate(String text) {
        char[] translation = text.toCharArray();
        for (int i = 0; i < translation.length - 1; ++i) {
            if (translation[i] == '&' && "0123456789AaBbCcDdEeFf".indexOf(translation[i + 1]) > -1) {
                translation[i] = 167;
                translation[i + 1] = Character.toLowerCase(translation[i + 1]);
            }
        }
        return new String(translation);
    }
}