package br.com.adaptworks.scraper.tag;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import br.com.adaptworks.scraper.matcher.TemplateTag;

/**
 * @author jonasabreu
 * 
 */
final public class DefaultTagMatcher implements TagMatcher {

    private static final Logger log = Logger.getLogger(DefaultTagMatcher.class);

    public boolean matches(final TemplateTag template, final Tag html) {

        boolean result = typeMatches(template.type(), html.type()) && nameMatches(template.name(), html.name())
                && attributesMatches(template.attributes(), html.attributes())
                && contentMatches(template, html.content());
        log.trace("Matching: " + template + " with " + html + " resulted " + result);
        return result;
    }

    private boolean contentMatches(final TemplateTag template, final String content) {
        if (template.content().trim().length() == 0) {
            return true;
        }
        return template.matches(content);
    }

    private boolean typeMatches(final TagType tagType, final TagType tagType2) {
        return tagType.equals(tagType2);
    }

    private boolean nameMatches(final String template, final String html) {
        return template.equals(html);
    }

    private boolean attributesMatches(final Map<String, String> template, final Map<String, String> html) {
        if (template.size() > html.size()) {
            return false;
        }
        for (Entry<String, String> entry : template.entrySet()) {
            String htmlValue = html.get(entry.getKey());
            if (htmlValue == null) {
                return false;
            }
            if (!htmlValue.equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

}
