package info.vividcode.example.kotlin.di.handmade;

import info.vividcode.example.kotlin.di.handmade.lib.di.SettableLazy;
import kotlin.Lazy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LazyInjection<T> implements InvocationHandler {

    private LazyInjection(Lazy<T> injected) {
        this.injected = injected;
    }

    private Lazy<T> injected;

    public T getProxy() {
        return injected.getValue();
    }

    private T actualValue;

    public void inject(T value) {
        actualValue = value;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method m = actualValue.getClass().getMethod(method.getName(), method.getParameterTypes());
        return m.invoke(actualValue, args);
    }

    static <T> LazyInjection<T> create(Class<T> clazz) {
        SettableLazy<T> v = new SettableLazy<>();
        LazyInjection<T> ip = new LazyInjection<>(v);
        v.setValue(clazz.cast(Proxy.newProxyInstance(LazyInjection.class.getClassLoader(), new Class[] { clazz }, ip)));
        return ip;

    }

}
