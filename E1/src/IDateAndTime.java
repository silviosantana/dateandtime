
public interface IDateAndTime {
	
	//parameter can be: 'hour', 'min' or 'sec'
	int time (String p1);
	
	//parameter can be: 'day', 'mon' or 'year'
	String date (String p1);
}
