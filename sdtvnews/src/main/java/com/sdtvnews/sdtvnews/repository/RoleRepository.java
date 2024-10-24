package com.sdtvnews.sdtvnews.repository;

import com.sdtvnews.sdtvnews.entity.Follow;
import com.sdtvnews.sdtvnews.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

}
