package ru.andreev.blog.postmanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.andreev.blog.domain.model.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
