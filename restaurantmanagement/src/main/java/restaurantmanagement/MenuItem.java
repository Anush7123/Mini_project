package restaurantmanagement;

public class MenuItem {
    private int id;
    private String name;
    private double price;

    public MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters and Setters (same as before)

    @Override
    public String toString() {
        return "MenuItem [id=" + id + ", name=" + name + ", price=" + price + "]";
    }

	public MenuItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}

