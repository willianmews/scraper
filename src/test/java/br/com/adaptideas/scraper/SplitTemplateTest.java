package br.com.adaptideas.scraper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.adaptideas.scraper.Html;
import br.com.adaptideas.scraper.SplitTemplate;

/**
 * @author jonasabreu
 * 
 */
final public class SplitTemplateTest {

	@Test
	public void testThatWorksAsSingleTemplate() {
		List<Item> match = new SplitTemplate<Item>("<td>${foo}</td>", Item.class).match(new Html("<td>test</td>"));

		Assert.assertEquals(1, match.size());
		Assert.assertEquals("test", match.get(0).foo());
	}

	@Test
	public void testThatIfFirstTemplateCantFindTheSecondFinds() {
		List<Item> match = new SplitTemplate<Item>("<tr>${foo}</tr><split><td>${foo}</td>", Item.class).match(new Html(
				"<td>test</td>"));

		Assert.assertEquals(1, match.size());
		Assert.assertEquals("test", match.get(0).foo());
	}

	@Test
	public void testThatSecondTemplateDoesNotOverrideFirst() {
		List<Item> match = new SplitTemplate<Item>("<tr>${foo}</tr><split><td>${foo}</td>", Item.class).match(new Html(
				"<tr>test</tr><td>foo</td>"));

		Assert.assertEquals(1, match.size());
		Assert.assertEquals("test", match.get(0).foo());
	}
}