package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<Method> methodList = getSortedMethodList(configClass.getMethods());
        for (Method method : methodList) {
            Class<?> appComponentType = method.getReturnType();
            Object instantiateComponent = instantiateComponent(appComponentType, configClass, method);
            String componentName = method.getDeclaredAnnotation(AppComponent.class).name();
            appComponents.add(instantiateComponent);
            appComponentsByName.put(componentName, instantiateComponent);
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object appComponent : appComponents) {
            if (appComponent.getClass() == componentClass) {
                return (C) appComponent;
            } else {
                Class<?>[] interfaces = appComponent.getClass().getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    if (anInterface == componentClass) {
                        return (C) appComponent;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private List<Method> getSortedMethodList(Method[] methods) {
        List<Method> methodList = new ArrayList<>();

        for (Method declaredMethod : methods) {
            AppComponent componentAnnotation = declaredMethod.getDeclaredAnnotation(AppComponent.class);
            if (componentAnnotation != null) {
                methodList.add(declaredMethod);
            }
        }

        methodList.sort(
                (o1, o2) -> {
                    int o1Order = o1.getDeclaredAnnotation(AppComponent.class).order();
                    int o2Order = o2.getDeclaredAnnotation(AppComponent.class).order();
                    return o1Order < o2Order ? -1 : (o1Order > o2Order) ? 1 : 0;
                }
        );

        return methodList;
    }

    private <T> T instantiateComponent(Class<T> clazz, Class<?> configClass, Method componentInstantiationMethod) {
        T resultObject = null;
        Class<?>[] parameterTypes = componentInstantiationMethod.getParameterTypes();

        try {
            Object configInstance = configClass.getConstructor().newInstance();

            if (parameterTypes.length == 0) {
                resultObject = (T) componentInstantiationMethod.invoke(configInstance);
            } else {
                List<Object> argsList = new ArrayList<>();
                for (Class<?> parameterType : componentInstantiationMethod.getParameterTypes()) {
                    argsList.add(getAppComponent(parameterType));
                }
                resultObject = (T) componentInstantiationMethod.invoke(configInstance, argsList.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultObject;
    }
}
