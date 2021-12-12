package de.vd24.amongus.net.server;

public interface PacketHandler<T> {

    void handle(PlayerConnection connection, T message);

}
