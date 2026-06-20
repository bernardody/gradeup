package br.com.sinodal.gradeup.service.warning;

import br.com.sinodal.gradeup.controller.response.warning.WarningResponse;
import br.com.sinodal.gradeup.domain.Warning;
import br.com.sinodal.gradeup.mapper.warning.ListWarningMapper;
import br.com.sinodal.gradeup.repository.WarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListWarningService {
    private final WarningRepository warningRepository;

    public Page<WarningResponse> list(Pageable pageable) {
        Page<Warning> warnings = warningRepository.findAll(pageable);
        return warnings.map(ListWarningMapper::toResponse);
    }
}
