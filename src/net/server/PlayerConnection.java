package de.vd24.amongus.net.server;

import com.esotericsoftware.kryonet.Connection;
import de.vd24.amongus.model.Player;
import de.vd24.amongus.model.PlayerBehavior;
import de.vd24.amongus.model.PlayerColor;
import de.vd24.amongus.model.PlayerRole;

public class PlayerConnection extends Connection implements PlayerBehavior {

    public Player player = new Player();

    public ServerSession session;

    @Override
    public int getId() {
        return player.id;
    }

    @Override
    public String getUsername() {
        return player.username;
    }

    @Override
    public PlayerRole getRole() {
        return player.role;
    }

    @Override
    public PlayerColor getColor() {
        return player.color;
    }

}
