package es.fer.encierros.busqueda;

import es.fer.encierros.Busqueda;
import es.fer.encierros.R;
import android.app.DialogFragment;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

public class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        int year = 0, month = 0, day = 0;
    	if (Busqueda.Output.getId() == R.id.StartDate){
    		year  = Busqueda.yearStart;
    		month = Busqueda.monthStart;
    		day   = Busqueda.dayStart;
    	}
    	else if (Busqueda.Output.getId() == R.id.EndDate){
    		year  = Busqueda.yearEnd;
    		month = Busqueda.monthEnd;
    		day   = Busqueda.dayEnd;
    	}

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    
	@Override
    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
         
    	if (Busqueda.Output.getId() == R.id.StartDate){
    		Busqueda.yearStart  = selectedYear;
    		Busqueda.monthStart = selectedMonth;
    		Busqueda.dayStart   = selectedDay;
    	}
    	else if (Busqueda.Output.getId() == R.id.EndDate){
    		Busqueda.yearEnd  = selectedYear;
    		Busqueda.monthEnd = selectedMonth;
    		Busqueda.dayEnd   = selectedDay;
    	}
        
        // Show selected date 
    	Busqueda.Output.setText(new StringBuilder().append(selectedDay)
        	.append("-").append(selectedMonth + 1).append("-").append(selectedYear)
            .append(" "));
 
    }
}

