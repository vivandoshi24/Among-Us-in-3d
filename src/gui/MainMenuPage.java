package de.vd24.amongus.gui;

import de.vd24.amongus.core.AmongUs;
import de.vd24.amongus.model.ClientSession;
import de.vd24.amongus.net.NetMessage;
import de.vd24.neko.util.Log;

public class MainMenuPage extends BasePage {

    public MainMenuPage() {
        super("MainMenu.html");
    }

    @Override
    public void onDomReady() {
        super.onDomReady();
        context.setElementProperty("username", "value", AmongUs.get().getUserSettings().getUsername());
    }

    public void updateUsername(String user) {
        amongUs.getUserSettings().setUsername(user.trim());
        amongUs.getUserSettings().save();
    }

    public void createGame() {
        amongUs.getGuiManager().showPage(new CustomizePage());
    }

    public void joinGame(String gameCode) {
        Log.i("Game code: " + gameCode);

        if (gameCode.trim().length() == 0) {
            showError("Invalid game code!");
        } else {
            showLoading("Joining...");
            sendJoinMessage(gameCode, () -> {
                amongUs.getGuiManager().showPage(new LobbyPage());
            });
        }
    }

    public void settings() {
        amongUs.getGuiManager().showPage(new SettingsPage());
    }

    public void quit() {
        amongUs.getWindow().close();
    }

    public void credits() {
        amongUs.getGuiManager().showPage(new CreditsPage());
    }

}
