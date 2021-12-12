package de.vd24.amongus.game;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.gui.FixSabotagePage;
import de.vd24.amongus.model.Sabotage;
import de.vd24.neko.render.Color;
import de.vd24.neko.render.model.ModelBase;

public class SabotageGameObject extends GameObject {

    private final Sabotage sabotage;
    private final int index;

    public SabotageGameObject(ModelBase model, Sabotage sabotage, int index) {
        super(model);
        this.sabotage = sabotage;
        this.index = index;
    }

    @Override
    public void onClick() {
        super.onClick();
        AmongUs.get().getGuiManager().showPage(new FixSabotagePage(sabotage));
    }

    @Override
    public Color getHighlightColor() {
        return new Color(1, 0, 0, 0.75f);
    }

    @Override
    public boolean canInteract() {
        return AmongUs.get().getSession().currentSabotage == sabotage;
    }
}
