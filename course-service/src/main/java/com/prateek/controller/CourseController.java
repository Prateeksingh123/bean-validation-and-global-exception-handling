package com.prateek.controller;

import com.prateek.dto.CourseRequestDTO;
import com.prateek.dto.CourseResponseDTO;
import com.prateek.dto.ServiceResponse;
import com.prateek.service.CourseService;
import com.prateek.util.AppUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Slf4j
@RestController
@RequestMapping("/course")
public class CourseController {

    Logger log = LoggerFactory.getLogger(CourseController.class);

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Operation(summary = "add a new course to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "course added successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CourseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "validation error")
    })
    @PostMapping
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO course) {
//        validateRequestPayload(course);
        log.info("CourseController::addCourse Request payload => {}", AppUtils.convertObjectToJson(course));
        ServiceResponse<CourseResponseDTO> serviceResponse = new ServiceResponse<>();
        CourseResponseDTO newCourse = courseService.onboardNewCourse(course);
        serviceResponse.setStatus(HttpStatus.CREATED);
        serviceResponse.setResponse(newCourse);
        log.info("CourseController::addCourse response => {}", AppUtils.convertObjectToJson(serviceResponse));
        return serviceResponse;
    }

    @Operation(summary = "Find all course object")
    @GetMapping
    public ServiceResponse<List<CourseResponseDTO>> findAllCourse() {
        return new ServiceResponse<>(HttpStatus.OK, courseService.viewAllCourses());
    }

    @Operation(summary = "Find course by courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "course found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CourseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "course not found with given id")
    })
    @GetMapping("/search/path/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourse(@PathVariable Integer courseId) {
        return new ServiceResponse<>(HttpStatus.OK, courseService.findByCourseId(courseId));
    }

    @Operation(summary = "Find course by courseId using RequestParam")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "course found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CourseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "course not found with given id")
    })
    @GetMapping("/search/request")
    public ServiceResponse<CourseResponseDTO> findCourseUsingRequestParam(@RequestParam(required = false) Integer courseId) {
        return new ServiceResponse<>(HttpStatus.OK, courseService.findByCourseId(courseId));
    }

    @Operation(summary = "Delete course by courseId")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        if(log.isInfoEnabled()) {
            log.info("CourseController::deleteCourse deleting a course with id {}", courseId);
        }

        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "update the course in system")
    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId,
                                                           @RequestBody @Valid CourseRequestDTO course) {
//        validateRequestPayload(course);
        log.info("CourseController::updateCourse Request payload: {} and {}",
                AppUtils.convertObjectToJson(course), courseId);
        ServiceResponse<CourseResponseDTO> serviceResponse = new ServiceResponse<>(
                HttpStatus.OK, courseService.updateCourse(courseId, course));
        log.info("CourseController::updateCourse response => {}", AppUtils.convertObjectToJson(serviceResponse));

        return serviceResponse;
    }

    @GetMapping("/log")
    public String loggingLevel() {
        log.trace("trace message");
        log.debug("debug message");
        log.info("info message");
        log.warn("warn message");
        log.error("error message");

        return "log printed in console";
    }

//    private void validateRequestPayload(CourseRequestDTO courseRequestDTO) {
//        if(courseRequestDTO.getDuration() == null || courseRequestDTO.getDuration().isEmpty()) {
//            throw new RuntimeException("duration field must need to be pass");
//        }
//        if(courseRequestDTO.getFees() == 0) {
//            throw new RuntimeException("fees must be provided");
//        }
//    }
}
