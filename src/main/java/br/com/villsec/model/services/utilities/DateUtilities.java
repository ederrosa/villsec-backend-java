package br.com.villsec.model.services.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import br.com.villsec.model.services.exceptions.DataIntegrityException;

@Component
public class DateUtilities {

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public static String dateFormat(Date date) {
		return formatter.format(date);
	}

	public static Date dateFormat(String sDate) {
		try {
			return formatter.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		throw new DataIntegrityException("Data informada é invalida!");
	}
}
