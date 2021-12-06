import annotations.Log;

public interface TestLogging {
    @Log
    void calculation(int param);

    @Log
    void calculation(int param, int param1);

    @Log
    void calculation(int param, String param1);
}
