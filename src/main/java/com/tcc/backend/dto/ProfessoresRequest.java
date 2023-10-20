package com.tcc.backend.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class ProfessoresRequest {
    private String formacaoacademica;
    private Date datacontratacao;
    private List<Date> datasDisponibilidade;
    private Long usuarioId;
}
