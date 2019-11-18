/**
 * FileName: User
 * Author:   huang.yj
 * Date:     2019/11/12 17:11
 * Description: 用户实体类
 */
package com.sample.test.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 〈用户实体类〉
 *
 * @author huang.yj
 * @create 2019/11/12
 * @since 0.0.1
 */
@Entity // 告诉JPA这是一个实体类（和数据表映射的类）
@Table(name="test_user")  // @Table来指定和哪个数据表对应，如果省略默认表名就是该类名首字母小写；
public class User {

    @Id // 这是一个主键
    @GeneratedValue(strategy= GenerationType.IDENTITY)  // 自增主键
    private int id;

    private String password;

    private String remarks;

    @Column(name="user_name")
    private String userName;

    private String email;

    private String phone;

    private int status;

    @Column(name="isLocked", length = 1)
    private int isLocked;

    @Column(name="update_time")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    @Column(name="create_time")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}