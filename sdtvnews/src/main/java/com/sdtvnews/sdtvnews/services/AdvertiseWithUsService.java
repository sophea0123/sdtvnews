package com.sdtvnews.sdtvnews.services;

import com.sdtvnews.sdtvnews.dto.request.AdvertiseWithUsRequest;
import com.sdtvnews.sdtvnews.dto.response.AdvertiseWithUsResponse;

import java.util.List;
import java.util.Optional;

public interface AdvertiseWithUsService {

    AdvertiseWithUsRequest createAdvertiseWithUs(AdvertiseWithUsRequest request);

    List<AdvertiseWithUsResponse>getAllAdvertiseWithUs();

    Optional<AdvertiseWithUsResponse>getAdvertiseWithUsById(Long id);

    void updateAdvertiseWithUs(Long id, AdvertiseWithUsRequest request);

    void updateAdvertiseWithUsStatus(Long id, String status);

}
