package com.att.bdcoe.cip.geo.data.core;

import com.att.bdcoe.cip.geo.data.core.gen.PoinType;
import org.codehaus.jackson.annotate.JsonIgnore;

public class Coord {
    protected double lat;
    protected double lng;
    protected double originalLat;
    protected double originalLng;
    protected boolean routeStartOrEnd;

    private double fluctuation = 0.0;
    private PoinType poiType;

    @JsonIgnore
    private String venueCode;

    public Coord(double lat, double lng) {
        this.originalLat = this.lat = lat;
        this.originalLng = this.lng = lng;
    }

    public Coord(double lat, double lng, int poiType) {
        this.originalLat = this.lat = lat;
        this.originalLng = this.lng = lng;
        this.poiType = PoinType.values()[poiType];
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coord)) return false;

        Coord coord = (Coord) o;

        if (Double.compare(coord.lat, lat) != 0) return false;
        if (Double.compare(coord.lng, lng) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

//    public void fluctuate(Random random)
//    {
//        if(fluctuation!=0.0) {
//            double randomAngle = Math.PI*2*random.nextDouble();
//            double randomVal = random.nextDouble()+random.nextDouble() ;
//            double radius = 0.0;
//            if(randomVal>1) radius=(2-randomVal)*fluctuation;
//            else radius=randomVal*fluctuation;
//            this.lat = this.originalLat+(radius*Math.cos(randomAngle));
//            this.lng =this.originalLng+(radius*Math.sin(randomAngle));
//
//        }
//
//    }

    @Override
    public String toString() {
        return "Coord{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }


    /**
     * Calculates the distance between current and given externalRing in 2D dimension
     *
     * @param point Other point
     * @return Double value
     */
    public double distance(Coord point) {
        return Math.sqrt(Math.pow(Math.abs(point.getLng() - this.getLng()), 2) +
                Math.pow(Math.abs(point.getLat() - this.getLat()), 2));
    }

    public double getFluctuation() {
        return fluctuation;
    }

    public void setFluctuation(double fluctuation) {
        this.fluctuation = fluctuation;
    }

    public PoinType getPoiTypeEnum() {
        return poiType;
    }

    public int getPoiType() {
        return poiType.getValue();
    }

    public void setPoiType(PoinType poiType) {
        this.poiType = poiType;
    }

    public boolean isRouteStartOrEnd() {
        return routeStartOrEnd;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }
}
