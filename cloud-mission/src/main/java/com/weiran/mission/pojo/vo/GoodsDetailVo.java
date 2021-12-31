package com.weiran.mission.pojo.vo;

import com.weiran.mission.pojo.bo.GoodsBo;
import com.weiran.mission.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品细节页面对象")
public class GoodsDetailVo extends BaseVo{

    @ApiModelProperty(value = "秒杀时间状态值", required = true, dataType = "int")
    private int seckillStatus = 0;
    @ApiModelProperty(value = "剩余时间", required = true, dataType = "int")
    private int remainSeconds = 0;
    @ApiModelProperty(value = "商品业务对象", required = true, dataType = "GoodsBo")
    private GoodsBo goodsBo;
    @ApiModelProperty(value = "用户持久化对象", required = true, dataType = "User")
    private User user;
}
