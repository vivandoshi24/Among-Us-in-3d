package de.vd24.amongus.gui;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.model.GameState;
import de.vd24.neko.Neko;
import de.vd24.neko.sound.SoundSource;

public class CreditsPage extends BasePage {

    private SoundSource soundSource;

    public CreditsPage() {
        super("Credits.html");
    }

    @Override
    public void onUnload() {
        super.onUnload();
        soundSource.stop();
        amongUs.getStateController().changeState(GameState.Menus);
    }

    @Override
    public void onDomReady() {
        soundSource = AmongUs.get().getSoundFX().play("CreditsRoll.ogg");
        context.setElementText("nekoVersion", Neko.VERSION);
        amongUs.getStateController().changeState(GameState.Credits);
    }

    public void back() {
        goBack();
    }

}
