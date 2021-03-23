package ru.asteac.blog.postmanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.asteac.blog.domain.model.entity.Post;
import ru.asteac.blog.domain.model.entity.User;

import java.util.List;

@Repository
public interface PostRepository extends GenericJpaRepository<Post> {

    @Query("select e from #{#entityName} e where e.deleteFlag=false")
    List<Post> getAllByUser(User user);

    @Query( value = "select * from posts p left join users u on posts.user_id = u.id inner join user_subscribers us on u.id = us.channel_id where us.subscriber_id = ? and p.deleteFlag = false",
            nativeQuery = true)
    List<Post> getAllBySubscription(Long userId);
}
