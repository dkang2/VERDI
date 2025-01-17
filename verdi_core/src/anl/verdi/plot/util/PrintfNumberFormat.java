package anl.verdi.plot.util;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class PrintfNumberFormat extends NumberFormat {
	
	Exception creation = new Exception("New PrintfNumberFormat");
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String pattern = null;
	
	public PrintfNumberFormat(String pattern) {
		this.pattern = "%" + pattern;
	}
	
    public StringBuffer format(double number,
            StringBuffer toAppendTo,
            FieldPosition pos) {
    	return toAppendTo.append(String.format(pattern,  number));
    }
	
    public StringBuffer format(long number,
            StringBuffer toAppendTo,
            FieldPosition pos) {
    	return toAppendTo.append(String.format(pattern,  number));
    }
	
    public StringBuffer format(Object number,
            StringBuffer toAppendTo,
            FieldPosition pos) {
	return toAppendTo.append(String.format(pattern,  number));	
	}
    
    public String toPattern() {
    	return pattern.substring(1);
    }

	@Override
	public Number parse(String source, ParsePosition parsePosition) {
		throw new UnsupportedOperationException();
	}

}
