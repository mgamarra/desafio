package br.com.challenge.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateUtil {

    private static final String dateMask = "dd/MM/yyyy HH:mm:ss";

    public static String dataAtual(){
        SimpleDateFormat formatter = new SimpleDateFormat(dateMask);
        Date date = new Date();
        return formatter.format(date);
    }
}
