public class Application {
    public static void main(String... args) {
        TestLogging testLogging = Ioc.getTestClass();
       testLogging.calculation(6);
       testLogging.calculation(6, 100);
       testLogging.calculation(6, "String parameter");
    }
}
