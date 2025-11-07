package br.com.sinodal.gradeup.service.aClass;

import br.com.sinodal.gradeup.controller.request.aClass.CreateClassRequest;
import br.com.sinodal.gradeup.controller.response.aClass.ListClassResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.mapper.aClass.CreateClassMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateClassService {

    private final ClassRepository classRepository;

    public ListClassResponse create(CreateClassRequest request) {

        Class aClass = CreateClassMapper.toEntity(request);

        classRepository.save(aClass);

        return CreateClassMapper.toResponse(aClass);
    }
}
