package dev.jeffersonfreitas.caixaki.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils implements Serializable{
	private static final long serialVersionUID = 1L;

	public static String nowDateReportFormat() {
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		return df.format(Calendar.getInstance().getTime());
	}
}
