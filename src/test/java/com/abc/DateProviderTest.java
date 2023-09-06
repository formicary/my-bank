package com.abc;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import com.abc.MainClasses.DateProvider;

//Validate class and returned millisecond value of DateProvider instance 
public class DateProviderTest {
    @Test
    public void testDateProvider() {
        DateProvider instance = new DateProvider();

        assertEquals(instance.getClass(), DateProvider.getInstance().getClass());
        assertEquals(String.valueOf(instance.now()), String.valueOf(Calendar.getInstance().getTime()));
    }
}