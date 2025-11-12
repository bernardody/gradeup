package br.com.sinodal.gradeup.mapper.trimester;

import br.com.sinodal.gradeup.controller.response.trimester.TrimesterResponse;
import br.com.sinodal.gradeup.domain.Trimester;

public class UpdateTrimesterMapper {

    public static TrimesterResponse toResponse(Trimester entity) {
        return TrimesterResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .maxPoints(entity.getMaxPoints())
                .minPoints(entity.getMinPoints())
                .build();
    }
}