package com.sdtvnews.sdtvnews.repository;

import com.sdtvnews.sdtvnews.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

    boolean existsByName(String name);

}
