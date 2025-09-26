package oy.interact.tira.util;

public class Car {
	public Car(String registerNumber, int yearBought) {
		this.registerNumber = registerNumber;
		this.yearBought = yearBought;
	}

	String getRegisterNumber() {
		return registerNumber;
	}

	int getYearBought() {
		return yearBought;
	}
	
	@Override
	public boolean equals(Object another) {
		if (another instanceof Car) {
			return this.registerNumber.equals(((Car)another).registerNumber);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(registerNumber);
		builder.append(" ");
		builder.append(Integer.toString(yearBought));
		return builder.toString();
	}

	private String registerNumber;
	private int yearBought;
}
