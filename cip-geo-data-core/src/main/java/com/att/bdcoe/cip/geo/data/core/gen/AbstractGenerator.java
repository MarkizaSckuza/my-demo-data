package com.att.bdcoe.cip.geo.data.core.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.Route;
import com.att.bdcoe.cip.geo.data.core.TrackCoord;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public abstract class AbstractGenerator<TId, TRCoord extends Coord, TTCoord extends TrackCoord>
		implements Generator<TId, TRCoord, TTCoord> {

	private Log log = LogFactory.getLog(getClass());

	protected List<Route<TRCoord>> routes;
	protected TrackBuilder<TId, TTCoord> trackBuilder;
    private BehaviourManager behaviourManager;

	protected Random random = new Random();

	double earthRadius = 6378137d;
	double degreesToRadials = (Math.PI / 180d);
	double radiansToDegrees = (180d / Math.PI);

	protected AbstractGenerator(List<Route<TRCoord>> routes,  TrackBuilder<TId, TTCoord> trackBuilder,BehaviourManager behaviourManager) {

		if (routes == null) throw new IllegalArgumentException("List of routes cannot be null");
		if (trackBuilder == null) throw new IllegalArgumentException("Track builder cannot be null");
        if (behaviourManager == null) throw new IllegalArgumentException("Behaviour Manager cannot be null");

		this.routes = routes;
		this.trackBuilder = trackBuilder;
        this.behaviourManager = behaviourManager;
	}

	protected List<TTCoord> generateTrackCoords(Date startTimestamp, double trackIntervalSeconds, double speedMps, Iterator<Coord> pathPoints) {
		ArrayList<TTCoord> trackPoints = new ArrayList<>();

		if (pathPoints == null) return trackPoints;
		if (!pathPoints.hasNext()) return trackPoints;

		Coord startPathCoord = pathPoints.next();
		if (!pathPoints.hasNext()) return trackPoints;

		Calendar timestamp = Calendar.getInstance();
		timestamp.setTime(startTimestamp);

		while (pathPoints.hasNext()) {
			Coord nextPathCoord = pathPoints.next();
			generateInterimCoords(timestamp, trackIntervalSeconds, speedMps, startPathCoord, nextPathCoord, trackPoints);
			startPathCoord = nextPathCoord;
		}

		return trackPoints;
	}

	// TODO: take in account prevDistanceToEnd on route turns
	protected void generateInterimCoords(
			Calendar timestamp,
			double trackIntervalSeconds,
			double speedMps,
			Coord startCoord,
			Coord endCoord,
			ArrayList<TTCoord> trackCoords)
	{
        if(startCoord.getPoiTypeEnum()!=PoinType.None
                &&startCoord.getPoiTypeEnum()!=PoinType.Begin){
            Behaviour behavior = behaviourManager.get(startCoord.getPoiTypeEnum(),timestamp.getTime(),startCoord,trackIntervalSeconds, (TrackBuilder<String, TrackCoord>) trackBuilder);
            if(behavior!=null)timestamp.setTime(behavior.behave((List<TrackCoord>) trackCoords));
        }

		double bearing = bearing(startCoord, endCoord);
		int trackIntervalMillis = (int)trackIntervalSeconds * 1000;

		Coord prevCoord = startCoord;
		double prevDistanceToEnd = distanceInMeters(prevCoord, endCoord);

		timestamp.add(Calendar.MILLISECOND, (int)getRandomDeviation(trackIntervalMillis, 0.1d));
		TTCoord startTrackCoord = trackBuilder.buildTrackCoord(timestamp.getTime(), startCoord, CoordType.Turn);
		trackCoords.add(startTrackCoord);

		while(true)
		{



            double intervalDistance = getRandomDeviation(speedMps) * trackIntervalSeconds;
			Coord interimCoord = getDestination(prevCoord, bearing, intervalDistance);

			double distanceToEnd = distanceInMeters(interimCoord, endCoord);

			if(distanceToEnd > prevDistanceToEnd) break;

			timestamp.add(Calendar.MILLISECOND, (int)getRandomDeviation(trackIntervalMillis, 0.1d));

			TTCoord trackCoord = trackBuilder.buildTrackCoord(timestamp.getTime(), interimCoord, CoordType.None);
			trackCoords.add(trackCoord);

			prevCoord = interimCoord;
			prevDistanceToEnd = distanceToEnd;
		}
        if(endCoord.getPoiTypeEnum()!=PoinType.None
                &&endCoord.getPoiTypeEnum()!=PoinType.Begin
                &&endCoord.isRouteStartOrEnd()){
            Behaviour behavior = behaviourManager.get(endCoord.getPoiTypeEnum(),timestamp.getTime(),endCoord,trackIntervalSeconds, (TrackBuilder<String, TrackCoord>) trackBuilder);
            if(behavior!=null)timestamp.setTime(behavior.behave((List<TrackCoord>) trackCoords));
        }

	}

	protected double distanceInMeters(Coord start, Coord end) {
		return distanceInMeters(start.getLat(), start.getLng(), end.getLat(), end.getLng());
	}

	protected double distanceInMeters(double startLat, double startLng, double endLat, double endLng) {
		double diffLng = (endLng - startLng) * degreesToRadials;
		double diffLat = (endLat - startLat) * degreesToRadials;

		double a =
				Math.pow(Math.sin(diffLat / 2d), 2d) +
				Math.cos(startLat * degreesToRadials) *
				Math.cos(endLat * degreesToRadials) *
				Math.pow(Math.sin(diffLng / 2d), 2d);

		double c = 2d * Math.atan2(Math.sqrt(a), Math.sqrt(1d - a));

		double d = earthRadius * c;

		return d;

	}

	protected double bearing(Coord start, Coord end) {
		return bearing(start.getLat(), start.getLng(), end.getLat(), end.getLng());
	}

	protected double bearing(double startLat, double startLng, double endLat, double endLng){
		double diffLng = (endLng - startLng) * degreesToRadials;
		double startLatRad = startLat * degreesToRadials;
		double endLatRad = endLat * degreesToRadials;

		double y = Math.sin(diffLng) * Math.cos(endLatRad);

		double x = Math.cos(startLatRad) * Math.sin(endLatRad) -
				Math.sin(startLatRad) * Math.cos(endLatRad) * Math.cos(diffLng);

		double bearing = Math.atan2(y, x) * radiansToDegrees;

		return bearing;
	}

	protected Coord getDestination(Coord coord, double bearing, double distanceInMeters) {
		return getDestination(coord.getLat(), coord.getLng(), bearing, distanceInMeters);
	}

	protected Coord getDestination(double startLat, double startLng, double bearing, double distanceInMeters) {
		double bearingRad = bearing * degreesToRadials;
		double startLatRad = startLat * degreesToRadials;
		double startLngRad = startLng * degreesToRadials;

		double angularDistance = distanceInMeters / earthRadius;
		double lat = Math.asin(
				Math.sin(startLatRad) * Math.cos(angularDistance) +
				Math.cos(startLatRad) * Math.sin(angularDistance) * Math.cos(bearingRad)
		);
		double lng = startLngRad +
				Math.atan2(
						Math.sin(bearingRad) * Math.sin(angularDistance) * Math.cos(startLatRad),
						Math.cos(angularDistance) - Math.sin(startLngRad) * Math.sin(lat)
				);

		return new Coord(lat * radiansToDegrees, lng * radiansToDegrees);
	}

	protected double getRandomDeviation(double value) {
		return getRandomDeviation(value, 0.25);
	}

	protected double getRandomDeviation(double value, double deviationPercentage) {
		double percentageValue = value * deviationPercentage;
		double deviation = random.nextDouble() * (percentageValue * 2) - percentageValue;
		return value + deviation;
	}
}
