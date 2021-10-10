package com.example.SubnetUtils.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
class TrieNode {
    private @Getter @Setter Map<Integer, TrieNode> children = new HashMap<>();
    
    private @Getter @Setter Integer key;

	private @Getter @Setter List< Pair<String,String>> listOfSubnets;
	
	TrieNode(Integer key){
		this.key = key;
	}
    

}