/**
 * 
 */
package com.liujy.utils.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.liujy.utils.validate.constraints.Mobile;

public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private final static String mobile = "^1\\d{10}$";

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
     * Annotation)
     */
    @Override
    public void initialize(Mobile constraintAnnotation) {
        // TODO Auto-generated method stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
     * javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) {
            return true;
        }
        if (RegExValidateUtils.isMobile(mobile)) {
            return true;
        } else {
            return false;
        }
    }

}
