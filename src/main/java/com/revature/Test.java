package com.revature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.revature.reflection.object.Confection;

public class Test {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchMethodException {
		Class myClass = Confection.class;
		Object object = myClass.newInstance();
		Method method = myClass.getMethod("setHasFrosting", Boolean.class);
		method.invoke(object, true);
		Confection confection = (Confection) object;
		System.out.println(confection);
	}
}
