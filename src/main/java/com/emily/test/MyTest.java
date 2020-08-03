package com.emily.test;

//配置spring和junit整合，这样junit在启动时就会加载spring容器

import com.emily.pojo.User;
import com.emily.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:applicationContext.xml","classpath:springMVC.xml"})
public class MyTest {
    @Autowired
    private UserService userService;
    @Test
    public void select() throws Exception{


    }
}
