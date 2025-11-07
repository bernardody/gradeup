package br.com.sinodal.gradeup.service.aClass;

import br.com.sinodal.gradeup.controller.request.aClass.CreateClassRequest;
import br.com.sinodal.gradeup.controller.response.aClass.ListClassResponse;
import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.mapper.aClass.UpdateClassMapper;
import br.com.sinodal.gradeup.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UpdateClassService {

    private final ClassRepository classRepository;

    public ListClassResponse update(Long id, CreateClassRequest request) {

        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        aClass.setName(request.getName());
        aClass.setYear(request.getYear());

        classRepository.save(aClass);

        return UpdateClassMapper.toResponse(aClass);
    }
}
