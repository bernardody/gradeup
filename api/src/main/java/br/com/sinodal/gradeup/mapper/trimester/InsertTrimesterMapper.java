package br.com.sinodal.gradeup.mapper.trimester;

import br.com.sinodal.gradeup.controller.request.trimester.InsertTrimesterRequest;
import br.com.sinodal.gradeup.controller.response.trimester.TrimesterResponse;
import br.com.sinodal.gradeup.domain.Trimester;

public class InsertTrimesterMapper {

    public static Trimester toEntity(InsertTrimesterRequest request) {
        return Trimester.builder()
                .name(request.getName())
                .maxPoints(request.getMaxPoints())
                .minPoints(request.getMinPoints())
                .build();
    }

    public static TrimesterResponse toResponse(Trimester entity) {
        return TrimesterResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .maxPoints(entity.getMaxPoints())
                .minPoints(entity.getMinPoints())
                .build();
    }
}