package ru.asteac.blog.postmanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericJpaRepository<T> extends JpaRepository<T,Long> {

    @Override
    @Query("select e from #{#entityName} e where e.deleteFlag=false")
    Optional<T> findById(@NonNull Long id);

    @Query("select e from #{#entityName} e where e.deleteFlag=true")
    Optional<T> findByIdDeleted(@NonNull Long id);

    @Override
    @Query("select e from #{#entityName} e where e.deleteFlag=false")
    List<T> findAll();

    @Query("select e from #{#entityName} e where e.deleteFlag=true")
    List<T> findAllDeleted();

    @Query("update #{#entityName} e set e.deleteFlag=true where e.id=?1")
    @Modifying
    void softDelete(Long id);

    @Query("update #{#entityName} e set e.deleteFlag=false where e.id=?1")
    @Modifying
    void recover(Long id);
}
