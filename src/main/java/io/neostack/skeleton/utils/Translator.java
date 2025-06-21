package io.neostack.skeleton.utils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@RequestScoped
public class Translator {

  @Context
  HttpHeaders headers;

  @Inject
  @ConfigProperty(name = "app.locale", defaultValue = "en")
  String defaultLocale;

  private static final List<String> BUNDLE_BASE_NAMES = List.of(
      "messages/msg" // Default messages
  );

  public String translate(String keyword) {
    return translate(keyword, (Object[]) null);
  }

  public String translate(String keyword, Object... args) {
    Locale locale = resolveLocale();

    for (String baseName : BUNDLE_BASE_NAMES) {
      try {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        if (bundle.containsKey(keyword)) {
          String template = bundle.getString(keyword);
          return format(template, args);
        }
      } catch (Exception ignored) {
        // bundle not found or bad format
      }
    }

    return format(keyword, args);
  }

  private Locale resolveLocale() {
    return headers.getAcceptableLanguages().stream()
        .findFirst()
        .orElse(Locale.forLanguageTag(defaultLocale));
  }

  private String format(String template, Object... args) {
    return (args == null || args.length == 0) ? template : MessageFormat.format(template, args);
  }

  public boolean isMessageEmpty(String message) {
    return message == null || message.trim().isEmpty();
  }

  public boolean hasTranslation(String keyword) {
    if (isMessageEmpty(keyword)) {
      return false;
    }

    Locale locale = resolveLocale();

    return BUNDLE_BASE_NAMES.stream().anyMatch(baseName -> {
      try {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        return bundle.containsKey(keyword);
      } catch (Exception e) {
        return false;
      }
    });
  }
}
