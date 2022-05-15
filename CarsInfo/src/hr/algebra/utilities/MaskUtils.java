/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utilities;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author asim2
 */
public class MaskUtils {

    private MaskUtils() {
    }
    
    public static void addIntegerMask(TextField field) {
        UnaryOperator<TextFormatter.Change> filter =  change -> {
            String input = change.getText();
            if (input.matches("\\d*")) {
                return change;
            }
            return null;
        };
        field.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, filter));
    }
    
}
