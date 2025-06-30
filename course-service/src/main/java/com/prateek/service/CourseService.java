package com.prateek.service;

import com.prateek.dto.CourseRequestDTO;
import com.prateek.dto.CourseResponseDTO;
import com.prateek.entity.CourseEntity;
import com.prateek.exception.CourseServiceBusinessException;
import com.prateek.repository.CourseDao;
import com.prateek.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CourseService {

    Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseDao courseDao;

    // create course object in DB
    public CourseResponseDTO onboardNewCourse(CourseRequestDTO courseRequestDTO) {
        CourseEntity courseEntity = AppUtils.mapDTOToEntity(courseRequestDTO);
        CourseEntity savedCourse = null;
        log.info("CourseService::onboardNewCourse execution started.");
        try {
            savedCourse = courseDao.save(courseEntity);
            log.debug("course entity response from Database {}", AppUtils.convertObjectToJson(savedCourse));

            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(savedCourse);
            courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
            log.debug("CourseService::onboardNewCourse method response {}", AppUtils.convertObjectToJson(courseResponseDTO));

            log.info("CourseService::onboardNewCourse method execution ended.");
            return courseResponseDTO;
        } catch (Exception ex) {
            log.error("CourseService::onboardNewCourse exception occurs while persisting the course object to DB", ex);
            throw new CourseServiceBusinessException("onboardNewCourse service method failed...");
        }
    }

    // load all the course from Database
    public List<CourseResponseDTO> viewAllCourses() {
        Iterable<CourseEntity> courseEntities = null;
        try {
            courseEntities = courseDao.findAll();
            return StreamSupport.stream(courseEntities.spliterator(), false)
                    .map(AppUtils::mapEntityToDTO)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new CourseServiceBusinessException("viewAllCourses service method failed...");
        }
    }

    // filter course by course id
    public CourseResponseDTO findByCourseId(Integer courseId) {
        return courseDao.findById(courseId)
                .map(AppUtils::mapEntityToDTO)
                .orElseThrow(() -> new CourseServiceBusinessException(courseId + " is not valid course id"));
    }

    // delete course
    public void deleteCourse(int courseId) {
        log.info("CourseService::deleteCourse method execution started...");
        try {
            log.debug("CourseService::deleteCourse method input {}", courseId);
            Optional<CourseEntity> optionalCourseEntity = courseDao.findById(courseId);
            if(optionalCourseEntity.isPresent()) {
                courseDao.delete(optionalCourseEntity.get());
            } else {
                log.error("CourseService::deleteCourse, courseId: {} is not present in system", courseId);
                throw new CourseServiceBusinessException(courseId + " is not valid course id");
            }
        } catch (Exception ex) {
            log.error("CourseService::deleteCourse exception occurs while deleting the course object", ex);
            throw new CourseServiceBusinessException("deleteCourse service method failed...");
        }
        log.info("CourseService::deleteCourse method execution ended...");
    }

    // update the course
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequestDTO) {
        log.info("CourseService::updateCourse method execution started...");
        try {
            log.debug("CourseService::updateCourse request payload {} & id {}",
                    AppUtils.convertObjectToJson(courseRequestDTO), courseId);
            CourseEntity courseEntity = courseDao.findById(courseId)
                    .map(course -> AppUtils.mapDTOToEntity(course, courseRequestDTO))
                    .orElseThrow(() -> new CourseServiceBusinessException(String.format("%s is not valid course id", courseId)));

            CourseEntity updatedEntity = courseDao.save(courseEntity);
            log.debug("CourseService::updateCourse getting updated course object as {}", AppUtils.convertObjectToJson(updatedEntity));

            log.info("CourseService::updateCourse method execution ended...");
            return AppUtils.mapEntityToDTO(updatedEntity);

        } catch (Exception ex) {
            log.error("CourseService::updateCourse exception occurs while updating the course object", ex);
            throw new CourseServiceBusinessException("updateCourse service method failed...");
        }
    }
}
