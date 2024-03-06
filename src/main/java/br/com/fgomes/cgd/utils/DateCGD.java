package br.com.fgomes.cgd.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Descrever o objetivo da classe.
 * <p>
 * Criado por fernando.gomes em 05/03/2024.
 * Copyright (c) 2024 -  Autotrac Comércio  e Telecomunicações S/A.
 * Todos os direitos reservados.
 */
public class DateCGD {
    private static final String TAG = "DateCGD";
    private static DateCGD mInstance = null;
    private String mDateDayNow = "";
    private String mDateMonthNow = "";
    private String mDateYearNow = "";

    public DateCGD() { }

    public static DateCGD getInstance() {
        if (mInstance == null)
            mInstance = new DateCGD();
        return mInstance;
    }

    public int getQtdDaysMonth(int pMonth) {
        return switch (pMonth) {
            case 2 -> 29;
            case 4, 6, 9, 11 -> 30;
            default -> 31;
        };
    }

    public int getNextMonth(int pMonth) {
        if (pMonth >= 12)
            return (pMonth - 12) == 0 ? 1 : pMonth - 12 + 1;
        else
            return pMonth + 1;
    }

    private String getDateNow(int pType) {
        Date data = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        switch (pType) {
            case 1 -> {
                Format formatDay = new SimpleDateFormat("dd");
                return formatDay.format(c.getTime());
            }
            case 2 -> {
                Format formatMonth = new SimpleDateFormat("MM");
                return formatMonth.format(c.getTime());
            }
            case 3 -> {
                Format formatYear = new SimpleDateFormat("yyyy");
                return formatYear.format(c.getTime());
            }
            default -> { return ""; }
        }
    }

    public String getNameMonth(String pMonth) {
        return switch (pMonth) {
            case "01" -> "Janeiro";
            case "02" -> "Fevereiro";
            case "03" -> "Março";
            case "04" -> "Abril";
            case "05" -> "Maio";
            case "06" -> "Junho";
            case "07" -> "Julho";
            case "08" -> "Agosto";
            case "09" -> "Setembro";
            case "10" -> "Outubro";
            case "11" -> "Novembro";
            case "12" -> "Dezembro";
            default -> "";
        };
    }

    public String getLastMonths(int pQtdMonths) {
        List<String> months = List.of("Janeiro",
                "Fevereiro",
                "Março",
                "Abril",
                "Maio",
                "Junho",
                "Julho",
                "Agosto",
                "Setembro",
                "Outubro",
                "Novembro",
                "Dezembro");

        int mesAtualIndiceNaLista = Integer.parseInt(
                months.get( Integer.parseInt( mDateMonthNow ) - 1 ) );

        switch ( mesAtualIndiceNaLista ) {
            case 1 -> { return months.get(0); }
            case 2 -> { return months.get(1); }
            case 3 -> { return months.get(2); }
            case 4 -> { return months.get(3); }
            case 5 -> { return months.get(4); }
            case 6 -> { return months.get(5); }
            case 7 -> { return months.get(6); }
            case 8 -> { return months.get(7); }
            case 9 -> { return months.get(8); }
            case 10 -> { return months.get(9); }
            case 11 -> { return months.get(10); }
            case 12 -> { return months.get(11); }
            default -> { return ""; }
        }
    }

    public static List<Integer> getPreviousMonths(int pAmount) {
        LocalDate dataAtual = LocalDate.now();
        List<Integer> mesesAnteriores = new ArrayList<>();

        for (int i = 0; i < pAmount; i++) {
            mesesAnteriores.add(dataAtual.minusMonths(i + 1).getMonthValue());
        }

        return mesesAnteriores;
    }

    public String getDateDayNow() {
        return getDateNow(1 );
    }

    public void setDateDayNow(String dateDayNow) {
        this.mDateDayNow = dateDayNow;
    }

    public String getDateMonthNow() {
        return getDateNow(2 );
    }

    public void setDateMonthNow(String dateMonthNow) {
        this.mDateMonthNow = dateMonthNow;
    }

    public String getDateYearNow() {
        return getDateNow(3);
    }

    public void setDateYearNow(String dateYearNow) {
        this.mDateYearNow = dateYearNow;
    }
}
