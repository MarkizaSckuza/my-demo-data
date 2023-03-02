package com.att.bdcoe.cip.geo.data.core;

public class Rectangle {
	private double left;
	private double right;
	private double bottom;
	private double top;

	public Rectangle() {
	}

	public Rectangle(double left, double right, double bottom, double top) {
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.top = top;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public double getBottom() {
		return bottom;
	}

	public void setBottom(double bottom) {
		this.bottom = bottom;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}
}
