package classes;

public class Price {
    private int id;
    private String Name;
    private int Cost;

    public Price(int id, String name, int cost) {
        this.id = id;
        this.Name = name;
        this.Cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }
    public int getCost() {
        return Cost;
    }

    public void setName(String name) {
        this.Name = name;
    }
    public void setCost(int cost) {
        this.Cost = cost;
    }
}
