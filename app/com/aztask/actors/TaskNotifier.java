package com.aztask.actors;

import com.aztask.vo.TaskVO;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

public class TaskNotifier  extends AbstractActor {

	private final LoggingAdapter log = Logging.getLogger(context().system(),this);

	public TaskNotifier() {
		receive(ReceiveBuilder.match(TaskVO.class, task -> {
			log.info("Received Task from supervisor:"+sender().path().name());
			sender().tell("Child Result", self());
		}).matchAny(o -> log.info("received unknown message")).build());
	}

}
