package com.springboot.demo03.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ProgZhou
 * @createTime 2022/04/09
 */
@TableName("tbl_test_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId
    private Integer id;

    private String username;

    private String password;

    private String role;

}
