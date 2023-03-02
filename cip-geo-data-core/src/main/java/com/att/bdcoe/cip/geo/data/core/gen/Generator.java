package com.att.bdcoe.cip.geo.data.core.gen;

import com.att.bdcoe.cip.geo.data.core.Coord;
import com.att.bdcoe.cip.geo.data.core.Track;
import com.att.bdcoe.cip.geo.data.core.TrackCoord;

import java.util.Date;

public interface Generator<TId, TRCoord extends Coord, TTCoord extends TrackCoord>  {
	Track<TId, TTCoord> generate(TId id, Date startTimestamp, double trackIntervalSeconds, double speedMps);
}
