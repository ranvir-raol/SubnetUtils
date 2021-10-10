package com.example.SubnetUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.SubnetUtils.loader.SubnetLoaderDecimalSet;
import com.example.SubnetUtils.loader.SubnetLoaderMap;
import com.example.SubnetUtils.loader.SubnetLoaderMapWithTrieStyle;
import com.example.SubnetUtils.loader.SubnetLoaderTrie;
import com.example.SubnetUtils.model.ListMetaData;
import com.example.SubnetUtils.model.Trie;

import edazdarevic.commons.net.CIDRUtils;

@SpringBootTest
class SubnetUtilsApplicationTests {

	@Autowired
	SubnetLoaderMap subnetLoaderMap;

	@Autowired
	SubnetLoaderTrie subnetLoaderTrie;

	@Autowired
	SubnetLoaderDecimalSet subnetLoaderDecimalSet;
	
	@Autowired
	SubnetLoaderMapWithTrieStyle subnetLoaderMapWithTrieStyle;
	

//	@Test
	// Internal test
	void checkRange() throws UnknownHostException {

		String listId = "src/main/resources/firehol_level1.netset";

		CIDRUtils cidrUtils = new CIDRUtils("224.0.0.0/3");
		List<String> list = new ArrayList<String>();

		Trie trie = new Trie();

		String subnetStr = "224.0.0.0/3";
		cidrUtils = new CIDRUtils(subnetStr);
		for (String str : cidrUtils.identifySubnetRange()) {
			System.out.println(str);
			trie.insert(str, subnetStr, listId);
		}

		subnetStr = "127.0.0.0/8";
		cidrUtils = new CIDRUtils(subnetStr);
		for (String str : cidrUtils.identifySubnetRange()) {
			System.out.println(str);
			trie.insert(str, subnetStr, listId);
		}

		subnetStr = "100.64.0.0/10";
		cidrUtils = new CIDRUtils(subnetStr);
		for (String str : cidrUtils.identifySubnetRange()) {
			System.out.println(str);
			trie.insert(str, subnetStr, listId);
		}

		subnetStr = "207.228.192.0/20";
		cidrUtils = new CIDRUtils(subnetStr);
		for (String str : cidrUtils.identifySubnetRange()) {
			System.out.println(str);
			trie.insert(str, subnetStr, listId);
		}

		subnetStr = "217.20.116.152/31";
		cidrUtils = new CIDRUtils(subnetStr);
		for (String str : cidrUtils.identifySubnetRange()) {
			System.out.println(str);
			trie.insert(str, subnetStr, listId);
		}

		subnetStr = "5.63.155.65/32";
		cidrUtils = new CIDRUtils(subnetStr);
		for (String str : cidrUtils.identifySubnetRange()) {
			System.out.println(str);
			trie.insert(str, subnetStr, listId);
		}

//		trie.insert("224.0.0.0/3");
//		trie.insert("127.0.0.0/8");
//		trie.insert("100.64.0.0/10");

		trie.printTrie(trie.root, 0);

//		System.out.println("224.0.0.0/3");
//		System.out.println(cidrUtils.isInRange("255.255.255.255"));
//		System.out.println(cidrUtils.getPrefixLength());
//		System.out.println(cidrUtils.getStartAddress());
//		System.out.println(cidrUtils.getEndAddress());
//		System.out.println(cidrUtils.getPrefixLength()/8);
//		List<String> list = identifySubnetRange("224.0.0.0/3");
//		for(String str: list) {
//			System.out.println("\t"+ str);
//		}
//		
//		System.out.println("\n");
//		
//		
//		
//		
//		cidrUtils = new CIDRUtils("127.0.0.0/8");
//		
//		System.out.println("127.0.0.0	8");
//		System.out.println(cidrUtils.isInRange("255.255.255.255"));
//		System.out.println(cidrUtils.getPrefixLength());
//		System.out.println(cidrUtils.getStartAddress());
//		System.out.println(cidrUtils.getEndAddress());
//		System.out.println(cidrUtils.getPrefixLength()/8);
//		
//		list = identifySubnetRange("127.0.0.0/8");
//		for(String str: list) {
//			System.out.println("\t"+ str);
//		}
//		
//		System.out.println("\n");
//		
//		
//		
//		cidrUtils = new CIDRUtils("100.64.0.0/10");
//		
//		System.out.println("100.64.0.0	10");
//		System.out.println(cidrUtils.isInRange("255.255.255.255"));
//		System.out.println(cidrUtils.getPrefixLength());
//		System.out.println(cidrUtils.getStartAddress());
//		System.out.println(cidrUtils.getEndAddress());
//		System.out.println(cidrUtils.getPrefixLength()/8);
//		list = identifySubnetRange("100.64.0.0/10");
//		for(String str: list) {
//			System.out.println("\t"+ str);
//		}
//		System.out.println("\n");

//		cidrUtils = new CIDRUtils("207.228.192.0/20");
//		
//		System.out.println("1207.228.192.0	20");
//		System.out.println(cidrUtils.isInRange("255.255.255.255"));
//		System.out.println(cidrUtils.getPrefixLength());
//		System.out.println(cidrUtils.getStartAddress());
//		System.out.println(cidrUtils.getEndAddress());
//		System.out.println(cidrUtils.getPrefixLength()/8);
//		list = identifySubnetRange("207.228.192.0/20");
//		for(String str: list) {
//			System.out.println("\t"+ str);
//		}
//		System.out.println("\n");
//		
//		
//		cidrUtils = new CIDRUtils("217.20.116.152/31");
//		
//		System.out.println("217.20.116.152	31");
//		System.out.println(cidrUtils.isInRange("255.255.255.255"));
//		System.out.println(cidrUtils.getPrefixLength());
//		System.out.println(cidrUtils.getStartAddress());
//		System.out.println(cidrUtils.getEndAddress());
//		System.out.println(cidrUtils.getPrefixLength()/8);
//		list = identifySubnetRange("217.20.116.152/31");
//		for(String str: list) {
//			System.out.println("\t"+ str);
//		}
//		System.out.println("\n");
//		
//		cidrUtils = new CIDRUtils("5.63.155.65/32");
//		
//		System.out.println("5.63.155.65 32");
//		System.out.println(cidrUtils.isInRange("255.255.255.255"));
//		System.out.println(cidrUtils.getPrefixLength());
//		System.out.println(cidrUtils.getStartAddress());
//		System.out.println(cidrUtils.getEndAddress());
//		System.out.println(cidrUtils.getPrefixLength()/8);
//		list = identifySubnetRange("5.63.155.65/32");
//		for(String str: list) {
//			System.out.println("\t"+ str);
//		}
//		System.out.println("\n");

	}

