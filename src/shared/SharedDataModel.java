package shared;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SharedDataModel {
    private final StringProperty sharedText = new SimpleStringProperty();

    public String getSharedText() {
        return sharedText.get();
    }

    public StringProperty sharedTextProperty() {
        return sharedText;
    }

    public void setSharedText(String text) {
        sharedText.set(text);
    }
}
