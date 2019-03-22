package com.gd.xp;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisSample {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connected to local Redis services--------success");
		System.out.println("is services runinng: " + jedis.ping());
	}
	

	@Test
	public void TestRedisString() {
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connected to server successfully");
		
		jedis.set("Master", "Hello Redis");
		System.out.println("Stored string in redis: " + jedis.get("Master"));
	}
}
