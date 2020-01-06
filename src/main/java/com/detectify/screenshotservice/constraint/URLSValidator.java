package com.detectify.screenshotservice.constraint;

import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class URLSValidator implements ConstraintValidator<URLS, List<String>> {

   private UrlValidator urlValidator = new UrlValidator();

   public void initialize(URLS constraint) {
      //noting to init
   }

   public boolean isValid(List<String> urls, ConstraintValidatorContext context) {
      return urls.stream().allMatch(urlValidator::isValid);
   }
}
