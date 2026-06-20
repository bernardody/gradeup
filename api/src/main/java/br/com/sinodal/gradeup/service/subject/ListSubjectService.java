package br.com.sinodal.gradeup.service.subject;

import br.com.sinodal.gradeup.controller.response.subject.SubjectResponse;
import br.com.sinodal.gradeup.domain.Subject;
import br.com.sinodal.gradeup.mapper.subject.ListSubjectMapper;
import br.com.sinodal.gradeup.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListSubjectService {

    private final SubjectRepository subjectRepository;

    public Page<SubjectResponse> list(Pageable pageable) {
        Page<Subject> subjects = subjectRepository.findAll(pageable);
        return subjects.map(ListSubjectMapper::toResponse);
    }
}