	static boolean checkRange(List<String> CIDR_networks, String ip) {
		for (String network : CIDR_networks) {
			System.out.println("Looking at range: " + network + " and ip " + ip);
			SubnetUtils utils = new SubnetUtils(network);
			if (utils.getInfo().isInRange(ip)) {
				System.out.println(ip + " in range " + network);
				return true;
			}
		}
		// no matches
		return false;
	}

	@Test
	void isContains() throws UnknownHostException {
		CIDRUtils utils = new CIDRUtils("224.0.0.0/3");
		assertEquals(true, utils.isInRange("225.1.0.0"));
	}

	@Test
	void isContainsTrueOrFalseMap() throws FileNotFoundException, IOException {

		String listId = "src/main/resources/firehol_level4.netset";
		
		subnetLoaderMap.init();

		// 1.0.213.203 is part of list
		Pair<Boolean, List<ListMetaData>> pair = subnetLoaderMap.contains("1.0.213.203");
		assertEquals(true, pair.getKey());
		assertEquals(subnetLoaderMap.listmap.get(listId), pair.getValue().get(0));

	}
	
	
	@Test
	void isContainsTrueOrFalseMapWithTrieStyle() throws FileNotFoundException, IOException {

		String listId = "src/main/resources/firehol_level1.netset";
		
		subnetLoaderMapWithTrieStyle.init_Trie();

		System.out.println("\n\n\n-----");
		
		// 198.23.48.104 is part of list
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair = subnetLoaderMapWithTrieStyle.contains("198.23.48.104");
		assertEquals(true, pair.getKey());
		assertEquals(subnetLoaderMapWithTrieStyle.listmap.get(listId), pair.getValue().get(0).getValue());

		// 198.20.16.0/20 is part of list
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair2 = subnetLoaderMapWithTrieStyle.contains("198.20.16.255");
		assertEquals(true, pair2.getKey());
		assertEquals(subnetLoaderMapWithTrieStyle.listmap.get(listId), pair.getValue().get(0).getValue());

		// 1.1.1 is not part of list

		Pair<Boolean, List<Pair<String, ListMetaData>>> pair3 = subnetLoaderTrie.contains("1.1.1.1");
		assertEquals(false, pair3.getKey());
		assertEquals(0, pair3.getValue().size());

	}

	@Test
	void isContainsTrueOrFalseTrie() throws FileNotFoundException, IOException {

		String listId = "src/main/resources/firehol_level1.netset";
		
		
		subnetLoaderTrie.init_Trie();
		

		// 198.23.48.104 is part of list
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair = subnetLoaderTrie.contains("198.23.48.104");
		assertEquals(true, pair.getKey());
		assertEquals(subnetLoaderTrie.listmap.get(listId), pair.getValue().get(0).getValue());

		// 198.20.16.0/20 is part of list
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair2 = subnetLoaderTrie.contains("198.20.16.255");
		assertEquals(true, pair2.getKey());
		assertEquals(subnetLoaderTrie.listmap.get(listId), pair.getValue().get(0).getValue());

		// 1.1.1 is not part of list

		Pair<Boolean, List<Pair<String, ListMetaData>>> pair3 = subnetLoaderTrie.contains("1.1.1.1");
		assertEquals(false, pair3.getKey());
		assertEquals(0, pair3.getValue().size());

	}

	@Test
	void isContainsTrueOrFalseTrieMultiMatch() throws FileNotFoundException, IOException {
		
		subnetLoaderTrie.init_Trie();

		// 225.0.0.0 should be rejected due to 224.0.0.0/3
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair4 = subnetLoaderTrie.contains("224.1.0.0");
		assertEquals(true, pair4.getKey());
		assertEquals(2, pair4.getValue().size());
	}

	@Test
	void isContainsTrueOrFalseDecimalMap() throws FileNotFoundException, IOException {

		
		subnetLoaderDecimalSet.init();
		
		// 1.0.213.203 is part of list
		Pair<Boolean, List<ListMetaData>> pair = subnetLoaderDecimalSet.contains("1.0.213.203");
		assertEquals(true, pair.getKey());

	}

}
