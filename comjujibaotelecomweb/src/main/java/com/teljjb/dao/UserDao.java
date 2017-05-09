package com.teljjb.dao;

import com.teljjb.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * This tools just a simple useful tools, gen table to javabean
 *
 * "I hope this tools can save your time"
 *
 * Generated by <tt>Generate</tt> 
 *
 * @author com.ypn.mapi.generate
 * @version v1.0
 */
@Repository
public interface UserDao {

	public User findUserById(Integer id);

	public User findUserByNickname(String nickname);

	public User findUserByEmailForupdate(String email);

	public User findUserByNicknameForupdate(String nickname);

	public Integer insert(User param);
	
	public Integer getUserCount(User param);
	
	public List<User> findUsersByPage(@Param("param") User param, @Param("offset") Integer offset, @Param("rows") Integer rows);
	
	//if not use,pls delete it
	public Integer deleteUserById(Integer id);


    public Integer updateHeadImage(@Param("userId") Integer userId, @Param("headImage") String headImage);

    public Integer updateStatus(@Param("userId") Integer userId, @Param("status") String status);
}