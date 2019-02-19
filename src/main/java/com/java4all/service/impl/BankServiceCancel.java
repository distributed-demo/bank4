package com.java4all.service.impl;

import com.java4all.dao.BankDao;
import com.java4all.service.BankService;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description:
 * tcc cancel逻辑
 *
 * 1.全局事务决定回滚时，分支事务中可补偿型service的cancel逻辑不一定会被执行，
 * 原因是：参与该分支事务的Try方法可能抛出异常导致其本地事务回滚，因此该服务的Try操作是没有生效的；
 *
 * 2.全局事务决定回滚时，主事务中可补偿型service的cancel逻辑并不一定会被执行；
 * 原因是：主事务控制着全局事务的最终完成方向，当其最终决定回滚全局事务时，
 * 有机会通过将自己本地Try阶段的事务直接rollback来完成撤销try阶段操作，而不必通过cancel逻辑来实现。
 *
 * 3.cancel阶段仅负责本service的cancel逻辑，而不应该再执行远程调用。
 * 如果try阶段调用过远程服务，则事务上下文已传播至远程节点，全局事务回滚时，
 * 将由其所在节点的事务管理器负责执行cancel逻辑。
 *
 * @author wangzhongxiang
 * @date 2019/2/13 17:09
 */
@Service("bankServiceCancel")
public class BankServiceCancel implements BankService {

  private Logger log = LoggerFactory.getLogger(BankServiceCancel.class);

  @Autowired
  private BankDao bankDao;

  @Override
  @Transactional
  public int decreaseMoney(Integer id, BigDecimal money) {
    int line = bankDao.cancelDecreaseMoney(id, money);
    log.info("【cancel】 decreaseMoney: id = "+id+",money ="+money);
    return line;
  }
}
