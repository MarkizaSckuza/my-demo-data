package com.att.bdcoe.cip.geo.data.core.util;

public class DataUtil {
	public static double milesToKilometers(double miles)
	{
		return miles * 1.609344;
	}

	public static double kilometersToMiles(double kilometers)
	{
		return kilometers * 0.621371192;
	}

	public static double metersPerSecondToKilometersPerHour(double metersPerSecond)
	{
		return metersPerSecond * 3600 / 1000;
	}

	public static double kilometersPerHourToMetersPerSecond(double kilometersPerHour)
	{
		return kilometersPerHour * 1000 / 3600;
	}
}
