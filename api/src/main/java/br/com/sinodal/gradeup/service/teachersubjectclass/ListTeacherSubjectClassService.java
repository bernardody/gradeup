package br.com.sinodal.gradeup.service.teachersubjectclass;

import br.com.sinodal.gradeup.controller.response.teachersubjectclass.TeacherSubjectClassResponse;
import br.com.sinodal.gradeup.domain.TeacherSubjectClass;
import br.com.sinodal.gradeup.mapper.teachersubjectclass.ListTeacherSubjectClassMapper;
import br.com.sinodal.gradeup.repository.TeacherSubjectClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListTeacherSubjectClassService {

    private final TeacherSubjectClassRepository teacherSubjectClassRepository;

    public Page<TeacherSubjectClassResponse> list(Pageable pageable) {
        Page<TeacherSubjectClass> teacherSubjectClasses = teacherSubjectClassRepository.findAll(pageable);
        return teacherSubjectClasses.map(ListTeacherSubjectClassMapper::toResponse);
    }
}