package com.att.bdcoe.cip.geo.data.core.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.Track;
import com.att.bdcoe.cip.geo.data.core.TrackCoord;

import java.util.Date;
import java.util.List;

public interface TrackBuilder<TId, TCoord extends TrackCoord> {

	Track<TId, TCoord> buildTrack(TId id, List<TCoord> coords);
	TCoord buildTrackCoord(Date timestamp, Coord coord, CoordType coordType);
    public void setFluctuation(double fluctuation);
}
