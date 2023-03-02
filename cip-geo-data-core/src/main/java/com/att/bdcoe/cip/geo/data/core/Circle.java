package com.att.bdcoe.cip.geo.data.core;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Circle extends AbstractFigure {

    @JsonIgnore
    protected Coord center;

    private double radius;
    private double radiusInMeeters;

    public Circle() {
        this.radius = 0.001916654;
    }

    public Circle(double x, double y, double radius) {
        this.center = new Coord(x, y);
        this.setRadius(radius);
    }

    public Circle(Coord center, double radius) {
        this.center = center;
        this.setRadius(radius);
    }

    /**
     * Checks inclusion of the point in a Circle
     *
     * @param point Point object
     * @return True if current Circle includes the point
     */
    @Override
    public boolean contains(Coord point) {
        //1 GPS unit is 111319 meeters

        return Double.compare(Math.abs(center.distance(point)), getRadius()) <= 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Circle)) return false;

        Circle circle = (Circle) o;

        if (Double.compare(circle.getRadius(), getRadius()) != 0) return false;
        if (!center.equals(circle.center)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = center.hashCode();
        temp = Double.doubleToLongBits(getRadius());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public Coord getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;

    }

    /**
     * if line defined with 2 points is crossing circle or not. Even if it is touching circle at one point(delta == 0) it will return false
     *
     * @param start
     * @param end
     * @return
     */
    public boolean crossingLine(Coord start, Coord end) {

        if (this.contains(start) || this.contains(end)) return true;
        Coord startAdjusted = new Coord(start.getLat() - this.getCenter().getLat(), start.getLng() - this.getCenter().getLng());
        Coord endAdjusted = new Coord(end.getLat() - this.getCenter().getLat(), end.getLng() - this.getCenter().getLng());
        double dradius = Math.sqrt(Math.pow(endAdjusted.getLng() - startAdjusted.getLng(), 2)
                + Math.pow(endAdjusted.getLat() - startAdjusted.getLat(), 2));
        double D = startAdjusted.getLng() * endAdjusted.getLat() - endAdjusted.getLng() * startAdjusted.getLat();

        double delta = Math.pow(this.getRadius(), 2) * Math.pow(dradius, 2) - Math.pow(D, 2);


        if (delta <= 0)
            return false;
        else {
            if ((startAdjusted.getLat() * endAdjusted.getLat() < 0) ||
                    (startAdjusted.getLng() * endAdjusted.getLng() < 0))
                return true;
            else
                return false;
        }
    }

    public Coord pointInsideCircle(Coord start, Coord end) {
        if (this.contains(start)) return start;
        if (this.contains(end)) return end;
        if (!this.crossingLine(start, end)) return null;
        Coord mid = new Coord((start.getLat() + end.getLat()) / 2, (start.getLng() + end.getLng()) / 2);
        if (this.contains(mid)) return mid;
        else {
            Coord retVal = null;
            if (this.crossingLine(start, mid)) retVal = this.pointInsideCircle(start, mid);
            else retVal = this.pointInsideCircle(mid, end);
            return retVal;
        }
    }

    public void setRadius(double radius) {
        if (radius > 0)
            this.radius = radius;
    }
}