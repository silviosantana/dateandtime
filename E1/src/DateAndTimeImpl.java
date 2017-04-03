import java.util.*;
import java.text.*;

public class DateAndTimeImpl implements IDateAndTime {
	private SimpleDateFormat ft;
	
	public int time (String p1){
		Date dNow = new Date( );
		int result = -1;

		if(p1.equals("hour")){
			ft = new SimpleDateFormat ("H");
        	result = Integer.parseInt(ft.format(dNow));
		}
		else if(p1.equals("min")){
			ft = new SimpleDateFormat ("m");
        	result = Integer.parseInt(ft.format(dNow));
		}
		else if(p1.equals("sec")){
			ft = new SimpleDateFormat ("s");
        	result = Integer.parseInt(ft.format(dNow));
		}
		return result;
	}
	
	public String date (String p1){
		Date dNow = new Date( );
		String result = "Invalid";

		if(p1.equals("year")){
			ft = new SimpleDateFormat ("yyyy");
        	result = ft.format(dNow);
		}
		else if(p1.equals("mon")){
			ft = new SimpleDateFormat ("M");
			result = ft.format(dNow);
		}
		else if(p1.equals("day")){
			ft = new SimpleDateFormat ("d");
			result = ft.format(dNow);
		}
		return result;
	}
}
