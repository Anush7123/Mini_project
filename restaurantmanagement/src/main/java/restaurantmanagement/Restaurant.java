package restaurantmanagement;

public class Restaurant {
    private int id;
    private String name;
    private String location;
    // Add more attributes as needed

    

    @Override
    public String toString() {
        return "Restaurant [id=" + id + ", name=" + name + ", location=" + location + "]";
    }



	public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Restaurant(int id, String name, String location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}
}
