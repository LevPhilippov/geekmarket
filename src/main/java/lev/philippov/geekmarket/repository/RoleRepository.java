package lev.philippov.geekmarket.repository;

import lev.philippov.geekmarket.Model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
