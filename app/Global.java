

import akka.actor.Props;
import java.lang.reflect.Method;
import com.aztask.akka.actors.TaskSupervisor;
import com.aztask.util.Util;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class Global extends GlobalSettings {

	final static Logger.ALogger logger = Logger.of(Global.class);


	@Override
	public void onStart(Application arg0) {
		super.onStart(arg0);
		logger.info("Application Started.");
    	Akka.system().actorOf(Props.create(TaskSupervisor.class),"ParentActor");
	}
	
	
//	@Override
//	public Action onRequest(Request request, Method actionMethod) {
//	   System.out.println("JAVED...Start Request" + request.toString());
//	   Util.insertEmptyLines();
//	   
//	   Action onRequest = super.onRequest(request, actionMethod);
//	   
//	   Util.insertEmptyLines();
//	   System.out.println("JAVED...End Request" + request.toString());
//	   Util.insertEmptyLines();
//	   
//	   return onRequest;
//	}
	
	public Action onRequest(final Http.Request request, Method method) {

	    Action action =  new Action.Simple() {
	        public Promise<Result> call(Http.Context ctx) throws Throwable {
	        	Util.insertEmptyLines();
	            Long start = System.currentTimeMillis();
	            
	            Logger.info("START :: method=" + request.method() + " uri=" + request.uri() + " remote-address=" + request.remoteAddress() + " start time=" +start);
	            Util.insertEmptyLine();
	            
	            Promise<Result> result = delegate.call(ctx);

	            Long finish = System.currentTimeMillis();
	            
	            Util.insertEmptyLine();
	            Logger.info("END :: method=" + request.method() + " uri=" + request.uri() + " remote-address=" + request.remoteAddress() + " time=" + (finish-start));
	            Util.insertEmptyLines();
	            
	            return result;
	        }
	    };
	    return action;
	}
	
}
