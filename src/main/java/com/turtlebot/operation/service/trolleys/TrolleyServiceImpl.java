package com.turtlebot.operation.service.trolleys;

import com.turtlebot.operation.dataobject.Indent;
import com.turtlebot.operation.utils.SSHshell;
import org.springframework.stereotype.Service;

/**
 * @Auther: davidddl
 * @Date: 2018/6/12 15:24
 * @Description:
 */
@Service
public class TrolleyServiceImpl implements TrolleyService {

    private Double x = -2.58;
    private Double y = 0.23;
    private String host = "192.168.199.124";
    private String user = "turtlebot";
    private Integer port = 22;
    private String psw = "1012";
    private String command = "python /helloworld/turtlebot/go_to_specific_point_on_map.py " + x + " " + y;

    @Override
    public Integer orderDispatch(Indent indent) {

        String res = new SSHshell(host,user,psw,port,command).exec();
        System.out.println(res);
        return 1;
    }
}
