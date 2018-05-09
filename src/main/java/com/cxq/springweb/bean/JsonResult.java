package com.cxq.springweb.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * bootstrap table一般需要的服务端返回的数据格式中含有"total"和"rows"字段，bootstrap table根据这俩个字段去填充表格
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {
	
	private List rows;
	
	private long total;

}
