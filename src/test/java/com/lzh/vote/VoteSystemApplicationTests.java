package com.lzh.vote;

import com.lzh.vote.entity.req.UserLoginReq;
import com.lzh.vote.entity.res.UserRes;
import com.lzh.vote.result.Result;
import com.lzh.vote.service.IUserService;
import com.lzh.vote.utils.Base64Util;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

@SpringBootTest
@AllArgsConstructor
class VoteSystemApplicationTests {
    private IUserService userService;


    @Test
    void contextLoads() {
    }

    @Test
    public void regexTest() {
        String content = "M688108(1)";

        String pattern = "^[A-Z]\\d{6}\\(\\d\\)$";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }

    @Test
    public void encryptTest() throws Exception {
        String s = "123456";
        System.out.println(Base64Util.encode(s));
        System.out.println(Base64Util.decode("MTIzNDU2dg=="));
    }
    @Test
    public void loginTest()  {
        UserLoginReq loginReq = new UserLoginReq();
        loginReq.setIsAdmin(1);
        loginReq.setPassword("admin");
        loginReq.setUserName("admin");
        Result<UserRes> login = userService.login(loginReq);
        System.out.println(login.toString());
    }

    public static void main(String[] args) {
        String content = "769339438";

        String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);
    }
}
