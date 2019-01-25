package com.itdan.shopmall.utils.result;

import java.io.Serializable;
import java.util.List;

/**
 * 商城后台响应的json数据格式包装类（分页）
 */
public class EasyUIDataGridResult implements Serializable{
	private Integer total;//总数

    private List<?> rows;//商品集合

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

    
}
