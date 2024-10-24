package com.sdtvnews.sdtvnews.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdtvnews.sdtvnews.config.CustomException;
import com.sdtvnews.sdtvnews.config.exception.ResponseDTO;
import com.sdtvnews.sdtvnews.dto.request.AdvertiseWithUsRequest;
import com.sdtvnews.sdtvnews.dto.response.AdvertiseWithUsResponse;
import com.sdtvnews.sdtvnews.services.AdvertiseWithUsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertiseWithUs")
public class AdvertiseWithUsRestController {

    private static final Logger log = LoggerFactory.getLogger(AdvertiseWithUsRestController.class);
    private final ObjectMapper objectMapper;

    private final AdvertiseWithUsService advertiseWithUsService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAdvertiseWithUs(@RequestBody AdvertiseWithUsRequest request) {
        try {
            log.info("Request to create AdvertiseWithUs: {}", objectMapper.writeValueAsString(request));
            AdvertiseWithUsRequest createdAdvertiseWithUs = advertiseWithUsService.createAdvertiseWithUs(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("success", "AdvertiseWithUs created successfully", createdAdvertiseWithUs));
        } catch (CustomException e) {
            log.warn("AdvertiseWithUs creation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseDTO<>("error", e.getMessage(), request));
        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<?>> getAllAdvertiseWithUs() {
        try {
            log.info("Request to list all categories");
            List<AdvertiseWithUsResponse> responseList = advertiseWithUsService.getAllAdvertiseWithUs();
            if (responseList.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO<>("success", "No categories found.", responseList));
            }
            return ResponseEntity.ok(new ResponseDTO<>("success", "AdvertiseWithUs retrieved successfully", responseList));

        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<?>> getAdvertiseWithUsById(@RequestBody AdvertiseWithUsRequest request) {
        try {
            log.info("Request to find AdvertiseWithUs: {}", objectMapper.writeValueAsString(request));

            // Retrieve advertiseWithUs by ID
            Optional<AdvertiseWithUsResponse> response = advertiseWithUsService.getAdvertiseWithUsById(Long.valueOf(request.getId()));

            // Check if advertiseWithUs was found
            if (response.isPresent()) {
                return ResponseEntity.ok(new ResponseDTO<>("success", "AdvertiseWithUs found by ID successfully", response.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>("error", "AdvertiseWithUs not found.", null));
            }

        } catch (CustomException e) {
            log.warn("Error finding advertiseWithUs: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseDTO<>("error", e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<?>> updateAdvertiseWithUs(@RequestBody AdvertiseWithUsRequest request) {
        try {
            log.info("Request to update AdvertiseWithUs: {}", objectMapper.writeValueAsString(request));
            advertiseWithUsService.updateAdvertiseWithUs(Long.valueOf(request.getId()), request);
            return ResponseEntity.ok(new ResponseDTO<>("success", "AdvertiseWithUs updated successfully", null));
        } catch (CustomException e) {
            log.warn("Error updating advertiseWithUs: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseDTO<>("error", e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }


    @RequestMapping(value = "/update-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<?>> updateAdvertiseWithUsStatus(@RequestBody AdvertiseWithUsRequest request) {
        try {
            log.info("Request to update status for AdvertiseWithUs ID: {}", request.getId());
            advertiseWithUsService.updateAdvertiseWithUsStatus(request.getId(), request.getStatus());
            return ResponseEntity.ok(new ResponseDTO<>("success", "AdvertiseWithUs status updated successfully.", null));

        } catch (CustomException e) {
            log.warn("Error updating advertiseWithUs status: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseDTO<>("error", e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }

}
