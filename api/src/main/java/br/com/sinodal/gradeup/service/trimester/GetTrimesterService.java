package br.com.sinodal.gradeup.service.trimester;

import br.com.sinodal.gradeup.controller.response.trimester.TrimesterResponse;
import br.com.sinodal.gradeup.domain.Trimester;
import br.com.sinodal.gradeup.mapper.trimester.ListTrimesterMapper;
import br.com.sinodal.gradeup.repository.TrimesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetTrimesterService {

    private final TrimesterRepository trimesterRepository;

    public TrimesterResponse byId(Long id) {
        Trimester trimester = trimesterRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trimestre não encontrado"));

        return ListTrimesterMapper.toResponse(trimester);
    }
}