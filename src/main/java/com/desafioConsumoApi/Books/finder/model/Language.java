package com.desafioConsumoApi.Books.finder.model;

public enum Language {
    ES("es"),
    EN("en"),
    FR("fr"),
    IT("it"),
    PT("pt");

    private String idiom;

    Language(String idiom){
        this.idiom = idiom;
    }


    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.idiom.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("No enum constant for code: " + text);
    }
    public String getIdiom(){return this.idiom;}

}

