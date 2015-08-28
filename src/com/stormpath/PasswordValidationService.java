package com.stormpath;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidationService {

    private Integer minLength;
    private Integer maxLength;
    private Map<String, Boolean> regexExpressionMap;

    public boolean validatePassword(String password) {
        if (minLength != null && password.length() < minLength) {
            return false;
        } else if (maxLength != null && password.length() > maxLength) {
            return false;
        }

        if (regexExpressionMap != null) {
            for (String regex : regexExpressionMap.keySet()) {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(password);
                if (m.find() != regexExpressionMap.get(regex)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public void setRegexExpressionMap(Map<String, Boolean> regexExpressionMap) {
        this.regexExpressionMap = regexExpressionMap;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public Map<String, Boolean> getRegexExpressionMap() {
        return regexExpressionMap;
    }
}
