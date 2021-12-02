import annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class Ioc {

    private Ioc() {
    }

    static TestLogging getTestClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<?> clazz = myClass.getClass();
            Class<?>[] paramsClasses = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                paramsClasses[i] = args[i].getClass();
            }
            Method clazzMethod;
            clazzMethod = clazz.getMethod(method.getName(), paramsClasses);
            if (clazzMethod.isAnnotationPresent(Log.class)) {
                System.out.println("invoking method: " + clazzMethod.getName() + "; with params: " + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }
    }
}
