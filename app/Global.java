

import akka.actor.ActorRef;
import akka.actor.Props;
import com.aztask.actors.TaskSupervisor;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;

public class Global extends GlobalSettings {

	final static Logger.ALogger logger = Logger.of(Global.class);

	@Override
	public void onStart(Application arg0) {
		super.onStart(arg0);
		logger.info("Javed:: Application Started.");
    	ActorRef actorRef=Akka.system().actorOf(Props.create(TaskSupervisor.class),"ParentActor");
    	logger.info("Parent actor create::"+actorRef.path());
    	actorRef.tell(true, null);
	}
	
}
