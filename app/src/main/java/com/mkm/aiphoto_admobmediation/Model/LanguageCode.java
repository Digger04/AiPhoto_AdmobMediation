package com.mkm.aiphoto_admobmediation.Model;

import java.util.Arrays;
import java.util.List;

public interface LanguageCode {
    String English = "en";
    String Vietnamese = "vi";
    String Deutsch = "de";
    String French = "fr";
    String Indonesian = "id";
    String Italian = "it";
    String Japanese = "ja";
    String Korean = "ko";
    String Portuguese = "pt";
    String Turkish = "tr";
    String Thai = "th";
    String Russian = "ru";
    String Hindi = "hi";
    String Hebrew = "iw";

    List<String> languages = Arrays.asList(
            "English",
            "Tiếng việt",
            "Deutsch",
            "Français",
            "Bahasa Indo",
            "Italiano",
            "日本",
            "한국인",
            "Português",
            "Türkçe",
            "ไทย",
            "Русский",
            "हिन्दी",
            "עִברִית");

    static String getLanguageCode(String language) {
        switch (language) {
            case "English":
                return English;
            case "Tiếng việt":
                return Vietnamese;
            case "Deutsch":
                return Deutsch;
            case "Français":
                return French;
            case "Bahasa Indo":
                return Indonesian;
            case "Italiano":
                return Italian;
            case "日本":
                return Japanese;
            case "한국인":
                return Korean;
            case "Português":
                return Portuguese;
            case "Türkçe":
                return Turkish;
            case "ไทย":
                return Thai;
            case "Русский":
                return Russian;
            case "हिन्दी":
                return Hindi;
            case "עִברִית":
                return Hebrew;
        }
        return English;
    }
}