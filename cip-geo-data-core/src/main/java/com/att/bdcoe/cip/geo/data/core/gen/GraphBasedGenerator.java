package com.att.bdcoe.cip.geo.data.core.gen;

import com.att.bdcoe.cip.geo.data.core.*;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class GraphBasedGenerator<TId, TRCoord extends Coord, TTCoord extends TrackCoord>
		extends AbstractGenerator<TId, TRCoord, TTCoord> {

	private RouteGraph<TRCoord> routeGraph;

	public GraphBasedGenerator(List<Route<TRCoord>> routes, TrackBuilder<TId, TTCoord> trackBuilder,BehaviourManager behaviourManager) {
		super(routes, trackBuilder,behaviourManager);
		this.routeGraph = new RouteGraph<>(routes);
	}

	@Override
	public Track<TId, TTCoord> generate(TId id, Date startTimestamp, double trackIntervalSeconds, double speedMps) {
		if (routeGraph.isEmpty()) return Track.empty();

		Iterator<Coord> coordIterator = routeGraph.iterator();

		if (!coordIterator.hasNext()) return Track.empty();

		List<TTCoord> trackPoints = generateTrackCoords(startTimestamp, trackIntervalSeconds, speedMps, coordIterator);
		Track<TId, TTCoord> track = trackBuilder.buildTrack(id, trackPoints);

		return track;
	}
}

