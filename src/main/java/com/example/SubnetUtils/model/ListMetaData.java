package com.example.SubnetUtils.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListMetaData {
	
	private @Getter @Setter String  maintainer;
	private @Getter @Setter String  MaintainerURL;
	private @Getter @Setter String  sourceFileDate;
	private @Getter @Setter String  category;
	private @Getter @Setter String  version;
	private @Getter @Setter String  thisFileDate ;
	private @Getter @Setter String  updateFrequency;
	private @Getter @Setter String  aggregation;
	private @Getter @Setter String  entriesSubnets;
	private @Getter @Setter String  entriesUniqueIPs;
}
