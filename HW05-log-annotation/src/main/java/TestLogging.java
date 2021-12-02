import annotations.Log;

public interface TestLogging {
    @Log
    void calculation(Integer param);

    @Log
    void calculation(Integer param, Integer param1);

    @Log
    void calculation(Integer param, String param1);
}
