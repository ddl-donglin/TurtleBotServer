package com.turtlebot.operation.service.tasks;

import com.turtlebot.operation.dao.IndentDAO;
import com.turtlebot.operation.dataobject.Indent;
import com.turtlebot.operation.service.trolleys.TrolleyService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 14:56
 * @Description:
 */
@Service
@Component
public class OrderTaskImpl implements OrderTask {

    @Resource
    private IndentDAO indentDAO;

    @Resource
    private TrolleyService trolleyService;

    static Logger logger = Logger.getLogger(OrderTaskImpl.class.getName());

    @Override
    public List<Indent> getNeedRecvIndents() {
        return indentDAO.getNeedRecvIndents();
    }

    @Override
    public List<Indent> getIndentList() {
        return indentDAO.getIndentList();
    }

    @Scheduled(cron="0/5 * * * * ? ")   //每5秒执行一次
    @Override
    public void sendIndent() {
        getIndentList().forEach(indent1 -> {
            if (indent1.getIsSend() == 0) {

                Integer trolleyId = trolleyService.orderDispatch(indent1); //此处是给小车派发任务的方法

                if (trolleyId != null) {
                    String message = "成功将 " + indent1.getOrderId() + " 号单派发至 " + trolleyId + " 号智能车执行！";
                    System.out.println(message);
                    logger.info(message);
                    indentDAO.sendIndent(indent1.getOrderId());
                }else {
                    System.out.println("系统出错！无法派发小车！");
                    logger.info("系统出错！无法派发小车！");
                }
            }else
                System.out.println("目前系统没有收到未派发出的订单！");
                logger.info("目前系统没有收到未派发出的订单！");
        });
    }
}
