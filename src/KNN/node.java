package KNN;

public class node {

	private double x;
	private double y;;
	static double max=99999;

	node(double x,double y){
		this.x=x;
		this.y=y;
	}

	public double getx() {
		return this.x;
	}

	public double gety() {
		return this.y;
	}

	public String toString() {
		return this.y+","+this.x;
	}

}
