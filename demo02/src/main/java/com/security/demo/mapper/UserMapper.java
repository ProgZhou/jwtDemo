package com.security.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.demo.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ProgZhou
 * @createTime 2022/04/07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
