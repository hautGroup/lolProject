package daoTest;

import com.teljjb.bean.User;
import com.teljjb.dao.UserDao;
import com.teljjb.service.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by dezhonger on 2017/4/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestTest {

    @Autowired
    UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    public void t() {

//        User user1 = new User();
//        user1.setCreateTime(new Date());
//        user1.setUpdateTime(new Date());
//        user1.setBindPhone("13655812579");
//        user1.setEmail("373440955@qq.com");
//        user1.setHeadImage("= = ");
//        user1.setIsNotfiySign("N");
//        user1.setLastLoginIp("127.0.0.1");
//        user1.setLastLoginTime(new Date());
//        user1.setNickname("dezhonger");
//        user1.setStatus("lock");
//        userDao.insert(user1);
        User user = userDao.findUserById(1);
        System.out.println(user);
//        System.out.println(userService.findUserResultById(1));
    }




}