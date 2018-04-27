package com.cxq.springweb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryParam {
	
	private int pageSize;
	
	private int pageNumber;
	
	private String searchText;
	
	private String sortName;
	
	private String sortOrder;

}
