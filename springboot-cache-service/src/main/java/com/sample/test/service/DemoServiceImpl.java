/**
 * FileName: DemoServiceImpl
 * Author:   huang.yj
 * Date:     2019/11/18 15:26
 * Description: 实现类
 */
package com.sample.test.service;

import com.sample.test.common.entity.User;
import com.sample.test.common.exception.ServiceException;
import com.sample.test.dao.UserDao;
import com.sample.test.service.interfaces.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * 〈实现类〉
 *
 * @author huang.yj
 * @create 2019/11/18
 * @since 0.0.1
 */
@Service
public class DemoServiceImpl implements DemoService{
    @Autowired
    private UserDao userDao;

    @Override
    @CachePut(value = "userCache",key = "#user.id")
    public User save(User user) throws ServiceException {

        try {
            User u = userDao.save(user);
            System.out.println("为key为："+user.getId()+"数据做了缓存");
            return u;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @CacheEvict(value = "userCache")
    public void remove(int id) throws ServiceException {
        System.out.println("删除缓存");
        try {
            userDao.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Cacheable(value = "userCache",key = "#user.id",unless = "#result == null")
    public User findOne(User user) throws ServiceException {
        try {
            Optional<User> op = userDao.findById(user.getId());
            if (op.isPresent()){ //如果值存在
                System.out.println("为key为："+user.getId()+"数据做了缓存");
                return op.get();
            }

            return null;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}