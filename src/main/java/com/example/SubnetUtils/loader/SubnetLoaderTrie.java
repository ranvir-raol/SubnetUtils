package com.example.SubnetUtils.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import com.example.SubnetUtils.model.ListMetaData;
import com.example.SubnetUtils.model.Trie;

import edazdarevic.commons.net.CIDRUtils;

@Component
public class SubnetLoaderTrie {

	// TRIE way of storing data
	public Trie trie = new Trie();
	public Map<String, ListMetaData> listmap = new HashMap<>();

//	@PostConstruct
	public void init_Trie() throws FileNotFoundException, IOException {
		String listId = "src/main/resources/firehol_level1.netset";
		process(listId);

		listId = "src/main/resources/firehol_level2.netset";
		process(listId);

		listId = "src/main/resources/firehol_level3.netset";
		process(listId);

		listId = "src/main/resources/firehol_level4.netset";
		process(listId);
	}

	private void process(String listId) throws IOException, UnknownHostException, FileNotFoundException {
		// process list metadata
		processMap(listmap, listId);

		// process list data

		try (BufferedReader br = new BufferedReader(new FileReader(listId))) {
			String line;
			while ((line = br.readLine()) != null) {
				String subnetStr = line;
				if (!subnetStr.contains("/")) {
					subnetStr += "/32";
				}

				if (line.equals("224.0.0.0/10")) {
					System.out.println();
				}

				CIDRUtils cidrUtils = new CIDRUtils(subnetStr);
				for (String str : cidrUtils.identifySubnetRange()) {
					trie.insert(str, subnetStr, listId);
				}

			}
		}
	}

	/**
	 * Temporary initialize map
	 * 
	 * @param listMap
	 * @param listId
	 */
	private void processMap(Map<String, ListMetaData> listMap, String listId) {

		ListMetaData listMetaData = new ListMetaData();
		listMetaData.setAggregation("none");
		listMetaData.setCategory("attacks");
		listMetaData.setEntriesSubnets("2873");
		listMetaData.setEntriesUniqueIPs("619824346");
		listMetaData.setMaintainer("FireHOL");
		listMetaData.setMaintainerURL("http://iplists.firehol.org/");
		listMetaData.setSourceFileDate("Thu Oct  7 01:27:46 UTC 2021");
		listMetaData.setThisFileDate("Thu Oct  7 02:00:23 UTC 2021");
		listMetaData.setUpdateFrequency("1 min");
		listMetaData.setVersion("26390");

		listMap.put(listId, listMetaData);

	}

	/**
	 * isContained
	 * 
	 * @param ip
	 * @return
	 * @throws UnknownHostException
	 */
	public Pair<Boolean, List<Pair<String,ListMetaData>>> contains(String ip) throws UnknownHostException {

		List<Pair<String, String>> list = trie.listOfSubnets(ip);
		List<Pair<String, ListMetaData>> listMetaData = new ArrayList<>();

		boolean isContained = false;
		for (Pair<String, String> pair : list) {
			String subnetStr = pair.getKey();
			String listId = pair.getValue();

			System.out.println("Looking at range: " + subnetStr + " and ip " + ip);
			CIDRUtils utils = new CIDRUtils(subnetStr);
			if (utils.isInRange(ip)) {
				isContained = true;
				Pair<String, ListMetaData> p = new ImmutablePair<>(subnetStr, listmap.get(listId));
				listMetaData.add(p);
			}
		}

		if (isContained) {

			return new ImmutablePair<Boolean, List<Pair<String,ListMetaData>>>(Boolean.TRUE, listMetaData);

		} else {
			return new ImmutablePair<Boolean, List<Pair<String,ListMetaData>>>(Boolean.FALSE, listMetaData);
		}

	}

}
