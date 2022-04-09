package com.springboot.demo03.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.demo03.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ProgZhou
 * @createTime 2022/04/09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
