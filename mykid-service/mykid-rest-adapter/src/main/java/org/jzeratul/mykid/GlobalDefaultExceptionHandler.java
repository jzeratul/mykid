package org.jzeratul.mykid;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.OffsetDateTime;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
  /**
   * Convert a predefined exception to an HTTP Status code
   */
//  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Server error")
  @ExceptionHandler(RuntimeException.class)
  public ModelAndView serverError(HttpServletRequest req, Exception exception) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.addObject("timestamp", OffsetDateTime.now().toString());
		mav.addObject("status", 500);
		
		return mav;
  }
}