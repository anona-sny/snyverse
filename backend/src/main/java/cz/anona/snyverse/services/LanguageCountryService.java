package cz.anona.snyverse.services;

import cz.anona.snyverse.entities.enums.CountryCode;
import cz.anona.snyverse.entities.enums.LanguageCode;
import org.springframework.stereotype.Service;

@Service
public class LanguageCountryService {

    /**
     * Return enum item if exist, else null
     * @param languageCode
     * @return Return enum item if exist, else null
     */
    public LanguageCode getLanguage(String languageCode) {
        try {
            return LanguageCode.valueOf(languageCode);
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * Return enum item if exist, else null
     * @param countryCode
     * @return Return enum item if exist, else null
     */
    public CountryCode getCountry(String countryCode) {
        try {
            return CountryCode.valueOf(countryCode);
        } catch (Exception ignored) {}
        return null;
    }

}
