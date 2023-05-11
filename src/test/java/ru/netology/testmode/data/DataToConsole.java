package ru.netology.testmode.data;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.BLACK_BACK;
import static com.diogonunes.jcolor.Attribute.BOLD;

public class DataToConsole {
    public static void printToConsole(String textToPrint){
        // вывод текста в консоль
        AnsiFormat greenText = new AnsiFormat
                (
                        Attribute.GREEN_TEXT(),
                        BLACK_BACK(),
                        BOLD()
                );
        System.out.println(colorize((textToPrint), greenText));
    }
}
