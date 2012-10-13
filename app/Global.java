import com.antwerkz.sofia.play.SofiaPlugin;
import play.Application;
import play.GlobalSettings;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import plugins.MongOphelia;

import java.io.IOException;
import java.lang.reflect.Method;

public class Global extends GlobalSettings {
    private SofiaPlugin sofia;

    @Override
    public void beforeStart(Application application) {
        super.beforeStart(application);
        sofia = new SofiaPlugin();
        try {
            sofia.start();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void onStop(Application application) {
        super.onStop(application);
        sofia.stop();
    }

    @Override
    public Action onRequest(Http.Request request, Method method) {
        return new Action.Simple() {
            public Result call(Http.Context ctx) throws Throwable {
                Result result = delegate.call(ctx);
                MongOphelia.close();

                return result;
            }
        };
    }
}