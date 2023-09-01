package com.abc;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.Calendar;

public class DateProviderTest {
    @Test
    public void DateProvider() {
        DateProvider instance = new DateProvider();

        assertEquals(instance.getClass(), DateProvider.getInstance().getClass());
        assertEquals(String.valueOf(Calendar.getInstance().getTime()), String.valueOf(instance.now()));
    }
}