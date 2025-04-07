package sky.pro.Hogwarts31Test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.Hogwarts31Test.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(long studentId);
}
