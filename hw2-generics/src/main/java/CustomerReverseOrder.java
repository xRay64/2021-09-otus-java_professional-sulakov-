import java.util.LinkedList;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    LinkedList<Customer> customerList = new LinkedList<>();

    public void add(Customer customer) {
        customerList.add(customer);
    }

    public Customer take() {
        return customerList.pollLast();
    }
}
