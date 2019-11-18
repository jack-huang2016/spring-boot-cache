/**
 * FileName: CacheController
 * Author:   huang.yj
 * Date:     2019/11/18 15:58
 * Description: 控制器
 */
package com.sample.test.web;

import com.sample.test.common.entity.User;
import com.sample.test.service.interfaces.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈控制器〉
 *
 * @author huang.yj
 * @create 2019/11/18
 * @since 0.0.1
 */
@RestController
public class CacheController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/put")
    public ResponseEntity<User> put(User user){
        try {
            User result = demoService.save(user);
            // 成功，响应200
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            // 出错，响应500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/able")
    public ResponseEntity<User> cacheable(User user){
        try {
            User result = demoService.findOne(user);
            // 成功，响应200
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            // 出错，响应500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping("/evit")
    public ResponseEntity<String> evit(int id){
        try {
            demoService.remove(id);
            // 成功，响应200
            return ResponseEntity.ok("缓存删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            // 出错，响应500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}