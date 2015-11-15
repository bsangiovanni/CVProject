package contour_detector;

import java_utilities.pgmutilities.PGM;

public interface IFilter {
	
	public void applyFilter(PGM imgIn, PGM imgOut);

}
