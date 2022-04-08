
public class Stop {
	int stop_id;
	int stop_code;
	String stop_name;
	String stop_desc;
	float stop_lat;
	float stop_lon;
	String zone_id;
	String stop_url;
	String location_type;
	String parent_station;
	
	Stop(int stop_id,int stop_code, String stop_name, String stop_desc, float stop_lat, float stop_lon, String zone_id, String stop_url, String location_type, String parent_station) {
		this.stop_id = stop_id;
		this.stop_code = stop_code;
		this.stop_name = stop_name;
		this.stop_desc = stop_desc;
		this.stop_lat = stop_lat;
		this.stop_lon = stop_lon;
		this.zone_id = zone_id;
		this.stop_url = stop_url;
		this.location_type = location_type;
		this.parent_station = parent_station;
	}
	
	public void printInfo() {
		System.out.println("---------------------------------------------------------");
		System.out.println("Name:" + stop_name);
		System.out.println("	" + "stop ID: " + stop_id);
		System.out.println("	" + "stop code: " + stop_code);
		System.out.println("	" + "stop desc: " + stop_desc);
		System.out.println("	" + "stop latitude: " + stop_lat);
		System.out.println("	" + "stop longitude: " + stop_lon);
		System.out.println("	" + "stop zone ID: " + zone_id);
		System.out.println("	" + "stop URL: " + stop_url);
		System.out.println("	" + "stop location type: " + location_type);
		System.out.println("	" + "stop parent station: " + parent_station);
		System.out.println("---------------------------------------------------------");
	}

}
