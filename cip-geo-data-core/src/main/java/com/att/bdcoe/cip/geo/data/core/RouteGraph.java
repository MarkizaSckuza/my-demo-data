package com.att.bdcoe.cip.geo.data.core;

import com.att.bdcoe.cip.geo.data.core.gen.PoinType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class RouteGraph<T extends Coord> implements Iterable<Coord> {

	private Log log = LogFactory.getLog(RouteGraph.class);

	List<RouteGraphCoord> border = new ArrayList<>();
	Set<RouteGraphCoord> borderSet = new HashSet<>();
	Map<RouteGraphCoord, List<RouteGraphCoord>> vertices = new HashMap<>();

	public RouteGraph(List<Route<T>> routes) {
		if(routes == null) throw new IllegalArgumentException("Route list cannot be null");

		fillGraphWithRoutes(routes);
		detectAndAddCrossroads();
	}

	public boolean isEmpty() {
		return vertices.isEmpty();
	}

//	public RouteGraphCoord getStartCoord() {
//		int startCoordIndex = random.nextInt(border.size());
//		return border.get(startCoordIndex);
//	}




	private void fillGraphWithRoutes(List<Route<T>> routes) {
		for(Route<T> route : routes) {
			List<T> routeCoords = route.getPoints();

			T origin = routeCoords.get(0);

			for (int i = 1; i < routeCoords.size(); i++) {
				T destination = routeCoords.get(i);


				RouteGraphCoord graphOrigin = new RouteGraphCoord(origin.getLat(), origin.getLng(), i == 1,origin.getPoiTypeEnum(),origin.getVenueCode());
				RouteGraphCoord graphDestination = new RouteGraphCoord(destination.getLat(), destination.getLng(), i == routeCoords.size() - 1,destination.getPoiTypeEnum(),destination.getVenueCode());

				List<RouteGraphCoord> originEdges = vertices.get(origin);
				if (originEdges == null) {
					originEdges = new ArrayList<>();
					vertices.put(graphOrigin, originEdges);
				}
				originEdges.add(graphDestination);

				List<RouteGraphCoord> destinationEdges = vertices.get(destination);
				if (destinationEdges == null) {
					destinationEdges = new ArrayList<>();
					vertices.put(graphDestination, destinationEdges);
				}
				destinationEdges.add(graphOrigin);

				if (graphOrigin.isRouteStartOrEnd()) {
					border.add(graphOrigin);
					borderSet.add(graphOrigin);
				}

				if (graphDestination.isRouteStartOrEnd()) {
					border.add(graphDestination);
					borderSet.add(graphDestination);
				}

				origin = destination;
			}
		}
	}

	private void detectAndAddCrossroads() {
		int crossroadsCount = 0;
		while(tryDetectAndAddCrossroad()){
			crossroadsCount++;
		}
		log.debug("Found " + crossroadsCount + " crossroads in the list of routes");
	}

	// TODO: move from brute-force algorithm to more efficient (consider preliminary vertices sorting)
	private boolean tryDetectAndAddCrossroad() {

		Set<RouteGraphCoord> verticesSet = new HashSet<>(vertices.keySet());

		// Find intersections between each pair of edges
		for (RouteGraphCoord currentCoord : verticesSet){
			List<RouteGraphCoord> currentEdges = vertices.get(currentCoord);

			for (RouteGraphCoord currentEdgeDestination : currentEdges) {

				for (RouteGraphCoord otherCoord : verticesSet) {
					if (otherCoord.equals(currentCoord) || otherCoord.equals(currentEdgeDestination))
						continue;

					List<RouteGraphCoord> otherEdges = vertices.get(otherCoord);

					for (RouteGraphCoord otherEdgeDestination : otherEdges) {
						if (otherEdgeDestination.equals(currentCoord) || otherEdgeDestination.equals(currentEdgeDestination))
							continue;

						Coord intersection = findIntersection(currentCoord, currentEdgeDestination, otherCoord, otherEdgeDestination);
						if(intersection == null) continue;

						// Add intersection vertex to the graph
						RouteGraphCoord intersectionCoord = new RouteGraphCoord(intersection.getLat(), intersection.getLng());
						ArrayList<RouteGraphCoord> intersectionDestinations = new ArrayList<>(
								Arrays.asList(currentCoord, currentEdgeDestination, otherCoord, otherEdgeDestination));
						vertices.put(intersectionCoord, intersectionDestinations);

						// Update destinations for current edge vertices
						vertices.get(currentCoord).remove(currentEdgeDestination);
						vertices.get(currentCoord).add(intersectionCoord);

						vertices.get(currentEdgeDestination).remove(currentCoord);
						vertices.get(currentEdgeDestination).add(intersectionCoord);

						vertices.get(otherCoord).remove(otherEdgeDestination);
						vertices.get(otherCoord).add(intersectionCoord);

						vertices.get(otherEdgeDestination).remove(otherCoord);
						vertices.get(otherEdgeDestination).add(intersectionCoord);

						return true;
					}
				}
			}
		}
		return false;
	}

	// TODO: make intersection detection in Cartesian plane 
	private Coord findIntersection(Coord edgeStart1, Coord edgeEnd1, Coord edgeStart2, Coord edgeEnd2) {

		double p0_x = edgeStart1.getLng();
		double p0_y = edgeStart1.getLat();
		double p1_x = edgeEnd1.getLng();
		double p1_y = edgeEnd1.getLat();
		double p2_x = edgeStart2.getLng();
		double p2_y = edgeStart2.getLat();
		double p3_x = edgeEnd2.getLng();
		double p3_y = edgeEnd2.getLat();

		double s1_x, s1_y, s2_x, s2_y;

		s1_x = p1_x - p0_x;     s1_y = p1_y - p0_y;
		s2_x = p3_x - p2_x;     s2_y = p3_y - p2_y;

		double s, t;
		s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
		t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
		{
			// Collision detected
			double lng = p0_x + (t * s1_x);
			double lat = p0_y + (t * s1_y);
			return new Coord(lat, lng);
		}

		return null;
	}

	private class RouteGraphIterator implements Iterator<Coord> {

		Random random = new Random();
        List<RouteGraphCoord> startCoords = null;
		RouteGraphCoord currentCoord;
		RouteGraphCoord startCoord;
		List<RouteGraphCoord> availableDestinations;
		Set<RouteGraphCoord> visitedCoords = new HashSet<>();

		@Override
		public boolean hasNext() {
			if (isEmpty()) return false;

			// Can move from start
			if (currentCoord == null)
				return true;

			// Reached the border
			if (borderSet.contains(currentCoord) && !currentCoord.equals(startCoord)) return false;

			// Get rid of visited coordinates
			List<RouteGraphCoord> possibleDestinations = vertices.get(currentCoord);
			availableDestinations = new ArrayList<>();

			for (RouteGraphCoord possibleDestination : possibleDestinations)
			{
				if(visitedCoords.contains(possibleDestination)) continue;
				availableDestinations.add(possibleDestination);
			}

			if(availableDestinations.size() <= 0) return false;

			return true;
		}

		@Override
		public Coord next() {
			if(currentCoord == null) {
				currentCoord = startCoord = getBegins().get(random.nextInt(getBegins().size()));
			}
			else {
				currentCoord = availableDestinations.get(random.nextInt(availableDestinations.size()));
			}

			visitedCoords.add(currentCoord);

			return currentCoord;
		}
        private List<RouteGraphCoord> getBegins()
        {
            if(startCoords==null)
            {
                startCoords=new ArrayList<RouteGraphCoord>();
                for(RouteGraphCoord point:border)
                {
                    if(point.getPoiTypeEnum()== PoinType.Begin) startCoords.add(point);
                }
                if(startCoords.size()==0) startCoords = border;
            }
            return startCoords;
        }

		@Override
		public void remove() {
			// Not Supported
		}
	}

	@Override
	public Iterator<Coord> iterator() {
		return new RouteGraphIterator();
	}
}
