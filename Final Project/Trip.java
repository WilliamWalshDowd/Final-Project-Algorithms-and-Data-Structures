import java.util.ArrayList;

public class Trip {
	int tripID;
	ArrayList<tripNode> nodes;
	
	Trip(int tripID) {
		this.tripID = tripID;
		this.nodes = new ArrayList<tripNode>();
	}
}
