package com.aztask.controllers;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.aztask.vo.TaskVO;

import akka.actor.ActorSelection;
import play.Logger.ALogger;
import play.Play;
import play.libs.Akka;
import play.mvc.*;

public class Application extends Controller {

	public static ALogger log=play.Logger.of(com.aztask.controllers.Application.class);

	public static Result index() {
    	String resource = "myibatis-config.xml";
    	InputStream inputStream;
		try {
			inputStream =Play.application().resourceAsStream(resource);// Resources.getResourceAsStream(resource);
	    	SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	    	System.out.println(sqlSessionFactory);
		} catch (Exception e) {
			log.error("My-Batis config file couldn't be found.");
			e.printStackTrace();
		}
		return ok("We have posted your task, you will be contact soon.");

//		return Promise.wrap(ask(myActor, "hello", 1000)).map(response -> ok(response.toString()));
    }
	
	public static Result task() {
		log.info("Starting to call actor.");
		//Akka.system().actorOf(arg0, arg1)
    	ActorSelection actorSelection = Akka.system().actorSelection("/user/ParentActor");
    	System.out.println("Actor Selection:"+actorSelection.anchor());
		log.info("Found actor."+actorSelection.anchor());
    	actorSelection.tell(new TaskVO("new task","general",4), null);
    	//actorSelection.tell(PoisonPill.getInstance(),null);
		return ok("Task has been forwarded.");

//		return Promise.wrap(ask(myActor, "hello", 1000)).map(response -> ok(response.toString()));
    }
	
//	public static Result redis(){
//		log.info("connecting to redis server.");
//		//JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1",);
//		JedisPool pool = new JedisPool("192.168.56.101",6379);
//		log.info("trying to connect with.");
//		Jedis jedis = pool.getResource();
//		String value=jedis.get("foo");
//		log.info("Object retrieved from Redis:"+value);
//		pool.returnResourceObject(jedis);
//		pool.destroy();
//		return ok("Task has been forwarded.");
//	}


}
