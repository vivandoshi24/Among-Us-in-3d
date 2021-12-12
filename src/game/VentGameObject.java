package de.vd24.amongus.game;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.gui.VentPage;
import de.vd24.amongus.model.Location;
import de.vd24.amongus.model.PlayerRole;
import de.vd24.amongus.model.VentConnections;
import de.vd24.neko.render.Color;
import de.vd24.neko.render.model.ModelBase;

public class VentGameObject extends GameObject {

    private final Location location;
    private final int index;

    public VentGameObject(ModelBase model, Location location, int index) {
        super(model);
        this.location = location;
        this.index = index;
    }

    @Override
    public boolean canInteract() {
        var me = AmongUs.get().getSession().getMyself();
        return me.role == PlayerRole.Impostor && me.alive;
    }

    @Override
    public void onClick() {
        super.onClick();
        AmongUs.get().getGuiManager().showPage(new VentPage(location));
    }

    @Override
    public Color getHighlightColor() {
        return new Color(1, 0, 0, 0.75f);
    }

    public Location getLocation() {
        return location;
    }
}
