package de.vd24.amongus.gui;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.model.Player;
import de.vd24.amongus.model.Sabotage;
import de.vd24.amongus.net.NetMessage;
import de.vd24.amongus.physics.BasePlayerController;
public class SabotagePage extends BasePage {

    private static long cooldown;

    public SabotagePage() {
        super("Sabotage.html");
        if (cooldown == 0)
            cooldown = System.currentTimeMillis() + 15000;
    }

    @Override
    public void onDomReady() {
        super.onDomReady();
        if (cooldown > System.currentTimeMillis())
            context.call("setCooldown");
    }

    public void sabotage(String sabotage) {
        var sab = Sabotage.valueOf(sabotage);

        if (cooldown < System.currentTimeMillis()) {
            restartCooldown();
            AmongUs.get().getClient().sendMessage(new NetMessage.StartSabotage(sab));
        }
    }
    public static void restartCooldown() {
        cooldown = System.currentTimeMillis() + 30000;
    }
    @Override
    protected boolean escapeGoesBack() {
        return true;
    }
}
