package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.logic.commands.exceptions.CommandException;

public class ChangeThemeCommand extends UndoableCommand{

    public static final String COMMAND_WORD = "theme";

    public static final String[] ALL_THEME_NAMES = {"dark", "bright"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the theme of the application. "
            + "Parameters: " + COMMAND_WORD + " "
            + "[THEME]\n"
            + "Example: " + COMMAND_WORD + " bright";

    public static final String MESSAGE_INVALID_THEME_NAME = "The theme %1$s is not supported. "
            + "Supported themes are: "
            + Arrays.toString(ALL_THEME_NAMES);

    public static final String MESSAGE_CHANGE_THEME_SUCCESS = "Changed theme to %1$s.";

    private final String theme;

    /**
     * Constructor
     * @param theme the new theme
     */
    public ChangeThemeCommand(String theme) {
        requireNonNull(theme);
        this.theme = theme;
    }

    /**
     * Returns true if a given string is a valid theme name.
     */
    public static boolean isValidThemeName(String test) {
        return Arrays.asList(ALL_THEME_NAMES).contains(test);
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        // Check whether a given theme name is valid
        if (!isValidThemeName(theme)) {
            throw new CommandException(String.format(MESSAGE_INVALID_THEME_NAME, theme));
        }
        return new CommandResult(String.format(MESSAGE_CHANGE_THEME_SUCCESS, theme));
    }
}
