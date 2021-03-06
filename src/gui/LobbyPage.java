package de.vd24.amongus.gui;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.model.GameState;
import de.vd24.amongus.net.NetMessage;
import org.greenrobot.eventbus.Subscribe;

public class LobbyPage extends BasePage {

    public LobbyPage() {
        super("Lobby.html");
    }

    @Override
    public void onDomReady() {
        super.onDomReady();
        context.setElementText("gamecode", amongUs.getSession().getGameCode());

        for (var player : amongUs.getSession().getPlayers()) {
            addPlayer(player.id, player.username, player.color.name());
        }

        AmongUs.get().getStateController().changeState(GameState.Lobby);

        updateHost();
    }

    private void updateHost() {
        context.call("setIsHost", amongUs.getSession().getMyPlayerId() == amongUs.getSession().getHost());
    }

    public void start() {
        amongUs.getClient().sendMessage(new NetMessage.StartGame());
    }

    public void customize() {

    }

    public void disconnect() {
        amongUs.getClient().disconnect();
        amongUs.getGuiManager().showPage(new MainMenuPage());
    }

    @Subscribe
    public void onStart(NetMessage.OnGameStart start) {
        runOnUiThread(() -> amongUs.getGuiManager().showPage(new RoleRevealPage()));
    }

    @Subscribe
    public void onJoin(NetMessage.OnPlayerJoin join) {
        runOnUiThread(() -> addPlayer(join.id, join.username, join.color.name()));
    }

    @Subscribe
    public void onLeave(NetMessage.OnPlayerLeave leave) {
        runOnUiThread(() -> context.call("removePlayer", leave.id));
    }

    @Subscribe
    public void onHostChanged(NetMessage.OnHostChanged changed) {
        runOnUiThread(this::updateHost);
    }

    private void addPlayer(int id, String username, String color) {
        context.call("addPlayer", id, username, color);
    }

}
