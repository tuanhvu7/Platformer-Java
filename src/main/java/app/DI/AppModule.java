package app.DI;

import app.Platformer;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module()
public class AppModule {

    @Provides @Singleton
    static Platformer appProvider() {
        return new Platformer();
    }
}
