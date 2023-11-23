package lottery.draw.springboot.mapper;

import lottery.draw.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liuxiang
 * @since 2023-10-24
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from tb_user where username=#{name}")
    User getSaltByName(String name);
}
