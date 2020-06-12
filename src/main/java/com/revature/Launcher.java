package com.revature;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Launcher {
	static Scanner scanner = new Scanner(System.in);

	static Map<Class, Object> defaultValues = new HashMap<>();
	static {
		defaultValues.put(Integer.class, 100);
		defaultValues.put(String.class, "My String");
		defaultValues.put(Double.class, 22.55);
		defaultValues.put(Float.class, 1.234f);
		defaultValues.put(Byte.class, 22);
		defaultValues.put(Short.class, 5555);
		defaultValues.put(Long.class, 1234789);
		defaultValues.put(Boolean.class, true);
		defaultValues.put(Character.class, 'a');
	}

	public static Class findClass() throws InterruptedException {
		Class myClass = null;

		do {
			String className = scanner.nextLine();
			Thread.sleep(1000);
			try {
				myClass = Class.forName("com.revature.reflection.object." + className);
			} catch (ClassNotFoundException e) {
				System.out.println("Class with this name not found. Try again.");
			}
		} while (myClass == null);
		return myClass;
	}

	public static void outputClassFields(Class myClass) throws InterruptedException {
		System.out.println(myClass.getSimpleName() + " has the following fields:");
		Thread.sleep(1000);
		int index = 0;
		for (Field field : myClass.getDeclaredFields()) {
			Thread.sleep(1000);
			System.out.printf("(%d) %s (%s)%n", index, field.getName(), field.getType());
			index++;
		}
	}

	public static void outputClassMethods(Class myClass) throws InterruptedException {
		System.out.println("\n" + myClass.getSimpleName() + " has the following methods:");
		int index = 0;
		for (Method m : myClass.getDeclaredMethods()) {
			Thread.sleep(1000);
			System.out.printf("(%d) %s %s", index, m.getReturnType().getSimpleName(), m.getName());
			System.out.print("(");

			String result = Arrays.asList(m.getParameters()).stream()
					.map(a -> a.getType().getSimpleName() + " " + a.getName())
					.reduce("", (a, b) -> String.join(",", a, b));

			System.out.println(result + ")");
			index++;
		}
	}

	public static void outputClassDetails(Class myClass) throws InterruptedException {
		Thread.sleep(1000);
		System.out.println("Selected class is: " + myClass.getName());
		outputClassFields(myClass);
		outputClassMethods(myClass);
	}

	public static void printConstructors(Constructor[] cList) throws InterruptedException {
		int index = 0;
		for (Constructor c : cList) {
			Thread.sleep(1000);
			System.out.printf("(%d) %s(", index, c.getName());
			String params = Arrays.asList(c.getParameters()).stream()
					.map(p -> p.getParameterizedType() + " " + p.getName())
					.reduce("", (a, b) -> String.join(", ", a, b));
			System.out.printf("(%s)%n", params);
		}
	}

	public static Object getUserSelection(Object[] arr) {
		String value = scanner.nextLine();
		Integer index = null;

		while (index == null || index >= arr.length) {
			try {
				index = Integer.valueOf(value);
			} catch (NumberFormatException e) {
				index = null;
			}
		}
		return arr[index];
	}

	public static Object genericParse(Class requestedClass) {
		for (;;) {
			String line = scanner.nextLine();
			if (requestedClass == String.class)
				return line;
			if (requestedClass == Integer.class) {
				try {
					return Integer.parseInt(line);
				} catch (NumberFormatException e) {
				}
			}
			if (requestedClass == Double.class) {
				try {
					return Double.parseDouble(line);
				} catch (NumberFormatException e) {
				}
			}
			if (requestedClass.getName().equals("boolean") || requestedClass == Boolean.class) {
				return Boolean.parseBoolean(line);
			}

		}
	}

	public static Object[] getParamsFromUser(Parameter[] params) throws InterruptedException {
		Object[] paramValues = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			Thread.sleep(1000);
			System.out.println("Need parameter value for: " + params[i].getType() + " " + params[i].getName());
			paramValues[i] = genericParse(params[i].getType());
		}
		return paramValues;
	}

	public static Object createInstance(Class myClass) throws InterruptedException {
		System.out.println("I'll create one!");
		Thread.sleep(1000);
		Object o;
		try {
			System.out.println("We'll try the no args constructor first.");
			o = myClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			o = null;
		}
		Thread.sleep(1000);
		if (o != null) {
			System.out.println("Great, that worked!");
			return o;
		}

		for (;;) {
			try {
				Constructor[] constructors = myClass.getConstructors();
				printConstructors(constructors);
				Constructor constructor = (Constructor) getUserSelection(constructors);
				Object[] params = getParamsFromUser(constructor.getParameters());
				Object object = constructor.newInstance(params);
				return object;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				System.out.println("Oh no, something went wrong.");
			}
		}

	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InterruptedException {
		Class myClass = findClass();
		outputClassDetails(myClass);
		Object o = createInstance(myClass);
		Method[] methods = myClass.getDeclaredMethods();
		methodLoop: for (Method m : methods) {
			Thread.sleep(1000);
			System.out.println("Attempting to execute method: " + m.getName());
			Object[] params = new Object[m.getParameterCount()];
			for (int i = 0; i < params.length; i++) {
				if (defaultValues.containsKey(m.getParameterTypes()[i])) {
					params[i] = defaultValues.get(m.getParameterTypes()[i]);
				} else {
					System.out.println("I don't know this type. We'll skip this one.");
					System.out.println("--------------");
					Thread.sleep(1000);
					continue methodLoop;
				}
			}
			String paramString = "";
			for (Object p : params) {
				paramString += p;
			}
			System.out.println("Calling with arguments: " + paramString);
			Thread.sleep(1000);
			System.out.printf("%s output is: %s%n", m.getName(), m.invoke(o, params));
			Thread.sleep(1000);
			System.out.println("--------------");
		}

		System.out.println("Done!");
	}
}