package com.revature.reflection.object;

public class Confection {

	private boolean hasFrosting;
	private double carbohydratesGrams;
	private int calories;
	private boolean glutenFree;
	private String type;

	public boolean isHasFrosting() {
		return hasFrosting;
	}

	public void setHasFrosting(Boolean hasFrosting) {
		this.hasFrosting = hasFrosting;
	}

	public double getCarbohydratesGrams() {
		return carbohydratesGrams;
	}

	public void setCarbohydratesGrams(double carbohydratesGrams) {
		this.carbohydratesGrams = carbohydratesGrams;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

	public boolean isGlutenFree() {
		return glutenFree;
	}

	public void setGlutenFree(boolean glutenFree) {
		this.glutenFree = glutenFree;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + calories;
		long temp;
		temp = Double.doubleToLongBits(carbohydratesGrams);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (glutenFree ? 1231 : 1237);
		result = prime * result + (hasFrosting ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Confection other = (Confection) obj;
		if (calories != other.calories)
			return false;
		if (Double.doubleToLongBits(carbohydratesGrams) != Double.doubleToLongBits(other.carbohydratesGrams))
			return false;
		if (glutenFree != other.glutenFree)
			return false;
		if (hasFrosting != other.hasFrosting)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Confection [hasFrosting=" + hasFrosting + ", carbohydratesGrams=" + carbohydratesGrams + ", calories="
				+ calories + ", glutenFree=" + glutenFree + ", type=" + type + "]";
	}

	public Confection(boolean hasFrosting, double carbohydratesGrams, int calories, boolean glutenFree, String type) {
		super();
		this.hasFrosting = hasFrosting;
		this.carbohydratesGrams = carbohydratesGrams;
		this.calories = calories;
		this.glutenFree = glutenFree;
		this.type = type;
	}

	public Confection() {
		super();
	}

}
