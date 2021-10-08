package com.example.SubnetUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SubnetUtilsApplicationTests {
	
	@Autowired
	SubnetLoader subnetLoader;

	@Test
	void isContainsTrueOrFalse() {
		//198.23.48.104 is part of list
		String listId = "src/main/resources/firehol_level1.netset";
		
		Pair<Boolean, List<ListMetaData>> pair = subnetLoader.contains("198.23.48.104");
		assertEquals(true,pair.getKey());
		assertEquals(subnetLoader.listmap.get(listId), pair.getValue().get(0));
		
		
		//198.20.16.0/20 is part of list
		Pair<Boolean, List<ListMetaData>> pair2 = subnetLoader.contains("198.20.16.255");
		assertEquals(true,pair2.getKey());
		assertEquals(subnetLoader.listmap.get(listId), pair.getValue().get(0));
		
		//1.1.1 is not part of list
		
		Pair<Boolean, List<ListMetaData>> pair3 = subnetLoader.contains("1.1.1.1");
		assertEquals(false,pair3.getKey());
		assertEquals(0, pair3.getValue().size());
	}

}
