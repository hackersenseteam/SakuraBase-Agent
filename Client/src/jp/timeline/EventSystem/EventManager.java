package jp.timeline.EventSystem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventManager
{
    private static final CopyOnWriteArrayList<Object> listeners = new CopyOnWriteArrayList<>();

    public static void reset()
    {
        listeners.clear();
    }

    public static void addListener(Object... projects)
    {
        for (Object object : projects)
        {
            if (!listeners.contains(object))
            {
                listeners.add(object);
            }
        }
    }

    public static void removeListener(Object... projects)
    {
        for (Object object : projects)
        {
            if (!listeners.isEmpty())
            {
                listeners.remove(object);
            }
        }
    }

    public static EventCore call(EventCore event)
    {
        for (Object listener : listeners)
        {
            if (listener.getClass() == null)
            {
                return event;
            }

            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (!method.isAccessible())
                {
                    method.setAccessible(true);
                }

                try
                {
                    if (method.isAnnotationPresent(EventListener.class))
                    {
                        if (method.getParameterCount() > 1)
                        {
                            throw new EventException("[EventSystem] too many method types! method name:" + method.getName());
                        }

                        Events events = method.getDeclaredAnnotation(EventListener.class).event();

                        if (events != Events.NONE && events == event.getEvents())
                        {
                            CurrentEvent.setEvent(event);

                            if (method.getParameterCount() == 1)
                            {
                                Class<?> methodObject = method.getParameterTypes()[0];
                                if (methodObject != event.getClass())
                                {
                                    throw new EventException("[EventSystem] The event does not match! method name:" + method.getName());
                                }
                                method.invoke(listener, event);
                            }
                            else
                            {
                                method.invoke(listener);
                            }
                        }
                        else if (events == Events.NONE && method.getParameterCount() == 1)
                        {
                            Class<?> methodObject = method.getParameterTypes()[0];

                            if (methodObject != null && methodObject == event.getClass())
                            {
                                method.invoke(listener, event);
                            }
                        }
                        else if (events == event.getEvents())
                        {
                            throw new EventException("Incorrect usage! method name:" + method.getName());
                        }
                    }
                }
                catch (IllegalAccessException | InvocationTargetException e)
                {
                    //System.err.println("Problematic usage! error:");
                    //e.printStackTrace();
                }
            }
        }

        return event;
    }

    public static Object call(Class<?> even, boolean isTrue)
    {
        if (!even.isInstance(EventCore.class))
            return even;

        for (Object listener : listeners)
        {
            if (listener.getClass() == null)
            {
                return even;
            }

            for (Method method : listener.getClass().getDeclaredMethods())
            {
                if (!method.isAccessible())
                {
                    method.setAccessible(true);
                }

                try
                {
                    if (method.isAnnotationPresent(EventListener.class))
                    {
                        if (method.getParameterCount() > 1)
                        {
                            throw new EventException("[EventSystem] too many method types! method name:" + method.getName());
                        }

                        Events events = method.getDeclaredAnnotation(EventListener.class).event();
                        EventCore event = even.asSubclass(EventCore.class).newInstance();

                        if (events != Events.NONE && events == event.getEvents())
                        {
                            CurrentEvent.setEvent(event);

                            if (method.getParameterCount() == 1)
                            {
                                Class<?> methodObject = method.getParameterTypes()[0];
                                if (methodObject != event.getClass())
                                {
                                    throw new EventException("[EventSystem] The event does not match! method name:" + method.getName());
                                }
                                method.invoke(listener, event);
                            }
                            else
                            {
                                method.invoke(listener);
                            }
                        }
                        else if (events == Events.NONE && method.getParameterCount() == 1)
                        {
                            Class<?> methodObject = method.getParameterTypes()[0];

                            if (methodObject != null && methodObject == event.getClass())
                            {
                                method.invoke(listener, event);
                            }
                        }
                        else if (events == event.getEvents())
                        {
                            throw new EventException("Incorrect usage! method name:" + method.getName());
                        }
                    }
                }
                catch (IllegalAccessException | InvocationTargetException | InstantiationException e)
                {
                    //System.err.println("Problematic usage! error:");
                    //e.printStackTrace();
                }
            }
        }

        return even;
    }
}
