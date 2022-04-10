
public class tripNode {
	String arrivalTime;
	String departureTime;
	int stopID;
	int stopSequence;
	int stopHeadsign;
	int pickupType;
	int dropOffType;
	float distanceTraveled;

	tripNode(String arrivalTime, String departureTime, int stopID, int stopSequence, int stopHeadsign, int pickupType,
			int dropOffType, float distanceTraveled) {
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.stopID = stopID;
		this.stopSequence = stopSequence;
		this.stopHeadsign = stopHeadsign;
		this.pickupType = pickupType;
		this.dropOffType = dropOffType;
		this.distanceTraveled = distanceTraveled;
	}

	public void printInfo() {
		System.out.println("Arrival Time: " + arrivalTime + ", Departure Time: " + departureTime + ", StopID: "
				+ stopID + ", stop sequence: " + stopSequence + ", stop headsign: " + stopHeadsign + ", pickup type: "
				+ pickupType + ", dropOff type: " + dropOffType + ", Distance traveled: " + distanceTraveled);
	}

}
