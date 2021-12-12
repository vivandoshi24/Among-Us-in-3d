package de.vd24.amongus.net.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.game.PlayerGameObject;
import de.vd24.amongus.gui.MainMenuPage;
import de.vd24.amongus.model.GameState;
import de.vd24.amongus.net.NetMessage;
import de.vd24.amongus.util.Config;
import de.vd24.neko.event.Events;
import de.vd24.neko.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NetClient extends Listener {

    private final Client client = new Client();
    private final NetHandler handler = new NetHandler(this);
    private final CallbackHandler callbackHandler = new CallbackHandler();
    private final Executor executor = Executors.newSingleThreadExecutor();

    private volatile boolean connecting = false;

    public NetClient() {
        NetMessage.registerAll(client.getKryo());
    }

    public void connect() {
        if (client.isConnected())
            return;
        if (connecting) {
            Log.d("Already connecting");
            return;
        }

        connecting = true;
        var config = Config.get();

        try {
            client.start();
            client.addListener(this);
            client.connect(5000, config.getServerIp(), config.getServerPort());
        } catch (IOException e) {
            Log.e("Cannot connect to the server", e);
        }

        connecting = false;
    }

    public void disconnect() {
        AmongUs.get().setSession(null);
        client.close();
    }

    public CallbackHandler sendMessage(Object msg) {
        executor.execute(() -> {
            connect();
            if (!client.isConnected()) {
                Log.e("Failed to send message");
                callbackHandler.failAll();
                return;
            }
            client.sendTCP(msg);
        });
        return callbackHandler;
    }

    @Override
    public void connected(Connection connection) {
        Log.i("Connection established");
    }

    @Override
    public void disconnected(Connection connection) {
        Log.w("Connection lost");
        callbackHandler.failAll();
        AmongUs.get().setSession(null);
        AmongUs.get().removeGameObjects(o -> o instanceof PlayerGameObject);
        AmongUs.get().getStateController().changeState(GameState.Menus);
        AmongUs.get().getScheduler().run(() -> AmongUs.get().getGuiManager().showPage(new MainMenuPage()));
    }

    @Override
    public void received(Connection connection, Object o) {
        callbackHandler.handle(o);
        handler.handle(o);
        Events.post(o);
    }

    public boolean isConnected() {
        return client.isConnected();
    }
}
