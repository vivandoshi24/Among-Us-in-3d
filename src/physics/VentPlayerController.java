package de.vd24.amongus.physics;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.game.VentGameObject;
import de.vd24.amongus.model.Location;
import de.vd24.amongus.net.NetMessage;
import de.vd24.neko.core.IPlayerController;
import de.vd24.neko.gl.Window;
import de.vd24.neko.render.Camera;
import de.vd24.neko.util.MathF;
import org.joml.Vector3f;

public class VentPlayerController implements IPlayerController {

    private Location vent;

    public VentPlayerController(Location vent) {
        this.vent = vent;
    }

    @Override
    public void update(Window window, Camera camera) {
        camera.getAngle().set(0, -90);
        for (var obj : AmongUs.get().getGameObjects())
            if (obj instanceof VentGameObject) {
                if (((VentGameObject) obj).getLocation() == vent)
                    camera.getPosition().set(obj.getPosition().x, obj.getPosition().y + 4, obj.getPosition().z);
            }

        AmongUs.get().getClient().sendMessage(new NetMessage.PositionChange(new Vector3f(camera.getPosition().x, -500, camera.getPosition().z), MathF.toRadians(camera.getAngle().x)));
    }

    public void setVent(Location vent) {
        this.vent = vent;
    }
}
