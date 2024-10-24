package com.sdtvnews.sdtvnews.repository;

import com.sdtvnews.sdtvnews.entity.AdvertiseWithUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiseWithUsRepository extends JpaRepository<AdvertiseWithUs,Long> {

}
