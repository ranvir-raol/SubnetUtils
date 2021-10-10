package com.example.SubnetUtils.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Trie {
	public TrieNode root;

	public Trie() {
		root = new TrieNode(-1);
	}

	void print(String subnetStr) {

	}

	public void insert(String octets, String subnetStr, String listID) {
		TrieNode current = root;
//		System.out.println(" Adding "+ octets + " for , "+ subnetStr);

		List<Integer> list = provideOctets(octets);

		int index = 0;
		for (Integer octet : list) {
			if (current.getChildren().get(octet) != null) {
				current = current.getChildren().get(octet);
			} else {
				insert(current, list, index, octets, subnetStr,  listID);
				break;
			}
			index++;
		}
	}

	private void insert(TrieNode current, List<Integer> list, int index, String octets, String subnetStr,
			String listID) {

		for (int i = index; i < list.size(); i++) {
			Map<Integer, TrieNode> map = current.getChildren();
			Integer key = list.get(i);
			TrieNode newNode = new TrieNode(key);
			List<Pair<String, String>> subnetList = new ArrayList<>();

			if (i == list.size() - 1) {
				Pair<String, String> pair = new ImmutablePair<String, String>(subnetStr, listID);
				subnetList.add(pair);
			}
			newNode.setListOfSubnets(subnetList);
			map.put(key, newNode);

//			System.out.println("\t\t Adding " + key + " , " + subnetStr);

			current = newNode;
		}

	}

	public List<Pair<String, String>> listOfSubnets(String ipAddress) {
		TrieNode current = root;
		List<Integer> list = provideOctets(ipAddress);

		List<Pair<String, String>> returnList = new ArrayList<>();

		for (Integer octet : list) {
			if (current.getChildren().containsKey(octet)) {

				returnList.addAll(current.getChildren().get(octet).getListOfSubnets());
				current = current.getChildren().get(octet);
			} else {
				break;
			}
		}

		return returnList;
	}

	private List<String> getListOfSubnets(List<Pair<String, String>> list) {
		List<String> returnList = new ArrayList<String>();

		for (Pair<String, String> pair : list) {
			returnList.add(pair.getKey());
		}

		return returnList;
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

	public void printTrie(TrieNode node, int offset) {
//        print(node, offset)
		String buffer = "\t";
		for (int i = 0; i < offset; i++) {
			buffer += "\t";
		}
		System.out.println(buffer + node.getKey() + ":" + node.getListOfSubnets());

		// here you can play with the order of the children
		for (TrieNode child : node.getChildren().values()) {
			printTrie(child, offset + 2);
		}
	}

}