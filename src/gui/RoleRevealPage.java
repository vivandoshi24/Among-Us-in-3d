package de.vd24.amongus.gui;

import de.vd24.amongus.model.GameState;
import de.vd24.amongus.model.PlayerRole;

public class RoleRevealPage extends BasePage {

    public RoleRevealPage() {
        super("RoleReveal.html");
        amongUs.getScheduler().runLater(4700, () -> amongUs.getGuiManager().showPage(new IngamePage()));
    }

    @Override
    public void onDomReady() {
        var players = amongUs.getSession().getPlayers();
        var me = amongUs.getSession().getMyself();
        context.call("setRole", me.role.name());
        for (var player : players) {
            if (me.role == PlayerRole.Impostor && player.role != me.role) continue;
            context.call("addTeammate", player.username);
        }
        amongUs.getStateController().changeState(GameState.Starting);
    }

}
