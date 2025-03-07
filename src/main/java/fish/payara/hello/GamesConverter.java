package fish.payara.hello;


import fish.payara.hello.entities.Games;
import fish.payara.hello.jsf.GameBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;

@Named("gamesConverter")
@ApplicationScoped
public class GamesConverter implements Converter<Games> {

    @Inject
    private GameBean gameBean;

    @Override
    public String getAsString(FacesContext context, UIComponent component, Games game) {
        return (game == null || game.getId() == null) ? null : game.getId().toString();
    }

    @Override
    public Games getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return gameBean.getGameById(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            throw new ConverterException("Invalid Game ID: " + value);
        }
    }
}
