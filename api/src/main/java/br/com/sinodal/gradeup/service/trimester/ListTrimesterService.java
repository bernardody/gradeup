package br.com.sinodal.gradeup.service.trimester;

import br.com.sinodal.gradeup.controller.response.trimester.TrimesterResponse;
import br.com.sinodal.gradeup.domain.Trimester;
import br.com.sinodal.gradeup.mapper.trimester.ListTrimesterMapper;
import br.com.sinodal.gradeup.repository.TrimesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListTrimesterService {

    private final TrimesterRepository trimesterRepository;

    public Page<TrimesterResponse> list(Pageable pageable) {
        Page<Trimester> trimesters = trimesterRepository.findAll(pageable);
        return trimesters.map(ListTrimesterMapper::toResponse);
    }
}