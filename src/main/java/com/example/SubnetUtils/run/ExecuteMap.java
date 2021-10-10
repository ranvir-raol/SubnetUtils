package com.example.SubnetUtils.run;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.SubnetUtils.loader.SubnetLoaderDecimalSet;
import com.example.SubnetUtils.loader.SubnetLoaderMapWithTrieStyle;
import com.example.SubnetUtils.loader.SubnetLoaderTrie;
import com.example.SubnetUtils.model.ListMetaData;

@Component
@Order(value = 1)
public class ExecuteMap implements CommandLineRunner {

	@Autowired
	SubnetLoaderTrie subnetLoaderTrie;

	@Autowired
	SubnetLoaderDecimalSet subnetLoaderDecimalSet;

	@Autowired
	SubnetLoaderMapWithTrieStyle subnetLoaderMapWithTrieStyle;

	@Override
	public void run(String... args) throws Exception {

//		runTrie();
		runMapAndTrie();
	}

	/**
	 * Solution doesnt scale with 16GB memory and all the level1 data
	 * 
	 * @throws Exception
	 */
	private void runDecimalMap() throws Exception {
		String listId = "src/main/resources/firehol_level1.netset";

		Thread.sleep(20 * 1000);

		long sTime = System.currentTimeMillis();
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("Heap Size = " + heapSize);
		subnetLoaderDecimalSet.init();
		long eTime = System.currentTimeMillis();
		System.out.println("Time for Init:" + (eTime - sTime));
		System.out.println("Heap Size = " + Runtime.getRuntime().totalMemory());
		long newheapSize = Runtime.getRuntime().totalMemory();
		System.out.println("Heap Used = " + (newheapSize - heapSize));

		sTime = System.currentTimeMillis();
		// 220.158.225.187 is part of list
		System.out.println("Searching for = 220.158.225.187 ");
		Pair<Boolean, List<ListMetaData>> pair = subnetLoaderDecimalSet.contains("220.158.225.187");
		assertEquals(true, pair.getKey());
		assertEquals(subnetLoaderDecimalSet.listmap.get(listId), pair.getValue().get(0));

		// 198.20.16.0/20 is part of list

		System.out.println("Searching for = 198.20.16.255 ");
		Pair<Boolean, List<ListMetaData>> pair2 = subnetLoaderDecimalSet.contains("198.20.16.255");
		assertEquals(true, pair2.getKey());
		assertEquals(subnetLoaderDecimalSet.listmap.get(listId), pair.getValue().get(0));

		// 1.1.1 is not part of list

		System.out.println("Searching for = 1.1.1.1 ");
		Pair<Boolean, List<ListMetaData>> pair3 = subnetLoaderDecimalSet.contains("1.1.1.1");
		assertEquals(false, pair3.getKey());
		assertEquals(0, pair3.getValue().size());

		System.out.println("Searching for = 224.1.0.0 ");
		Pair<Boolean, List<ListMetaData>> pair4 = subnetLoaderDecimalSet.contains("224.1.0.0");
		assertEquals(true, pair4.getKey());
		eTime = System.currentTimeMillis();
		System.out.println("Time for 4 Searchs:" + (eTime - sTime));

		Thread.sleep(200 * 1000);

	}

	private void runMapAndTrie() throws Exception {
		String listId = "src/main/resources/firehol_level1.netset";
		String listId4 = "src/main/resources/firehol_level4.netset";

		Thread.sleep(20 * 1000);

		long sTime = System.currentTimeMillis();
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("Heap Size = " + heapSize);
		subnetLoaderMapWithTrieStyle.init_Trie();
		long eTime = System.currentTimeMillis();
		System.out.println("Time for Init:" + (eTime - sTime));
		System.out.println("Heap Size = " + Runtime.getRuntime().totalMemory());
		long newheapSize = Runtime.getRuntime().totalMemory();
		

		sTime = System.currentTimeMillis();
		// 220.158.225.187 is part of list
		System.out.println("\nSearching for = 220.158.225.187 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair = subnetLoaderMapWithTrieStyle.contains("220.158.225.187");
		assertEquals(true, pair.getKey());

		// 198.20.16.0/20 is part of list
		System.out.println("\nSearching for = 198.20.16.255 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair2 = subnetLoaderMapWithTrieStyle.contains("198.20.16.255");
		assertEquals(true, pair2.getKey());

		// 1.1.1 is not part of list
		System.out.println("\nSearching for = 1.1.1.1 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair3 = subnetLoaderMapWithTrieStyle.contains("1.1.1.1");
		assertEquals(false, pair3.getKey());
		assertEquals(0, pair3.getValue().size());

		// 5.62.18.48
		System.out.println("\nSearching for = 5.62.18.48 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair4 = subnetLoaderMapWithTrieStyle.contains("5.62.18.48");
		assertEquals(true, pair4.getKey());

		// 225.0.0.0 should be rejected due to 224.0.0.0/3
		System.out.println("\nSearching for = 224.1.0.0 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair5 = subnetLoaderMapWithTrieStyle.contains("224.1.0.0");
		assertEquals(true, pair5.getKey());
		eTime = System.currentTimeMillis();
		System.out.println("Time for Query:" + (eTime - sTime));
		System.out.println("Heap Used = " + (newheapSize - heapSize));

		Thread.sleep(200 * 1000);
		
	}

	private void runTrie() throws Exception {
		String listId = "src/main/resources/firehol_level1.netset";
		String listId4 = "src/main/resources/firehol_level4.netset";

		Thread.sleep(20 * 1000);

		long sTime = System.currentTimeMillis();
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("Heap Size = " + heapSize);
		subnetLoaderTrie.init_Trie();
		long eTime = System.currentTimeMillis();
		System.out.println("Time for Init:" + (eTime - sTime));
		System.out.println("Heap Size = " + Runtime.getRuntime().totalMemory());
		long newheapSize = Runtime.getRuntime().totalMemory();

		sTime = System.currentTimeMillis();
		// 220.158.225.187 is part of list
		System.out.println("\nSearching for = 220.158.225.187 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair = subnetLoaderTrie.contains("220.158.225.187");
		assertEquals(true, pair.getKey());
		assertEquals(subnetLoaderTrie.listmap.get(listId), pair.getValue().get(0).getValue());

		// 198.20.16.0/20 is part of list
		System.out.println("\nSearching for = 198.20.16.255 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair2 = subnetLoaderTrie.contains("198.20.16.255");
		assertEquals(true, pair2.getKey());
		assertEquals(subnetLoaderTrie.listmap.get(listId), pair.getValue().get(0).getValue());

		// 1.1.1 is not part of list
		System.out.println("\nSearching for = 1.1.1.1 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair3 = subnetLoaderTrie.contains("1.1.1.1");
		assertEquals(false, pair3.getKey());
		assertEquals(0, pair3.getValue().size());

		// 5.62.18.48
		System.out.println("\nSearching for = 5.62.18.48 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair4 = subnetLoaderTrie.contains("5.62.18.48");
		assertEquals(true, pair4.getKey());
		assertEquals(subnetLoaderTrie.listmap.get(listId4), pair4.getValue().get(0).getValue());

		// 225.0.0.0 should be rejected due to 224.0.0.0/3
		System.out.println("\nSearching for = 224.1.0.0 ");
		Pair<Boolean, List<Pair<String, ListMetaData>>> pair5 = subnetLoaderTrie.contains("224.1.0.0");
		assertEquals(true, pair5.getKey());
		eTime = System.currentTimeMillis();
		
		System.out.println("Time for Query:" + (eTime - sTime));
		System.out.println("Heap Used = " + (newheapSize - heapSize));
		
		

	}
}