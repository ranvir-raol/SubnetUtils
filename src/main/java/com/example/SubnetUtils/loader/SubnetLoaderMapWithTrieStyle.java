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

import edazdarevic.commons.net.CIDRUtils;

@Component
public class SubnetLoaderMapWithTrieStyle {

	// TRIE way of storing data
	public Map<String, List<Pair<String, String>>> map = new HashMap<>();
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
		
		System.out.println("Processing file :" + listId);
		processMap(listmap, listId);

		// process list data

		try (BufferedReader br = new BufferedReader(new FileReader(listId))) {
			String line;
			while ((line = br.readLine()) != null) {
				String subnetStr = line;
				if (!subnetStr.contains("/")) {
					subnetStr += "/32";
				}


				CIDRUtils cidrUtils = new CIDRUtils(subnetStr);
				for (String str : cidrUtils.identifySubnetRange()) {

					if (map.containsKey(str)) {
						List<Pair<String, String>> list = map.get(str);
						Pair<String, String> pair = new ImmutablePair<>(subnetStr, listId);
						list.add(pair);
						map.put(subnetStr, list);

					} else {
						List<Pair<String, String>> list = new ArrayList<>();
						Pair<String, String> pair = new ImmutablePair<>(subnetStr, listId);
						list.add(pair);
						map.put(str, list);
					}
				}
			}
		}

		System.out.println(this.getClass().getName() + " has size: " + map.size());
	}

	private List<Integer> provideOctets(String subnetStr) {
		List<Integer> list = new ArrayList<Integer>();

		String[] arr = subnetStr.split("\\.");
		for (String s1 : arr) {
			Integer i = Integer.parseInt(s1);
			list.add(i);
		}

		return list;
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
	public Pair<Boolean, List<Pair<String, ListMetaData>>> contains(String ip) throws UnknownHostException {

		List<Pair<String, ListMetaData>> listMetaData = new ArrayList<>();

		boolean isContained = false;
		StringBuffer sb = new StringBuffer();

		List<Integer> listInteger = provideOctets(ip);

		for (Integer octet : listInteger) {
			sb.append(octet).append(".");

			String key = sb.substring(0, sb.length() - 1);

			List<Pair<String, String>> list = map.get(key);
			if (list != null) {
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
			}

		}

		if (isContained) {

			return new ImmutablePair<Boolean, List<Pair<String, ListMetaData>>>(Boolean.TRUE, listMetaData);

		} else {
			return new ImmutablePair<Boolean, List<Pair<String, ListMetaData>>>(Boolean.FALSE, listMetaData);
		}

	}

}
