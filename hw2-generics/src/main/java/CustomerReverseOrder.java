import java.util.Stack;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    Stack<Customer> customerStack = new Stack<>();

    public void add(Customer customer) {
        customerStack.add(customer);
    }

    public Customer take() {

        return customerStack.pop();
    }
}
