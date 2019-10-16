package cn.edu.ncu.meeting.user.repo;

import cn.edu.ncu.meeting.user.model.User;
import cn.edu.ncu.meeting.user.model.UserKey;
import org.springframework.data.repository.CrudRepository;

/**
 * User Repository
 * @author lorry
 * @author lin864464995@163.com
 * @see org.springframework.data.repository.CrudRepository
 */
public interface UserRepository extends CrudRepository<User, UserKey> {
    User findById(Integer id);

    User findByUsername(String username);
}
