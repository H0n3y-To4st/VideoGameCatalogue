package fish.payara.hello.jsf;

import fish.payara.hello.servlet.ExportUserGamesService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named(value = "exportUserGamesBean")
@ViewScoped
public class ExportUserGamesBean implements Serializable {

    @Inject
    ExportUserGamesService exportUserGamesService;

    public void exportUserGames() throws IOException {
        exportUserGamesService.exportUserGames();
    }
}
