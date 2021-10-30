public class Customer implements Comparable<Customer>, MyCloneable<Customer>{
    private final long id;
    private String name;
    private long scores;

    //todo: 1. в этом классе надо исправить ошибки

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Customer c) {
        int result = 0;
        if (this.scores > c.getScores()) {
            return 1;
        } else if (this.scores < c.getScores()) {
            return -1;
        }
        return result;
    }

    @Override
    public Customer clone() {
        return new Customer(this.id, this.name, this.scores);
    }
}
