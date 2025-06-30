package com.prateek.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prateek.dto.CourseRequestDTO;
import com.prateek.dto.CourseResponseDTO;
import com.prateek.entity.CourseEntity;
import com.prateek.exception.CourseServiceBusinessException;

public class AppUtils {

    public static CourseEntity mapDTOToEntity(CourseRequestDTO courseRequestDTO) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(courseRequestDTO.getName());
        courseEntity.setTrainerName(courseRequestDTO.getTrainerName());
        courseEntity.setDuration(courseRequestDTO.getDuration());
        courseEntity.setStartDate(courseRequestDTO.getStartDate());
        courseEntity.setCourseType(courseRequestDTO.getCourseType());
        courseEntity.setFees(courseRequestDTO.getFees());
        courseEntity.setCertificateAvailable(courseRequestDTO.isCertificateAvailable());
        courseEntity.setDescription(courseRequestDTO.getDescription());
        courseEntity.setEmailId(courseRequestDTO.getEmailId());
        courseEntity.setContact(courseRequestDTO.getContact());
        return courseEntity;
    }

    public static CourseResponseDTO mapEntityToDTO(CourseEntity courseEntity) {
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourseId(courseEntity.getCourseId());
        courseResponseDTO.setName(courseEntity.getName());
        courseResponseDTO.setTrainerName(courseEntity.getTrainerName());
        courseResponseDTO.setDuration(courseEntity.getDuration());
        courseResponseDTO.setStartDate(courseEntity.getStartDate());
        courseResponseDTO.setCourseType(courseEntity.getCourseType());
        courseResponseDTO.setFees(courseEntity.getFees());
        courseResponseDTO.setCertificateAvailable(courseEntity.isCertificateAvailable());
        courseResponseDTO.setDescription(courseEntity.getDescription());
        courseResponseDTO.setEmailId(courseEntity.getEmailId());
        courseResponseDTO.setContact(courseEntity.getContact());
        return courseResponseDTO;
    }

    public static CourseEntity mapDTOToEntity(CourseEntity existingCourse, CourseRequestDTO courseRequestDTO) {
        existingCourse.setName(courseRequestDTO.getName());
        existingCourse.setTrainerName(courseRequestDTO.getTrainerName());
        existingCourse.setDuration(courseRequestDTO.getDuration());
        existingCourse.setStartDate(courseRequestDTO.getStartDate());
        existingCourse.setCourseType(courseRequestDTO.getCourseType());
        existingCourse.setFees(courseRequestDTO.getFees());
        existingCourse.setCertificateAvailable(courseRequestDTO.isCertificateAvailable());
        existingCourse.setDescription(courseRequestDTO.getDescription());
        existingCourse.setEmailId(courseRequestDTO.getEmailId());
        existingCourse.setContact(courseRequestDTO.getContact());
        return existingCourse;
    }

    public static String convertObjectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CourseServiceBusinessException(e.getMessage());
        }
    }
}
