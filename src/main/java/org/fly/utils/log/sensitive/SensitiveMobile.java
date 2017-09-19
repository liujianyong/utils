package org.fly.utils.log.sensitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.fly.utils.log.CryptoConvertConfig.DefaultPhoneCryptoConvertor;
import org.fly.utils.log.ICryptoConvertor;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD})
public @interface SensitiveMobile{
	public Class<? extends ICryptoConvertor> convertor() default DefaultPhoneCryptoConvertor.class; 
}