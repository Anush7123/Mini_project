package restaurantmanagement;

public class SpecialMenuItem extends MenuItem {
    private String specialNote;

    public SpecialMenuItem(int id, String name, double price, String specialNote) {
        super(id, name, price);
        this.specialNote = specialNote;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    @Override
    public String toString() {
        return "SpecialMenuItem [id=" + getId() + ", name=" + getName() + ", price=" + getPrice() + ", specialNote=" + specialNote + "]";
    }

	private String getPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getId() {
		// TODO Auto-generated method stub
		return null;
	}
}