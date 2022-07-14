package com.ciandt.feedfront.excecoes;

import java.io.IOException;

public class ArquivoException extends BusinessException {
    public ArquivoException(String message) {
        super(message);
    }

    public ArquivoException(Throwable throwable) {
        super(throwable.getMessage());
    }
}
