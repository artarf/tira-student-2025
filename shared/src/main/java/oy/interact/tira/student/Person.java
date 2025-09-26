package oy.interact.tira.student;

import java.util.UUID;

/**
 * Class person with names and identity
 * 
 * NOTE: Java docs on Comparable note that: 
 * 
 *   It is strongly recommended, but not strictly required that 
 *   (x.compareTo(y)==0) == (x.equals(y)). Generally speaking, any 
 *   class that implements the Comparable interface and violates this 
 *   condition should clearly indicate this fact. 
 *   The recommended language is "Note: this class has a natural ordering
 *    that is inconsistent with equals."
 * 
 * NB: In this class Person, this class HAS a natural ordering
 *    that is inconsistent with equals.
 * 
 * Also note (when implementing Hash table) Java docs on hashCode():
 * 
 *   * Whenever it [hashCode()] is invoked on the same object more than once 
 *     during an execution of a Java application, the hashCode method must consistently
 *     return the same integer, provided no information used in equals comparisons on 
 *     the object is modified. 
 *     This integer need not remain consistent from one execution of an application to 
 *     another execution of the same application.
 *   * If two objects are equal according to the equals method, then calling the hashCode 
 *     method on each of the two objects must produce the same integer result.
 *   * It is not required that if two objects are unequal according to the equals method, 
 *     then calling the hashCode method on each of the two objects must produce distinct 
 *     integer results. However, the programmer should be aware that producing distinct 
 *     integer results for unequal objects may improve the performance of hash tables.
 * 
 * @see https://docs.oracle.com/javase/8/docs/api/java/lang/Comparable.html
 * @see https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#hashCode()
 */
public class Person implements Comparable<Person> {

	private String id;
	private String lastName;
	private String firstName;
	private String middleName;

	private int age;		// 0...

	public Person(final String firstName, final String lastName, final String middleName, final int age) {
		if (firstName == null || lastName == null || middleName == null) {
			throw new IllegalArgumentException("Person must have names");
		}
		if (age < 0) {
			throw new IllegalArgumentException("Person's age must be >= 0");
		}
		id = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;		
		this.age = age;
	}

	public Person(final Person another) {
		if (another == null) {
			throw new IllegalArgumentException("Person to copy must not be null");
		}
		id = another.id;
		this.firstName = another.firstName;
		this.middleName = another.middleName;
		this.lastName = another.lastName;
		this.age = another.age;
	}

	public String getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("Person must have last name");
		}
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("Person must have first name");
		}
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		if (middleName == null) {
			throw new IllegalArgumentException("Person must have middle name");
		}
		this.middleName = middleName;
	}

	// NB: DO NOT use this method in equals() nor compareTo!
	// With large data sets, a new string is always allocated
	// if you do that, which will be bad for time and memory complexity.
	// Instead, just compare the member variables one at a time.
	public String getFullName() {
		return String.format("%s, %s %s", lastName, firstName, middleName);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age < 0) {
			throw new IllegalArgumentException("Person's age must be >= 0");
		}
		this.age = age;
	}

	// STUDENTS TODO implement equals
	/**
	 * Compares if two persons are equal. Returns true only if
	 * - parameter another is an instance of Person
	 * - persons' ids match.
	 * 
	 * Comparing id's for equalness allows different persons
	 * with different ids to have the same name, as in real life.
	 * 
	 * NB: See also the class docs above for further insight.
	 * Since this class HAS a natural ordering
    * that is inconsistent with equals.
	 * 
	 * @param another An object to compare this person to.
	 * @return true if another is a person with same id than this.
	 */
	@Override
	public boolean equals(Object another) {
		return false;
	}

	// STUDENTS TODO implement compareTo
	/**
	 * Compares two persons for ordering.
	 * 
	 * Order is determined by comparing the names so that 
	 * 1. last name is compared first, and if there is no difference
	 * 2. then first name, and if there is no difference
	 * 3. then middle names are compared.
	 * 
	 * NB: See also the class docs above for further insight.
	 * Since this class HAS a natural ordering
    * that is inconsistent with equals.
	 * 
	 * @param another The person to compare this person to.
	 * @throws IllegalArgumentException If parameter another is null.
	 * @return < 0 if this comes before another, > 0 if this comes after another, 0 if the persons are equal in order.
	 */
	@Override
	public int compareTo(Person another) {
		return Integer.MIN_VALUE;
	}

	@Override
	public String toString() {
		return String.format("%s, %s %s, age: %d", lastName, firstName, middleName, age);
	}

	// STUDENTS TODO calculate hash for person 
	// in hashtable task.
	/**
	 * Calculates a hash for the Person.
	 * 
	 * NB: See also the class docs above for further insight.
	 * Since this class HAS a natural ordering
    * that is inconsistent with equals.
	 *
	 * The hash must be calculated from the same member variable(s)
	 * used in the implementation of equals(). In the case
	 * of this class, that member variable is Person.id.
	 * 
	 * @return Returns the hash for the person.
	 */
	@Override
	public int hashCode() {
		return 0;
	}

}
