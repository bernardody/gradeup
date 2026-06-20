package br.com.sinodal.gradeup.service.teachersubjectclass;

import br.com.sinodal.gradeup.controller.response.teachersubjectclass.TeacherSubjectClassResponse;
import br.com.sinodal.gradeup.domain.TeacherSubjectClass;
import br.com.sinodal.gradeup.mapper.teachersubjectclass.ListTeacherSubjectClassMapper;
import br.com.sinodal.gradeup.repository.TeacherSubjectClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetTeacherSubjectClassService {

    private final TeacherSubjectClassRepository teacherSubjectClassRepository;

    public TeacherSubjectClassResponse byId(Long id) {
        TeacherSubjectClass teacherSubjectClass = teacherSubjectClassRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atribuição não encontrada"));

        return ListTeacherSubjectClassMapper.toResponse(teacherSubjectClass);
    }
}