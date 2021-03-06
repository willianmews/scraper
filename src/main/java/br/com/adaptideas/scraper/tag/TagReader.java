package br.com.adaptideas.scraper.tag;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jonasabreu
 * 
 */
final public class TagReader {

	public Tag readTag(final String declaration, final String content) {
		String tag = new TagSanitizer().sanitize(declaration);
		Map<String, Attribute> attributes = recoverAttributes(tag);
		String tagName = recoverName(tag);
		TagType tagType = recoverType(tag);

		return tagType.createTag(tagName, content, attributes);
	}

	private TagType recoverType(final String tag) {
		return TagType.fromValue(tag.charAt(0));
	}

	private String recoverName(final String tag) {
		String tagName = tag.substring(0, tag.indexOf(" ") == -1 ? tag.length() : tag.indexOf(" "));

		return tagName.startsWith("/") || tagName.startsWith("!") ? tagName.substring(1) : tagName;
	}

	private Map<String, Attribute> recoverAttributes(final String declaration) {
		String[] tokens = tokenize(declaration);
		Map<String, Attribute> map = new HashMap<String, Attribute>();

		for (int i = 1; i < tokens.length; i++) {
			String[] attribute = tokens[i].split("=\"|\"");
			if (attribute.length == 2) {
				map.put(attribute[0], Attribute.from(attribute[1]));
			}
		}
		return map;
	}

	private String[] tokenize(final String declaration) {
		boolean insideAttributeValue = false;
		String safeDeclaration = "";

		for (int i = 0; i < declaration.length(); i++) {
			if (declaration.charAt(i) == '"') {
				insideAttributeValue = !insideAttributeValue;
			}
			if ((declaration.charAt(i) == ' ') && !insideAttributeValue) {
				safeDeclaration += '#';
			} else {
				safeDeclaration += declaration.charAt(i);
			}
		}
		return safeDeclaration.split("#");
	}

}
