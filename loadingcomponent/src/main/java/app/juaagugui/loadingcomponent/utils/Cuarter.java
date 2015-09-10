package app.juaagugui.loadingcomponent.utils;

public class Cuarter<T1, T2, T3, T4> {
	private T1 first; // first member of pair
	private T2 second; // second member of pair
	private T3 third;
	private T4 fourth;

	public Cuarter(T1 first, T2 second, T3 third, T4 fourth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
	}

	public void setFirst(T1 first) {
		this.first = first;
	}

	public void setSecond(T2 second) {
		this.second = second;
	}

	public T1 getFirst() {
		return first;
	}

	public T2 getSecond() {
		return second;
	}

	public T3 getThird() {
		return third;
	}

	public void setThird(T3 third) {
		this.third = third;
	}

	public T4 getFourth() {
		return fourth;
	}

	public void setFourth(T4 fourth) {
		this.fourth = fourth;
	}

	public String toString() {
		return "(" + getFirst() + "," + getSecond() + "," + getThird() + "," + getFourth() + ")";
	}

}