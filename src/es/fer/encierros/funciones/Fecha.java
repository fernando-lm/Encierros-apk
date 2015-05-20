package es.fer.encierros.funciones;

import java.util.Calendar;
import java.util.Date;

public class Fecha {
	String sFecha; //2013-06-16T09:30:00+02:00
	
	public Fecha(String fecha){
		this.sFecha = fecha;
	}
	
	public Date ToDate(){		
		String [] cadenas = sFecha.split("[\\-\\-T::+:]");
		
		Calendar cld = Calendar.getInstance();
		cld.set(Integer.parseInt(cadenas[0]), 
				Integer.parseInt(cadenas[1])-1, 
				Integer.parseInt(cadenas[2]), 
				Integer.parseInt(cadenas[3]), 
				Integer.parseInt(cadenas[4]),
				(int)Double.parseDouble(cadenas[5]));
		
		Date result = cld.getTime();
		
		return result;		
	}
	
}
