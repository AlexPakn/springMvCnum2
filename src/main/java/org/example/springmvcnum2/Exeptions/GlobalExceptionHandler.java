package org.example.springmvcnum2.Exeptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Ошибка: Магазин не найден
  @ExceptionHandler(StoreNotFoundException.class)
  public String handleStoreNotFound(StoreNotFoundException ex, Model model) {
    model.addAttribute("error", ex.getMessage());
    return "error-page"; // Покажем страницу с ошибкой
  }

  // Ошибка загрузки файла
  @ExceptionHandler(FileUploadException.class)
  public String handleFileUpload(FileUploadException ex, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("error", "Ошибка загрузки файла: " + ex.getMessage());
    return "redirect:/stores";
  }

  // Общая обработка ошибок
  @ExceptionHandler(Exception.class)
  public String handleGenericException(Exception ex, Model model) {
    model.addAttribute("error", "Произошла ошибка: " + ex.getMessage());
    return "error-page";
  }
}
