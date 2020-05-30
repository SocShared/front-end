package ml.socshared.frontend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ml.socshared.frontend.domain.TestObject;

@Repository
public interface TestRepository extends JpaRepository<TestObject, Integer> {
}
