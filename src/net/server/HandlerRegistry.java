package de.vd24.amongus.net.server;

import de.vd24.neko.util.CrashHandler;
import de.vd24.neko.util.Log;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class HandlerRegistry {

    private final Map<Class, PacketHandler> map = new HashMap<>();

    public <T> void register(Class<T> clazz, PacketHandler<T> handler) {
        map.put(clazz, handler);
    }

    public void handle(PlayerConnection c, Object o) {
        var handler = map.get(o.getClass());
        if (handler == null) return;
        try {
            handler.handle(c, o);
        } catch (Throwable t) {
            Log.e("Critical server error");
            t.printStackTrace();
        }
    }

}
