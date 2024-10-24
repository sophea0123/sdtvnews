package com.sdtvnews.sdtvnews.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdtvnews.sdtvnews.config.CustomException;
import com.sdtvnews.sdtvnews.config.exception.ResponseDTO;
import com.sdtvnews.sdtvnews.dto.request.DepartmentRequest;
import com.sdtvnews.sdtvnews.dto.response.DepartmentResponse;
import com.sdtvnews.sdtvnews.services.DepartmentService;
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
@RequestMapping("/api/department")
public class DepartmentRestController {

    private static final Logger log = LoggerFactory.getLogger(DepartmentRestController.class);
    private final ObjectMapper objectMapper;

    private final DepartmentService departmentService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentRequest request) {
        try {
            log.info("Request to create Department: {}", objectMapper.writeValueAsString(request));
            DepartmentRequest createdDepartment = departmentService.createDepartment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("success", "Department created successfully", createdDepartment));
        } catch (CustomException e) {
            log.warn("Department creation failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseDTO<>("error", e.getMessage(), request));
        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<?>> getAllDepartment() {
        try {
            log.info("Request to list all department");
            List<DepartmentResponse> responseList = departmentService.getAllDepartment();
            if (responseList.isEmpty()) {
                return ResponseEntity.ok(new ResponseDTO<>("success", "No department found.", responseList));
            }
            return ResponseEntity.ok(new ResponseDTO<>("success", "Department retrieved successfully", responseList));

        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<?>> getDepartmentById(@RequestBody DepartmentRequest request) {
        try {
            log.info("Request to find Department: {}", objectMapper.writeValueAsString(request));

            // Retrieve department by ID
            Optional<DepartmentResponse> response = departmentService.getDepartmentById(Long.valueOf(request.getId()));

            // Check if department was found
            if (response.isPresent()) {
                return ResponseEntity.ok(new ResponseDTO<>("success", "Department found by ID successfully", response.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<>("error", "Department not found.", null));
            }

        } catch (CustomException e) {
            log.warn("Error finding department: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseDTO<>("error", e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<?>> updateDepartment(@RequestBody DepartmentRequest request) {
        try {
            log.info("Request to update Department: {}", objectMapper.writeValueAsString(request));
            departmentService.updateDepartment(Long.valueOf(request.getId()), request);
            return ResponseEntity.ok(new ResponseDTO<>("success", "Department updated successfully", null));
        } catch (CustomException e) {
            log.warn("Error updating department: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseDTO<>("error", e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }


    @RequestMapping(value = "/update-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO<?>> updateDepartmentStatus(@RequestBody DepartmentRequest request) {
        try {
            log.info("Request to update status for Department ID: {}", request.getId());
            departmentService.updateDepartmentStatus(request.getId(), request.getStatus());
            return ResponseEntity.ok(new ResponseDTO<>("success", "Department status updated successfully.", null));

        } catch (CustomException e) {
            log.warn("Error updating department status: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseDTO<>("error", e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error processing request: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>("error", "An unexpected error occurred.", null));
        }
    }

}
