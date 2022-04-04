
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

}
