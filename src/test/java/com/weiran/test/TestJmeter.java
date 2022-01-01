package com.weiran.test;//package com.weiran.seckill.test;
//
//import com.weiran.seckill.common.enums.RedisCacheTimeEnum;
//import com.weiran.seckill.common.obj.CodeMsg;
//import com.weiran.seckill.common.obj.Result;
//import com.weiran.seckill.redis.key.GoodsKey;
//import com.weiran.seckill.redis.key.SeckillKey;
//import com.weiran.seckill.redis.key.UserKey;
//import com.weiran.seckill.redis.manager.RedisService;
//import com.weiran.seckill.common.utils.MD5Util;
//import com.weiran.seckill.pojo.bo.GoodsBo;
//import com.weiran.seckill.pojo.entity.SeckillOrder;
//import com.weiran.seckill.pojo.entity.User;
//import com.weiran.seckill.rabbitmq.BasicPublisher;
//import com.weiran.seckill.rabbitmq.SeckillMessage;
//import com.weiran.seckill.service.GoodsService;
//import com.weiran.seckill.service.SeckillOrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.List;
//import java.util.UUID;
//
///**
// * Jmeter测试
// */
//@Controller
//@RequiredArgsConstructor
//public class TestJmeter {
//
//    final RedisService redisService;
//    final SeckillOrderService seckillOrderService;
//    final GoodsService goodsService;
//    final BasicPublisher basicPublisher;
//
//    // 内存标记，减少redis访问
//    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();
//
//    /**
//     * 系统初始化，在redis中加载，每件商品还剩多少件。com.weiran.seckill.redis。
//     */
//    @PostConstruct
//    public void initSeckillGoodsNumber() {
//        List<GoodsBo> goodsList = goodsService.selectAllGoods();
//        for (GoodsBo goods : goodsList) {
//            redisService.set(GoodsKey.getSeckillGoodsStock, "" + goods.getId(), goods.getStockCount(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
//            localOverMap.put(goods.getId(), true);
//        }
//    }
//
//    @RequestMapping("/test")
//    @ResponseBody
//    public Result<Integer> test(@RequestParam("id") long id) {
//
//        return doTest(id);
//    }
//
//    private Result<Integer> doTest(long num) {
//        User user = new User();
//        user.setId(num);
//        String userName = "user" + num;
//        user.setUserName(userName);
//        String token = userName;
//        redisService.set(UserKey.getByName, token, user, 120);
//        String path = getSeckillPath(token, 1);
//        if (path == null) {
//            throw new RuntimeException("秒杀URL生成失败");
//        }
//        return doSeckill(user, 1, path);
//    }
//
//    private String getSeckillPath(String token, long goodsId) {
//        User user = redisService.get(UserKey.getByName, token, User.class);
//        if (user == null) {
//            return null;
//        }
//        String path = createSeckillPath(user, goodsId);
//        return path;
//    }
//
//    // 进行秒杀
//    private Result<Integer> doSeckill(User user, long goodsId, String path) {
//        // 验证path
//        boolean check = checkPath(user, goodsId, path);
//        if (!check) {
//            return Result.error(CodeMsg.REQUEST_ILLEGAL);
//        }
//        // 若为非，则为商品已经售完
//        boolean over = localOverMap.get(goodsId);
//        if (!over) {
//            return Result.error(CodeMsg.SECKILL_OVER);
//        }
//        // 查询剩余数量
//        int stock = redisService.get(GoodsKey.getSeckillGoodsStock, "" + goodsId, Integer.class);
//        if (stock <= 0) {
//            localOverMap.put(goodsId, false);
//            return Result.error(CodeMsg.SECKILL_OVER);
//        }
//        // 判断是否已经秒杀到了, 防止重复秒杀
//        SeckillOrder seckillOrder = seckillOrderService.selectByUserIdAndGoodsId(user.getId() , goodsId);
//        if (seckillOrder != null) {
//            return Result.error(CodeMsg.REPEATED_SECKILL);
//        }
//        // 预减库存
//        redisService.decrease(GoodsKey.getSeckillGoodsStock, "" + goodsId);
//
//        // 入队
//        SeckillMessage seckillMessage = new SeckillMessage();
//        seckillMessage.setUser(user);
//        seckillMessage.setGoodsId(goodsId);
////        // 判断库存、判断是否已经秒杀到了和减库存 下订单 写入秒杀订单都由RabbitMQ来执行，做到削峰填谷
//        basicPublisher.sendMsg(seckillMessage);
//
////        // 不用MQ
////        GoodsBo goodsBo = goodsService.getGoodsBoByGoodsId(goodsId);
////        // 减库存 下订单 写入秒杀订单
////        seckillOrderService.insertByUserAndGoodsBo(user, goodsBo);
//
//        return Result.success(0); // 排队中
//    }
//
//    // 加盐生成唯一path，构成URl动态化
//    private String createSeckillPath(User user, long goodsId) {
//        if (user == null || goodsId <= 0) {
//            return null;
//        }
//        // 随机返回一个唯一的id，加上123456的盐，然后md5加密
//        String str = MD5Util.md5(UUID.randomUUID() + "123456");
//        redisService.set(SeckillKey.getSeckillPath, "" + user.getId() + "_" + goodsId, str, RedisCacheTimeEnum.GOODS_ID_EXTIME.getValue());
//        return str;
//    }
//
//    // 在redis里验证path
//    private boolean checkPath(User user, long goodsId, String path) {
//        if (user == null || path == null) {
//            return false;
//        }
//        String redis_path = redisService.get(SeckillKey.getSeckillPath, "" + user.getId() + "_"+ goodsId, String.class);
//        return path.equals(redis_path);
//    }
//
//}
