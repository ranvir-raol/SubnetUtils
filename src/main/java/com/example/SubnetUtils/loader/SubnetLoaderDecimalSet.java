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
import com.example.SubnetUtils.model.SubnetDetails;

import edazdarevic.commons.net.CIDRUtils;

@Component
public class SubnetLoaderDecimalSet {

	// Map way of storing data
	public Map<Long, List<SubnetDetails>> map = new HashMap<>();
	
	public Map<String, ListMetaData> listmap = new HashMap<>();

//	@PostConstruct
	public void init() throws FileNotFoundException, IOException {
		String listId = "src/main/resources/firehol_level4.netset";
		// process list metadata
		processMap(listmap, listId);

		// process list data

		long count = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(listId))) {
			String line;
			while ((line = br.readLine()) != null) {

				String subnetStr = line;
				if (!subnetStr.contains("/")) {
					subnetStr += "/32";
				}
				
				count++;

				CIDRUtils cidrUtils = new CIDRUtils(subnetStr);
				long startRange = ipToLong(cidrUtils.getStartAddress().getHostAddress());
				long endRange = ipToLong(cidrUtils.getEndAddress().getHostAddress());

				
//				SubnetDetails value = new SubnetDetails(subnetStr, listId);

				for (long key = startRange; key <= endRange; key++) {
					
					if (!map.containsKey(key)) {
						List<SubnetDetails> list = new ArrayList<>();
						
						//TODO : Poppulate list
						
						map.put(key, list);
					}
				}

			}
		}
		
		System.out.println("Finished processing  :" + count +", total:"+ map.size() );
		
		
	}

	/**
	 * Convert IP Address to Long
	 * 
	 * @param ipAddress
	 * @return
	 */
	private long ipToLong(String ipAddress) {

		String[] ipAddressInArray = ipAddress.split("\\.");

		long result = 0;
		for (int i = 0; i < ipAddressInArray.length; i++) {

			int power = 3 - i;
			int ip = Integer.parseInt(ipAddressInArray[i]);
			result += ip * Math.pow(256, power);

		}

		return result;
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
	public Pair<Boolean, List<ListMetaData>> contains(String ip) throws UnknownHostException {
		Long key = ipToLong(ip);
		
		boolean isContained = false;
		List<ListMetaData> list = new ArrayList<ListMetaData>();
		if (map.containsKey(key)) {
			isContained= true;
//			for (SubnetDetails subnetDetails : map.get(key)) {
//				CIDRUtils utils = new CIDRUtils(subnetDetails.subnetStr);
//				if (utils.isInRange(ip)) {
//					isContained = true;
//					list.add(listmap.get(subnetDetails.listId));
//
//				}
//			}
		}

		if (isContained) {

			return new ImmutablePair<Boolean, List<ListMetaData>>(Boolean.TRUE, list);

		} else {
			return new ImmutablePair<Boolean, List<ListMetaData>>(Boolean.FALSE, list);
		}

	}

}
