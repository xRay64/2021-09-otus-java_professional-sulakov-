import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    TreeMap<Customer, String> customersDataMap = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = customersDataMap.firstEntry();
        return cloneEntry(entry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = customersDataMap.higherEntry(customer);
        return cloneEntry(entry);
    }

    public void add(Customer customer, String data) {
        customersDataMap.put(customer, data);
    }

    private <K extends MyCloneable, V> Map.Entry<K, V> cloneEntry(Map.Entry<K, V> clonableEntry) {
        Map.Entry<K, V> newEntry = null;
        if (clonableEntry != null) {
            newEntry = new AbstractMap.SimpleEntry<>(
                    (K) clonableEntry.getKey().clone(),
                    clonableEntry.getValue());
        }
        return newEntry;
    }
}
