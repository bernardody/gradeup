package br.com.sinodal.gradeup.service.teachersubjectclass;

import br.com.sinodal.gradeup.controller.request.teachersubjectclass.InsertTeacherSubjectClassRequest;
import br.com.sinodal.gradeup.controller.response.teachersubjectclass.TeacherSubjectClassResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.TeacherSubjectClass;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.teachersubjectclass.InsertTeacherSubjectClassMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.repository.SubjectRepository;
import br.com.sinodal.gradeup.repository.TeacherSubjectClassRepository;
import br.com.sinodal.gradeup.repository.UserRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class InsertTeacherSubjectClassService {

    private final TeacherSubjectClassRepository teacherSubjectClassRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final ClassRepository classRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public TeacherSubjectClassResponse insert(InsertTeacherSubjectClassRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para criar atribuições");

        User teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));

        if (!teacher.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário informado não é um professor");

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matéria não encontrada"));

        Class classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        TeacherSubjectClass teacherSubjectClass = InsertTeacherSubjectClassMapper.toEntity(request, teacher, subject, classEntity);

        teacherSubjectClassRepository.save(teacherSubjectClass);

        return InsertTeacherSubjectClassMapper.toResponse(teacherSubjectClass);
    }
}