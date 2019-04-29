package com.javarush.task.task32.task3212;

import com.javarush.task.task32.task3212.contex.InitialContext;
import com.javarush.task.task32.task3212.service.Service;


public class ServiceLocator {
    private static Cache cache;

    static {
        cache = new Cache();
    }

    /**
     * First check the service object available in cache
     * If service object not available in cache do the lookup using
     * JNDI initial context and get the service object. Add it to
     * the cache for future use.
     *
     * @param jndiName The name of service object in context
     * @return Object mapped to name in context
     */
    /*1) Верни из кэша нужный сервис.
    2) Если в кэше нет нужного сервиса то:
    2.1) Создай контекст.
    2.2) Возьми у контекста нужный сервис.
    2.3) Добавь сервис в кеш и верни его.*/

    public static Service getService(String jndiName) {
        if (jndiName != null) {
            Service service = cache.getService(jndiName);
            if (service == null) {
                InitialContext initialContext = new InitialContext();
                service = (Service) initialContext.lookup(jndiName);
                cache.addService(service);
            }
            return service;
        }
        else
            return null;
    }
}
