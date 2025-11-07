package br.com.sinodal.gradeup.service.aClass;

import br.com.sinodal.gradeup.controller.response.aClass.ListClassResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.mapper.aClass.ListClassMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListClassService {

    private final ClassRepository classRepository;

    public Page<ListClassResponse> list(Pageable pageable) {
        Page<Class> classes = classRepository.findAll(pageable);
        return classes.map(ListClassMapper::toResponse);
    }
}
