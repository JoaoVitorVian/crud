package com.challenge.crud.core.Utils;

import org.springframework.stereotype.Component;

@Component
public class StringExtensions {

    public String formatCpf(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

}
