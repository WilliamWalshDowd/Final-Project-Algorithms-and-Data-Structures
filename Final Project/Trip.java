import java.util.ArrayList;

public class Trip {
	int tripID;
	ArrayList<TripNode> nodes;
	
	Trip(int tripID) {
		this.tripID = tripID;
		this.nodes = new ArrayList<TripNode>();
	}
}
