package de.vd24.amongus.gui;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.event.SabotageEvent;
import de.vd24.amongus.model.Sabotage;
import de.vd24.amongus.net.NetMessage;
import org.greenrobot.eventbus.Subscribe;

public class FixSabotagePage extends BasePage {

    private final Sabotage sabotage;

    public FixSabotagePage(Sabotage sabotage) {
        super("Tasks/Fix" + sabotage.name() + ".html");
        this.sabotage = sabotage;
    }

    @Override
    public void onDomReady() {
        if (AmongUs.get().getSession().currentSabotage == Sabotage.O2) {
            context.call("setCode", AmongUs.get().getSession().currentSabotageCode);
        }
    }

    public void setFixing(boolean fixing) {
        AmongUs.get().getClient().sendMessage(new NetMessage.FixSabotage(AmongUs.get().getSession().currentSabotage, fixing));
    }

    @Subscribe
    public void onSabotageFixed(SabotageEvent event) {
        if (amongUs.getSession().currentSabotage == null) {
            AmongUs.get().getScheduler().runLater(800, () -> {
                if (AmongUs.get().getStateController().isRunning())
                    this.goBack();
            });
        }
    }

    @Override
    protected boolean escapeGoesBack() {
        return true;
    }

}
