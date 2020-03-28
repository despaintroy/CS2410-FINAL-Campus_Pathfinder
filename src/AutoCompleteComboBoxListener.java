/*
This class is based on code from JulianG posted on Stack Overflow
https://stackoverflow.com/questions/19010619/javafx-filtered-combobox
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private ComboBox comboBox;
    private ObservableList<T> data;
    private boolean moveCursorToPos = false;
    private int cursorPos;

    public AutoCompleteComboBoxListener(final ComboBox comboBox) {
        data = comboBox.getItems();
        this.comboBox = comboBox;
        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            private KeyEvent t;
            @Override
            public void handle(KeyEvent t) {
                this.t = t;
                comboBox.hide();
            }
        });
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {

        KeyCode eventCode = event.getCode();

        // Handle special key codes
        if(eventCode == KeyCode.UP) {
            moveCursorToEnd();
            return;
        } else if(eventCode == KeyCode.DOWN) {
            if(!comboBox.isShowing()) {
                comboBox.show();
            }
            moveCursorToEnd();
            return;
        } else if(eventCode == KeyCode.BACK_SPACE) {
            moveCursorToPos = true;
            cursorPos = comboBox.getEditor().getCaretPosition();
            comboBox.hide();
        } else if(eventCode == KeyCode.DELETE) {
            moveCursorToPos = true;
            cursorPos = comboBox.getEditor().getCaretPosition();
        }
        else if (eventCode == KeyCode.RIGHT || eventCode == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.TAB) {
            return;
        }
        else if (eventCode == KeyCode.ENTER) {
            return;
        }


        // Filter the list and put it in the ComboBox
        ObservableList<Object> list = FXCollections.observableArrayList();
        for (T d : data) {
            if (d.toString().toLowerCase().contains(
                    AutoCompleteComboBoxListener.this.comboBox.getEditor().getText().toLowerCase())) {
                list.add(d);
            }
        }

        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if(!moveCursorToPos) {
            cursorPos = -1;
        }

        moveCursor(t.length());

        if(!list.isEmpty()) {
//            comboBox.hide();
            comboBox.show();
        }
    }

    private void moveCursorToEnd() {
        cursorPos = -1;
        comboBox.getEditor().positionCaret(comboBox.getEditor().getText().length());
    }

    private void moveCursor(int textLength) {
        if(cursorPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(cursorPos);
        }
        moveCursorToPos = false;
    }
}