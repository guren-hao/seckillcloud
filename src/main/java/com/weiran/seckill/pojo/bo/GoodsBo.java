package com.weiran.seckill.pojo.bo;

import io.swagger.annotations.Api;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Api("商品业务对象")
public class GoodsBo {
	private Long id;
	private String goodsName;
	private String goodsTitle;
	private String goodsImg;
	private BigDecimal goodsPrice;
	private Integer goodsStock;
	private Date createDate;
	private Date updateDate;
	private String goodsDetail;

	private BigDecimal seckillPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;

}
