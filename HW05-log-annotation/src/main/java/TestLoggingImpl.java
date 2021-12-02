import annotations.Log;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void calculation(Integer param) {
        System.out.println("method with one int param");
    }

    @Log
    @Override
    public void calculation(Integer param, Integer param1) {
        System.out.println("method with two int param");
    }

    @Log
    @Override
    public void calculation(Integer param, String param1) {
        System.out.println("method with one int param and one string param");
    }
}
