package com.bookstore.repository;

import com.bookstore.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Savek on 2017-04-17.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
}
