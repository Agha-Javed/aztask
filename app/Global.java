

import akka.actor.Props;
import com.aztask.akka.actors.TaskSupervisor;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;

public class Global extends GlobalSettings {

	final static Logger.ALogger logger = Logger.of(Global.class);


	@Override
	public void onStart(Application arg0) {
		super.onStart(arg0);
		logger.info("Application Started.");
    	Akka.system().actorOf(Props.create(TaskSupervisor.class),"ParentActor");
	}
	
}
