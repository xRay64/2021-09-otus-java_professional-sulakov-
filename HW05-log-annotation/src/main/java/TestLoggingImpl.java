import annotations.Log;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void calculation(int param) {
        System.out.println("method with one int param");
    }

    @Log
    @Override
    public void calculation(int param, int param1) {
        System.out.println("method with two int param");
    }

//    @Log
    @Override
    public void calculation(int param, String param1) {
        System.out.println("method with one int param and one string param");
    }
}
