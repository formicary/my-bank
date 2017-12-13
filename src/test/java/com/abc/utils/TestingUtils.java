package com.abc.utils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;

public class TestingUtils {
	public static void changePrivateField(Object object, String fieldName, Object value) throws IllegalAccessException, NoSuchFieldException {
		
		Field field = object.getClass().getDeclaredField(fieldName);
		
		if(!field.isAccessible()) {
			field.setAccessible(true);
			field.set(object, value);
			field.setAccessible(false);
		} else
			field.set(object, value);
	}
	
	public static void assertEqualsBigDecimal(BigDecimal expected, BigDecimal actual) {
		Assert.assertTrue("expected value =" + expected.toString() + "; actual value = " + actual.toString(),
				expected.compareTo(actual) == 0);
	}
	
	public static String getResourceAsString(String resourcePath, String encoding, Object caller) throws URISyntaxException, IOException {
		return new String(Files.readAllBytes(Paths.get(caller.getClass().getResource(resourcePath).toURI())), encoding);
	}
}
