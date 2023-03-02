package com.att.bdcoe.cip.geo.data.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class CircleTest {

    @Test
    public void testCrossingLine() throws Exception {

        Circle circle = new Circle(0.0,0.0,1.0);

        assertFalse(circle.crossingLine(new Coord(0.0, 3.5), new Coord(0.0, 1.5)));


        assertTrue(circle.crossingLine(new Coord(0.0, 0.5), new Coord(2.0, 0.5)));
        assertTrue(circle.crossingLine(new Coord(-2.0, 0.5), new Coord(2.0, 0.5)));
        assertFalse(circle.crossingLine(new Coord(-2.0, 1.0), new Coord(2.0, 1.0)));
        assertFalse(circle.crossingLine(new Coord(-2.0, 1.5), new Coord(2.0, 1.5)));
        assertFalse(circle.crossingLine(new Coord(2.0, 1.5), new Coord(3.0, 1.5)));
    }


    @Test
    public void testPointInsideCircle() throws Exception {

        Circle circle = new Circle(0.0,0.0,1.0);
        assertNull(circle.pointInsideCircle(new Coord(0.0, 3.5), new Coord(0.0, 1.5)));
        assertNotNull(circle.pointInsideCircle(new Coord(0.0, 0.5), new Coord(2.0, 0.5)));
        assertNotNull(circle.pointInsideCircle(new Coord(-2.0, 0.5), new Coord(2.0, 0.5)));
        assertNull(circle.pointInsideCircle(new Coord(-2.0, 1.0), new Coord(2.0, 1.0)));
        assertNull(circle.pointInsideCircle(new Coord(-2.0, 1.5), new Coord(2.0, 1.5)));
        assertNull(circle.pointInsideCircle(new Coord(2.0, 1.5), new Coord(3.0, 1.5)));
    }
}