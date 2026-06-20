package br.com.sinodal.gradeup.service.exam;

import br.com.sinodal.gradeup.controller.request.exam.UpdateExamRequest;
import br.com.sinodal.gradeup.controller.response.exam.ExamResponse;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.domain.Exam;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.domain.Trimester;
import br.com.sinodal.gradeup.domain.User;
import br.com.sinodal.gradeup.enums.UserType;
import br.com.sinodal.gradeup.mapper.exam.UpdateExamMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import br.com.sinodal.gradeup.repository.ExamRepository;
import br.com.sinodal.gradeup.repository.SubjectRepository;
import br.com.sinodal.gradeup.repository.TeacherSubjectClassRepository;
import br.com.sinodal.gradeup.repository.TrimesterRepository;
import br.com.sinodal.gradeup.service.user.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateExamService {

    private final ExamRepository examRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final TrimesterRepository trimesterRepository;
    private final TeacherSubjectClassRepository teacherSubjectClassRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public ExamResponse update(Long id, UpdateExamRequest request) {

        User loggedUser = authenticatedUserService.get();

        if (!loggedUser.getType().equals(UserType.TEACHER))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar provas");

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prova não encontrada"));

        Clazz clazz = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Matéria não encontrada"));

        Trimester trimester = trimesterRepository.findById(request.getTrimesterId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trimestre não encontrado"));

        if (!teacherSubjectClassRepository.existsByTeacherIdAndSubjectIdAndClazzId(
                exam.getTeacher().getId(), subject.getId(), clazz.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Professor não está atribuído a esta matéria nesta turma");
        }

        exam.setClazz(clazz);
        exam.setSubject(subject);
        exam.setTrimester(trimester);
        exam.setName(request.getName());
        exam.setMaxScore(request.getMaxScore());
        exam.setExamDate(request.getExamDate());

        examRepository.save(exam);

        return UpdateExamMapper.toResponse(exam);
    }
}