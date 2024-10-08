package com.alura.agencias.exception;

import com.alura.agencias.domain.http.SituacaoCadastral;

public class AgenciaNaoAtivaException extends RuntimeException {

    @Override
    public String getMessage() {
        return "O status da agência é " + SituacaoCadastral.INATIVO;
    }
}
