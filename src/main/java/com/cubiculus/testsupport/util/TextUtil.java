package com.cubiculus.testsupport.util;

public class TextUtil {

    private final static String RANDOM_TEXT_PIECE = "0123456789";

    /**
     * Method generate random text of some length. It's useful for testing too long
     * strings.
     * 
     * @param length
     * @return
     */
    public static String getRandomText(final int length) {
        final StringBuilder stringBuilder = new StringBuilder(5000);
        for (int len = 0; len < length; len += RANDOM_TEXT_PIECE.length()) {
            stringBuilder.append(RANDOM_TEXT_PIECE);
        }
        return stringBuilder.toString();
    }
}
