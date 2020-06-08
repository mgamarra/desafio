package br.com.challenge.core.base;

import java.util.*;

public interface Constants {
    String ROOT_PACKAGE = "br.com.challenge";
    String BASE_PACKAGE = ROOT_PACKAGE + ".core";
    String ENTITY_PACKAGE = BASE_PACKAGE + ".data.entity";
    String REPOSITORY_PACKAGE = BASE_PACKAGE + ".data.repository";
    Locale PT_BR = new Locale("pt", "BR");
    List<Locale> SUPPORTED_LOCALES = Arrays.asList(PT_BR, Locale.ENGLISH);
}
