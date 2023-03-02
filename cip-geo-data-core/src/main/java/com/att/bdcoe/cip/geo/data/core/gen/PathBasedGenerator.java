package com.att.bdcoe.cip.geo.data.core.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.Route;
import com.att.bdcoe.cip.geo.data.core.Track;
import com.att.bdcoe.cip.geo.data.core.TrackCoord;
import com.att.bdcoe.cip.geo.data.core.util.ReverseIterator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PathBasedGenerator<TId, TRCoord extends Coord, TTCoord extends TrackCoord>
		extends AbstractGenerator<TId, TRCoord, TTCoord> {

	public PathBasedGenerator(List<Route<TRCoord>> routes, TrackBuilder<TId, TTCoord> trackBuilder, BehaviourManager behaviourManager) {
		super(routes, trackBuilder,behaviourManager);
	}

	public Track<TId, TTCoord> generate(TId id, Date startTimestamp, double trackIntervalSeconds, double speedMps) {

		if (routes.size() <= 0) return Track.empty();

		int routeIndex = random.nextInt(routes.size());
		Route route = routes.get(routeIndex);

		boolean reverse = random.nextBoolean();

		Iterator<Coord> routePointsIterator = reverse ?
				new ReverseIterator<Coord>(route.getPoints()) :
				(Iterator<Coord>)route.getPoints().iterator();

		List<TTCoord> trackPoints = generateTrackCoords(startTimestamp, trackIntervalSeconds, speedMps, routePointsIterator);
		Track<TId, TTCoord> track = trackBuilder.buildTrack(id, trackPoints);

		return track;
	}
}
