
public class tripNode {
	String arrivalTime;
	String departureTime; 
	int stopID; 
	int stopSequence; 
	int stopHeadsign; 
	int pickupType; 
	int dropOffType; 
	float distanceForThisNode;
	
	tripNode(String arrivalTime, String departureTime, int stopID, int stopSequence, int stopHeadsign, int pickupType, int dropOffType, float distanceForThisNode) {
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.stopID = stopID;
		this.stopSequence = stopSequence;
		this.stopHeadsign = stopHeadsign;
		this.pickupType = pickupType;
		this.dropOffType = dropOffType;
		this.distanceForThisNode = distanceForThisNode;
	}

}
