package oy.interact.tira.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import oy.interact.tira.student.Person;

public class PersonUtils {

	private static List<String> firstNames = null;
	private static List<String> lastNames = null;

	public static Person [] createPersons(final int count) throws IOException {
		if (count < 1) {
			throw new IllegalArgumentException("Count must be 1 or more");
		}
		if (firstNames == null || lastNames == null) {
			firstNames = new ArrayList<>();
			lastNames = new ArrayList<>();
			loadNames(firstNames, lastNames);
		}
		int counter = 0;
		Person [] persons = new Person[count];
		while (counter < count) {
			persons[counter] = new Person(
				firstNames.get(ThreadLocalRandom.current().nextInt(firstNames.size())),
				lastNames.get(ThreadLocalRandom.current().nextInt(lastNames.size())),
				firstNames.get(ThreadLocalRandom.current().nextInt(firstNames.size())),
				ThreadLocalRandom.current().nextInt(150)
			);
			counter++;
		}
		return persons;
	}

	public static boolean isInOrder(final Person [] persons, Comparator<Person> order) {
		for (int index = 1; index < persons.length; index++) {
			if (persons[index-1] == null || persons[index] == null) {return false;}
			if (order.compare(persons[index-1], persons[index]) > 0) {
				return false;
			}
		}
		return true;
	}

	private static void loadNames(List<String> firstNames, List<String> lastNames) throws IOException {
		String allFirstNames = new String(PersonUtils.class.getClassLoader().getResourceAsStream("random-firstnames.txt").readAllBytes());
		String [] names = allFirstNames.split("\\s*,\\s*");
		firstNames.addAll(Arrays.asList(names));

		String allLastNames = new String(PersonUtils.class.getClassLoader().getResourceAsStream("finnish-surnames.txt").readAllBytes());
		String [] names2 = allLastNames.split("\\s*,\\s*");
		lastNames.addAll(Arrays.asList(names2));
	}

}
