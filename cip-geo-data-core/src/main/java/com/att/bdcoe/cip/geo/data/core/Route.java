package com.att.bdcoe.cip.geo.data.core;

import java.util.List;

public class Route<T extends Coord> {
	protected int id;
	protected List<T> points;

	public Route(int id, List<T> points) {
		this.id = id;
		this.points = points;
	}

	public int getId() {
		return id;
	}

	public List<T> getPoints() {
		return points;
	}
}
