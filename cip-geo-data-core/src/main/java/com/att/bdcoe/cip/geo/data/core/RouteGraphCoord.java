package com.att.bdcoe.cip.geo.data.core;

import com.att.bdcoe.cip.geo.data.core.gen.PoinType;

public class RouteGraphCoord extends Coord {



	public RouteGraphCoord(double lat, double lng) {
		super(lat, lng);
	}

	public RouteGraphCoord(double lat, double lng, boolean routeStartOrEnd,PoinType poinType) {
		super(lat, lng);
		this.routeStartOrEnd = routeStartOrEnd;
        this.setPoiType(poinType);
	}
    public RouteGraphCoord(double lat, double lng, boolean routeStartOrEnd, PoinType poinType, String venueCode) {
        super(lat, lng);
        this.routeStartOrEnd = routeStartOrEnd;
        this.setPoiType(poinType);
        this.setVenueCode(venueCode);
    }


}
