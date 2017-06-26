package paradox.ui.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import paradox.ui.application.ApplicationModule;

import java.net.URL;

public final class FXMLLoaderWithGuice extends FXMLLoader {

    private static final Injector injector = Guice.createInjector(new ApplicationModule());

    public FXMLLoaderWithGuice() {
        super();
        this.setControllerFactory(injector::getInstance);
    }

    public FXMLLoaderWithGuice(final URL location) {
        super(location);
        this.setControllerFactory(injector::getInstance);
    }
}
