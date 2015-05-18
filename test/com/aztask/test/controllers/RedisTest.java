package com.aztask.test.controllers;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.pool2.impl.GenericObjectPool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
	
	public static void main(String[] args) throws IOException {
		
		JedisPoolConfig jConfig = new JedisPoolConfig();
		  jConfig = new JedisPoolConfig();
		  jConfig.setMaxTotal(50);
		  jConfig.setMaxIdle(10);
		  jConfig.setMinIdle(10);
		  
		 // jConfig.setWhenExhaustedAction(GenericObjectPool.MEAN_TIMING_STATS_CACHE_SIZE);
		  JedisPool pool = new JedisPool(jConfig, "192.168.56.101", 6379);
		  //JedisPool pool = new JedisPool(jConfig, "127.0.0.1", 6379);
		  
		  
		System.out.println("Connecting to Redis server.");
		
		System.out.println("connecting to redis server.");
		//JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1",);
	//	JedisPool pool = new JedisPool(new GenericObjectPoolConfig(), "127.0.0.1","6379");
		
		//JedisPool pool = new JedisPool("127.0.0.1",6379);
		//JedisPool pool = new JedisPool("10.0.2.15",6379);
		
		
		System.out.println("trying to connect with."+pool);
		System.out.println("is closed."+pool.getNumActive());

		Jedis jedis = pool.getResource();
		//jedis.set("foo", "test");
		System.out.println("object saved."+jedis.get("foo"));
		pool.returnResource(jedis);
		pool.destroy();
		
//		System.out.println("Connecting with server.");
//		//MemcachedClient c=new MemcachedClient(new InetSocketAddress("127.0.1.1", 11211));
//		//MemcachedClient c=new MemcachedClient(new InetSocketAddress("10.0.2.15", 11211));
//		MemcachedClient c=new MemcachedClient(new InetSocketAddress("192.168.56.101", 11211));
//
//		System.out.println("Client connected.");
//
//			// Store a value (async) for one hour
//			//c.set("name", 36000, "Javed");
//			// Retrieve a value (synchronously).
//			Object myObject=c.get("name");
//			System.out.println("Object receieved."+myObject);
		
	}

}
