package com.bikeshop.demo.controller.Exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionGeneral {

    @ExceptionHandler
    public String exception(Exception ex, Model model){
        model.addAttribute("exception",ex);
        System.out.println("Cause: " + ex.getCause() + ex.getLocalizedMessage());
        ex.printStackTrace();
        return "/error";
        }

    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/uploadStatus";

    }

    }

