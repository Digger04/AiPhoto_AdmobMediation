package com.mkm.aiphoto_admobmediation;

import com.zeugmasolutions.localehelper.Locales;

import java.util.Locale;

public class MyUtils {

    public static Locale convertLanguage(String language) {
        switch (language) {
            case "English":
                return Locales.INSTANCE.getEnglish();
            case "Tiếng việt":
                return Locales.INSTANCE.getVietnamese();
            case "Deutsch":
                return Locales.INSTANCE.getGerman();
            case "Français":
                return Locales.INSTANCE.getFrench();
            case "Bahasa Indo":
                return Locales.INSTANCE.getIndonesian();
            case "Italiano":
                return Locales.INSTANCE.getItalian();
            case "日本":
                return Locales.INSTANCE.getJapanese();
            case "한국인":
                return Locales.INSTANCE.getKorean();
            case "Português":
                return Locales.INSTANCE.getPortuguese();
            case "Türkçe":
                return Locales.INSTANCE.getTurkish();
            case "ไทย":
                return Locales.INSTANCE.getThai();
            case "Русский":
                return Locales.INSTANCE.getRussian();
            case "हिन्दी":
                return Locales.INSTANCE.getHindi();
            case "עִברִית":
                return Locales.INSTANCE.getHebrew();
        }
        return Locales.INSTANCE.getEnglish();
    }
}


