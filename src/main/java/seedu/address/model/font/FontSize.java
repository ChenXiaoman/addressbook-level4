package seedu.address.model.font;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the font size of the AddressBook.
 * Guarantees: immutable; is valid as declared in {@link #isValidFontSize(String)}
 */
public class FontSize {

    public static final String MESSAGE_FONT_SIZE_CONSTRAINTS =
            "Font size can only be either \"xs\", \"s\","
                    + " \"m\", \"l\",  or \"xl\"";
    public static final String MESSAGE_FONT_SIZE_IS_LARGEST =
            "The current font size is the largest one.";
    public static final String MESSAGE_FONT_SIZE_IS_SMALLEST =
            "The current font size is the smallest one.";
    public static final String[] FONT_SIZE_LIST = {"xs", "s", "m", "l", "xl"};
    public static final String[] FONT_SIZE_CHANGE_SYMBOL = {"+", "-"};

    public static final String FONT_SIZE_XS_LABEL = "xs";
    public static final String FONT_SIZE_S_LABEL = "s";
    public static final String FONT_SIZE_M_LABEL = "m";
    public static final String FONT_SIZE_L_LABEL = "l";
    public static final String FONT_SIZE_XL_LABEL = "xl";

    public final String value;
    public static String currentFontSizeLabel = FONT_SIZE_M_LABEL;

    /**
     * Validates given font size.
     *
     * @throws IllegalValueException if given font size is invalid.
     */
    public FontSize(String fontSize) throws IllegalValueException {
        requireNonNull(fontSize);
        if (isValidFontSizeChangeSymbol(fontSize)) {

            // Get the new font size from "+" or "-" symbol base on current font size
            fontSize = getFontSizeFromChangeSymbol(fontSize);

        } else if (!isValidFontSize(fontSize)) {
            throw new IllegalValueException(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
        this.value = fontSize;
    }

    /**
     * Get the new font size from "+" or "-" symbol and change the current font size
     */
    private String getFontSizeFromChangeSymbol(String symbol) throws IllegalValueException {
        int fontSizeListLength = FONT_SIZE_LIST.length;

        // Increase the font size
        if (symbol.equals("+")) {

            // Find which is the current font size except the largest one
            for (int i = 0; i < fontSizeListLength - 1; i++) {

                // The current font size is at the ith place in the list
                if (currentFontSizeLabel.equals(FONT_SIZE_LIST[i])) {

                    // Get the next largest font size
                    currentFontSizeLabel =  FONT_SIZE_LIST[i + 1];
                    return currentFontSizeLabel;
                }
            }

            // Current font size is the largest font size
            throw new IllegalValueException(MESSAGE_FONT_SIZE_IS_LARGEST);

        } else {
            // Decrease the font size

            // Find which is the current font size except the smallest one
            for (int i = 1; i < fontSizeListLength; i++) {

                // The current font size is at the ith place in the list
                if (currentFontSizeLabel.equals(FONT_SIZE_LIST[i])) {
                    // Get the next smaller size
                    currentFontSizeLabel = FONT_SIZE_LIST[i - 1];
                    return currentFontSizeLabel;
                }
            }

            // Current font size is the smallest font size
            throw new IllegalValueException(MESSAGE_FONT_SIZE_IS_SMALLEST);
        }
    }

    private boolean isValidFontSizeChangeSymbol(String symbol) {
        for (String s : FONT_SIZE_CHANGE_SYMBOL) {
            if (symbol.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given string is a valid font size.
     */
    public static boolean isValidFontSize(String test) {
        for (String s : FONT_SIZE_LIST) {
            if (test.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the current font size
     */
    public static String getCurrentFontSizeLabel() {
        return currentFontSizeLabel;
    }

    /**
     * Set the current font size to a new font size
     * @param newFontSizeLabel
     */
    public static void setCurrentFontSizeLabel(String newFontSizeLabel) {
        requireNonNull(newFontSizeLabel);

        if (isValidFontSize(newFontSizeLabel)) {
            currentFontSizeLabel = newFontSizeLabel;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other.equals(this.value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


    /**
     * Get the associate fx format string for a give font size
     * @param inputFontSize
     * @return
     */
    public static String getAssociateFXFontSizeString(String inputFontSize) {
        assert (FontSize.isValidFontSize(inputFontSize));
        String FXFontSizeString = "-fx-font-size: ";
        switch (inputFontSize) {
            case FONT_SIZE_XS_LABEL:
                FXFontSizeString += "x-small;";
                break;

            case FONT_SIZE_S_LABEL:
                FXFontSizeString += "small;";
                break;

            case FONT_SIZE_M_LABEL:
                FXFontSizeString += "normal;";
                break;

            case FONT_SIZE_L_LABEL:
                FXFontSizeString += "x-large;";
                break;

            case FONT_SIZE_XL_LABEL:
                FXFontSizeString += "xx-large;";
                break;

            default:
                FXFontSizeString += "normal;";
        }
        return FXFontSizeString;
    }

}