package br.com.sinodal.gradeup.service.classes;

import br.com.sinodal.gradeup.controller.response.classes.ClassResponse;
import br.com.sinodal.gradeup.domain.Clazz;
import br.com.sinodal.gradeup.mapper.classes.ListClassMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListClassService {

    private final ClassRepository classRepository;

    public Page<ClassResponse> list(Pageable pageable) {
        Page<Clazz> classes = classRepository.findAll(pageable);
        return classes.map(ListClassMapper::toResponse);
    }
}
