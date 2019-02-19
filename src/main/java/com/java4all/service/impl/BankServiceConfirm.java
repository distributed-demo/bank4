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
 * tcc confirm逻辑
 * 1.全局事务决定提交时，可补偿型service的confirm逻辑总是会被执行；
 *
 * 2.全局事务决定提交时，可能会存在某个分支事务try操作没有执行成功的情况，
 * 此时该分支的confirm逻辑仍然会被调用。存在该情况的原因是：分支事务执行出错并抛出异常（如ServiceException），
 * 其业务逻辑通过Transactional定义了该异常应该回滚事务（或容器通过判断其异常类型最终决定回滚），
 * 因而导致分支的try阶段操作没有生效；然而发起方捕捉到了分支抛出的异常，此时如果发起方可以处理分支执行出错的逻辑，
 * 则不再向外抛出异常；最终发起方的容器认为执行成功，并决定提交全局事务，因此就会通知分支事务管理器提交分支事务，
 * 而分支事务会回调分支事务中涉及的所有service的confirm逻辑。
 *
 * 3.confirm逻辑被回调时，若不确定try阶段事务是否成功执行，
 * 则可以通过CompensableContext.isCurrentCompensableServiceTried()来确定。
 *
 * 4.confirm阶段仅负责本service的confirm逻辑，而不应该再执行远程调用。
 * 如果try阶段调用过远程服务，则事务上下文已传播至远程节点，全局事务提交时，
 * 将由其所在节点的事务管理器负责执行confirm逻辑。
 *
 * @author wangzhongxiang
 * @date 2019/2/13 17:08
 */
@Service("bankServiceConfirm")
public class BankServiceConfirm implements BankService {

  private Logger log = LoggerFactory.getLogger(BankServiceConfirm.class);

  @Autowired
  private BankDao bankDao;

  @Override
  @Transactional
  public int decreaseMoney(Integer id, BigDecimal money) {
    int line = bankDao.confirmDecreaseMoney(id, money);
    log.info("【confirm】 decreaseMoney: id = "+id+",money ="+money);
    return line;
  }
}
