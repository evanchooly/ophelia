import play.GlobalSettings;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import plugins.MongOphelia;

import java.lang.reflect.Method;

public class Global extends GlobalSettings {

    @Override
    public Action onRequest(Http.Request request, Method method) {
        return new Action.Simple() {
            public Result call(Http.Context ctx) throws Throwable {
                Result result = delegate.call(ctx);
                System.out.println("Global.call");
                System.out.println("closing mongo");
                MongOphelia.close();
                return result;
            }
        };
    }
}