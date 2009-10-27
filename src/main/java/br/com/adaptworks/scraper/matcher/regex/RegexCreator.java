package br.com.adaptworks.scraper.matcher.regex;

/**
 * @author jonasabreu
 * 
 */
public interface RegexCreator {

    boolean accepts(String token);

    String regexFor(String token);

}
