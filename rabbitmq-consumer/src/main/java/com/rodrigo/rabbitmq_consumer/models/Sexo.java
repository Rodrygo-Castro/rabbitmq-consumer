package com.rodrigo.rabbitmq_consumer.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Sexo {
    
    MASCULINO("Masculino"),
    FEMININO("Feminino");

    private String descricao;

    private Sexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Sexo fromValue(String value) {
        for (Sexo sexo : Sexo.values()) {
            if (sexo.descricao.equalsIgnoreCase(value)) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }
}
