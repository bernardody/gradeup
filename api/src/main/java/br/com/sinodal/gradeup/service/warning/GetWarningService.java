package br.com.sinodal.gradeup.service.warning;

import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.mapper.warning.ListWarningMapper;
import br.com.sinodal.gradeup.repository.WarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class GetWarningService {

    private final WarningRepository warningRepository;

    public WarningResponse byId(Long id) {
        Warning warning = warningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aviso não encontrada"));

        return ListWarningMapper.toResponse(warning);
    }
}
