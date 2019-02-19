package com.java4all.feign;

import java.math.BigDecimal;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:
 *
 * @author wangzhongxiang
 * @date 2019/2/13 17:25
 */
@FeignClient(value = "user4")
public interface UserServiceApi {

  @GetMapping(value = "/user/increaseMoney")
  void increaseMoney(@RequestParam("id") Integer id, @RequestParam("money") BigDecimal money);
}
