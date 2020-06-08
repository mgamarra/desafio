package br.com.challenge.core.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import br.com.challenge.core.base.Constants;
import lombok.extern.slf4j.Slf4j;


/**
 * Utility class to internationalize messages (I18N)
 *
 * @author rodrigo
 * @ deprecated Use Deltaspike messages instead
 */
@Slf4j
@Component
public class I18N implements Serializable {
	public String getString(Enum<?> key, Object... params) {
		return getString(null, key.name(), params);
	}

	public String getString(Locale locale, Enum<?> key, Object... params) {
		return getString(locale, key.name(), params);
	}

	public String getString(String key, Object... params) {
		return getString(null, key, params);
	}

	public String getString(Locale locale, String key, Object... params) {
		List<ResourceBundle> bundles = getBundles(locale);

		if (key == null)
			throw new RuntimeException("I18N: Message key must not be null");

		if (params != null && params.length > 0) {
			List<String> resolvedParams = new ArrayList<>(params.length);
			for (Object param : params) {
				if (param == null)
					throw new RuntimeException("I18N: Message parameters must not be null. Current message key: " + key);

				resolvedParams.add(param instanceof Enum
						? resolveKey(bundles, (Enum) param)
						: resolveKey(bundles, param.toString()));
			}

			return new MessageFormat(resolveKey(bundles, key))
					.format(resolvedParams.toArray());

		} else {
			return resolveKey(bundles, key);
		}
	}

	/*
	 * Private members
	 */

	private List<ResourceBundle> getBundles(Locale locale) {
		locale = resolveLocale(locale);
		return Arrays.asList(
				ResourceBundle.getBundle("i18n.messages", locale),
				ResourceBundle.getBundle("i18n.validation", locale),
				ResourceBundle.getBundle("i18n.email", locale)
		);
	}

	private Locale resolveLocale(Locale locale) {
		if (locale == null) locale = LocaleContextHolder.getLocale();
		return Constants.SUPPORTED_LOCALES.contains(locale) ? locale : Locale.ENGLISH;
	}

	private String resolveKey(List<ResourceBundle> bundles, String key) {
		for (ResourceBundle bundle : bundles)
			if (bundle.containsKey(key))
				return bundle.getString(key);

			else if (bundle.containsKey(key.toUpperCase()))
				return bundle.getString(key.toUpperCase());

		return key;
	}

	private String resolveKey(List<ResourceBundle> bundles, Enum<?> e) {
		String resolvedString = null;

		for (ResourceBundle bundle : bundles)
			if (bundle.containsKey(e.name())) {
				resolvedString = bundle.getString(e.name());
				break;
			}

		if (StringUtils.isEmpty(resolvedString))
			resolvedString = this.resolveKey(bundles, e.toString());

		return StringUtils.isNotEmpty(resolvedString) ? resolvedString : e.name();
	}
}
