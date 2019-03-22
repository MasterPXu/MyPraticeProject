package com.gd.xp.pubsub;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Program {
	public static final String CHANNEL_NAME = "MY_CHANNEL";
	public static final String REDIS_HOST = "localhost"; //"192.168.*.*"
	public static final int REDIS_PORT = 6379;
	
	public static final Logger LOGGER = Logger.getLogger(Program.class);
	public static final JedisPoolConfig POOL_CONFIG = new JedisPoolConfig();
	public static final JedisPool JEDIS_POOL = new JedisPool(POOL_CONFIG,REDIS_HOST,REDIS_PORT,0);
	
	public static void main(String[] args) {
		final Jedis subscriberJedis = JEDIS_POOL.getResource();
		final Jedis publisherJedis = JEDIS_POOL.getResource();
		final Subscriber subscriber = new Subscriber();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					LOGGER.info("Subscribing to \"MY_CHANNEL\". This thread will be blocked.");
					
					subscriberJedis.subscribe(subscriber, CHANNEL_NAME);
					
					LOGGER.info("Subscription ended.");
				} catch (Exception e) {
					LOGGER.info("Subscription failed:" + e);
				}
				
			}
		}).start();
		
		new Publisher(publisherJedis, CHANNEL_NAME).startPublish();
		publisherJedis.close();
		
		subscriber.unsubscribe();
		subscriberJedis.close();
	}
}
