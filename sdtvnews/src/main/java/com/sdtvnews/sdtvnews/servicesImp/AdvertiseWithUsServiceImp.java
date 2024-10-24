package com.sdtvnews.sdtvnews.servicesImp;

import com.sdtvnews.sdtvnews.config.CustomException;
import com.sdtvnews.sdtvnews.dto.request.AdvertiseWithUsRequest;
import com.sdtvnews.sdtvnews.dto.response.AdvertiseWithUsResponse;
import com.sdtvnews.sdtvnews.entity.AdvertiseWithUs;
import com.sdtvnews.sdtvnews.repository.AdvertiseWithUsRepository;
import com.sdtvnews.sdtvnews.services.AdvertiseWithUsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertiseWithUsServiceImp implements AdvertiseWithUsService {

    private final AdvertiseWithUsRepository advertiseWithUsRepository;

    @Override
    public AdvertiseWithUsRequest createAdvertiseWithUs(AdvertiseWithUsRequest advertiseWithUsRequest) {
        // Check if the advertiseWithUs already exists
        List<AdvertiseWithUs> existingCategories = advertiseWithUsRepository.findAll();

        for (AdvertiseWithUs advertiseWithUs : existingCategories) {
            if (advertiseWithUs.getName().equalsIgnoreCase(advertiseWithUsRequest.getName())) {
                // Return a message if the advertiseWithUs already exists
                throw new CustomException("AdvertiseWithUs with the name '" + advertiseWithUsRequest.getName() + "' already exists.");
            }
        }

        // Create a new AdvertiseWithUs if it doesn't exist
        AdvertiseWithUs newAdvertiseWithUs = new AdvertiseWithUs();
        newAdvertiseWithUs.setName(advertiseWithUsRequest.getName());
        newAdvertiseWithUs.setDescription(advertiseWithUsRequest.getDescription());
        newAdvertiseWithUs.setStatus("1");//1=active;0=dis-active
        newAdvertiseWithUs.setCreateBy(advertiseWithUsRequest.getCreateBy());
        newAdvertiseWithUs.setCreateDate(LocalDateTime.now());
        newAdvertiseWithUs.setIndexShow(advertiseWithUsRequest.getIndexShow());
        // Save the new advertiseWithUs
        advertiseWithUsRepository.save(newAdvertiseWithUs);

        return advertiseWithUsRequest; // or convert and return the saved entity as needed
    }

    @Override
    public List<AdvertiseWithUsResponse> getAllAdvertiseWithUs() {
        List<AdvertiseWithUs> categories = advertiseWithUsRepository.findAll(); // Retrieve all advertiseWithUs from the repository
        List<AdvertiseWithUsResponse> advertiseWithUsResponses = new ArrayList<>();

        for (AdvertiseWithUs advertiseWithUs : categories) {
            AdvertiseWithUsResponse response = new AdvertiseWithUsResponse();
            response.setId(advertiseWithUs.getId());
            response.setName(advertiseWithUs.getName());
            response.setDescription(advertiseWithUs.getDescription());
            response.setStatus(advertiseWithUs.getStatus());
            response.setCreateDate(advertiseWithUs.getCreateDate());
            response.setIndexShow(advertiseWithUs.getIndexShow());
            // Set other necessary fields

            advertiseWithUsResponses.add(response);
        }

        return advertiseWithUsResponses;
    }

    @Override
    public Optional<AdvertiseWithUsResponse> getAdvertiseWithUsById(Long id) {
        // Retrieve the advertiseWithUs by its ID
        Optional<AdvertiseWithUs> advertiseWithUsOptional = advertiseWithUsRepository.findById(id);

        // Check if the advertiseWithUs exists
        if (advertiseWithUsOptional.isPresent()) {
            // Convert AdvertiseWithUs to AdvertiseWithUsResponse if it exists
            AdvertiseWithUs advertiseWithUs = advertiseWithUsOptional.get();
            AdvertiseWithUsResponse advertiseWithUsResponse = new AdvertiseWithUsResponse();
            advertiseWithUsResponse.setId(advertiseWithUs.getId());
            advertiseWithUsResponse.setName(advertiseWithUs.getName());
            advertiseWithUsResponse.setDescription(advertiseWithUs.getDescription());
            advertiseWithUsResponse.setStatus(advertiseWithUs.getStatus());
            advertiseWithUsResponse.setCreateDate(advertiseWithUs.getCreateDate());
            advertiseWithUsResponse.setIndexShow(advertiseWithUs.getIndexShow());
            // Set other necessary fields

            return Optional.of(advertiseWithUsResponse);
        } else {
            // Return an empty Optional if the advertiseWithUs doesn't exist
            return Optional.empty();
        }
    }

    @Override
    public void updateAdvertiseWithUs(Long id, AdvertiseWithUsRequest advertiseWithUsRequest) {
        // Find the advertiseWithUs by its ID
        Optional<AdvertiseWithUs> advertiseWithUsOptional = advertiseWithUsRepository.findById(id);

        if (advertiseWithUsOptional.isPresent()) {
            AdvertiseWithUs advertiseWithUs = advertiseWithUsOptional.get();
            // Update the advertiseWithUs details
            advertiseWithUs.setName(advertiseWithUsRequest.getName());
            advertiseWithUs.setDescription(advertiseWithUsRequest.getDescription());
            advertiseWithUs.setStatus(advertiseWithUsRequest.getStatus());
            advertiseWithUs.setIndexShow(advertiseWithUsRequest.getIndexShow());
            advertiseWithUs.setUpdateBy(advertiseWithUsRequest.getUpdateBy());
            advertiseWithUs.setUpdateDate(LocalDateTime.now());

            // Save the updated advertiseWithUs
            advertiseWithUsRepository.save(advertiseWithUs);
        } else {
            throw new CustomException("AdvertiseWithUs not found with ID: " + id);
        }
    }


    @Override
    public void updateAdvertiseWithUsStatus(Long id, String status) {
        Optional<AdvertiseWithUs> advertiseWithUsOptional = advertiseWithUsRepository.findById(id);

        if (advertiseWithUsOptional.isPresent()) {
            AdvertiseWithUs advertiseWithUs = advertiseWithUsOptional.get();
            advertiseWithUs.setStatus(status); // Update the status (e.g., "0" for inactive)
            advertiseWithUsRepository.save(advertiseWithUs); // Save the updated advertiseWithUs
        } else {
            throw new CustomException("AdvertiseWithUs not found with ID: " + id);
        }
    }

}
