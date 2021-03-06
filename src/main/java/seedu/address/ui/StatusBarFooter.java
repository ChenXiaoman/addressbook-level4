package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.model.font.FontSize;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    /**
     * Used to generate time stamps.
     * <p>
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;
    @FXML
    private StatusBar totalPersons;


    public StatusBarFooter(String saveLocation, int numberOfTotalPersons) {
        super(FXML);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
        setTotalPerson(numberOfTotalPersons);
    }

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    private void setSaveLocation(String location) {
        Platform.runLater(() -> this.saveLocationStatus.setText(location));
    }

    private void setSyncStatus(String status) {
        Platform.runLater(() -> this.syncStatus.setText(status));
    }

    /**
     * Get the display text in status bar totalPersons for a given number
     *
     * @param numberOfTotalPersons
     * @return string of display text
     */
    public static String getDisplayTextTotalPerson(int numberOfTotalPersons) {
        String displayText = "";

        if (numberOfTotalPersons > 1) {
            displayText = numberOfTotalPersons + " persons total.";
        } else {
            displayText = numberOfTotalPersons + " person total.";
        }
        return displayText;
    }

    private void setTotalPerson(int numberOfTotalPersons) {
        this.totalPersons.setText(getDisplayTextTotalPerson(numberOfTotalPersons));
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce,
                "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
        setTotalPerson(abce.data.getPersonList().size());
    }

    //@@author ChenXiaoman
    @Subscribe
    private void handleChangeFontSizeEvent(ChangeFontSizeEvent event) {
        setFontSize(event.getFontSize());
    }

    private void setFontSize(String fontSize) {
        String fxFomatString = FontSize.getAssociateFxFontSizeString(fontSize);
        syncStatus.setStyle(fxFomatString);
        saveLocationStatus.setStyle(fxFomatString);
        totalPersons.setStyle(fxFomatString);
    }
}
