package br.com.sinodal.gradeup.service.aClass;

import br.com.sinodal.gradeup.domain.Class;
import br.com.sinodal.gradeup.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class DeleteClassService {

    private final ClassRepository classRepository;


    public void delete(Long id) {
        Class aClass = classRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Turma não encontrada"));

        classRepository.deleteById(aClass.getId());
    }
}
