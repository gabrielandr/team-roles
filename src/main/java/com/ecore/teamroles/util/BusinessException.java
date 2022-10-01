package com.ecore.teamroles.util;

/**
 * Classe de excecao customizada que engloba qualquer tipo de excecao,
 * podendo customizar a mensagem de retorno e a causa da excecao.
 */
public class BusinessException extends Exception {

    public BusinessException(String message) {
        super(message);
    }
}
