package hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * <code></code>
 *
 * @author masonhsieh
 * @version 1.0
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
