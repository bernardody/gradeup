package br.com.sinodal.gradeup.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Component
public class GradeValidator {

    private static final BigDecimal MIN_GRADE = BigDecimal.ZERO;
    private static final BigDecimal MAX_GRADE = new BigDecimal("10.00");

    public void validate(BigDecimal grade) {
        if (grade == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nota não pode ser nula");
        }

        if (grade.compareTo(MIN_GRADE) < 0 || grade.compareTo(MAX_GRADE) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nota é maior ou menor que o requisitado");
        }
    }
}