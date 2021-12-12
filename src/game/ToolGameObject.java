package de.vd24.amongus.game;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.gui.SecurityCameraPage;
import de.vd24.amongus.gui.CallMeetingPage;
import de.vd24.amongus.model.Location;
import de.vd24.amongus.model.ToolType;
import de.vd24.neko.render.model.ModelBase;

public class ToolGameObject extends GameObject {

    private final Location location;
    private final ToolType toolType;

    public ToolGameObject(ModelBase model, Location location, ToolType toolType) {
        super(model);
        this.location = location;
        this.toolType = toolType;
    }

    @Override
    public boolean canInteract() {
        var session = AmongUs.get().getSession();
        if (session == null) return false;

        var myself = session.getMyself();
        return myself != null && myself.alive;
    }

    @Override
    public void onClick() {
        super.onClick();
        if (toolType == ToolType.Emergency) {
            AmongUs.get().getGuiManager().showPage(new CallMeetingPage());
        } else if (toolType == ToolType.Surveillance) {
            AmongUs.get().getGuiManager().showPage(new SecurityCameraPage());
        }
    }
}
