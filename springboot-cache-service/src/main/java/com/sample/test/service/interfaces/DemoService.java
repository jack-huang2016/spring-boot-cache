package com.sample.test.service.interfaces;

import com.sample.test.common.entity.User;
import com.sample.test.common.exception.ServiceException;

public interface DemoService {
    public User save(User user) throws ServiceException;

    public void remove(int id) throws ServiceException;

    public User findOne(User user) throws ServiceException;
}
