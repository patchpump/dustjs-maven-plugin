package patchpump.dust;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import patchpump.dust.DustCompiler;
import patchpump.dust.DustSource;

import javax.script.ScriptException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: dmonroe
 * Date: 4/10/13
 * Time: 3:18 PM
 */
public class DustCompilerTest {

	DustCompiler dustCompiler = null;

	@Before
	public void setUp() throws Exception {
		dustCompiler = new DustCompiler(DustMojo.DEFAULT_COMPILER_VERSION);
	}

	@After
	public void tearDown() throws Exception {
		dustCompiler = null;
	}

	@Test
	public void testCompileFromString() throws NoSuchMethodException, FileNotFoundException, ScriptException {

		// 1.2.2 String expectedResult = "(function(){dust.register(\"hello\",body_0);function body_0(chk,ctx){return chk.write(\"Hello \").reference(ctx.get(\"name\"),ctx,\"h\");}return body_0;})();";
		// 2.5.1 String expectedResult = "(function(){dust.register(\"hello\",body_0);function body_0(chk,ctx){return chk.w(\"Hello \").f(ctx.get([\"name\"], false),ctx,\"h\");}body_0.__dustBody=!0;return body_0;})();";
		String expectedResult = "(function(dust){dust.register(\"hello\",body_0);function body_0(chk,ctx){return chk.w(\"Hello \").f(ctx.get([\"name\"], false),ctx,\"h\");}body_0.__dustBody=!0;return body_0}(dust));";
		String actualResult = dustCompiler.compile("Hello {name}", "hello");

		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testCompileFromFile() throws NoSuchMethodException, FileNotFoundException, ScriptException {

		// 1.2.2 String expectedResult = "(function(){dust.register(\"test_template\",body_0);function body_0(chk,ctx){return chk.write(\"Hello \").reference(ctx.get(\"name\"),ctx,\"h\");}return body_0;})();";
		// 2.5.1 String expectedResult = "(function(){dust.register(\"test_template\",body_0);function body_0(chk,ctx){return chk.w(\"Hello \").f(ctx.get([\"name\"], false),ctx,\"h\");}body_0.__dustBody=!0;return body_0;})();";
		String expectedResult = "(function(dust){dust.register(\"test_template\",body_0);function body_0(chk,ctx){return chk.w(\"Hello \").f(ctx.get([\"name\"], false),ctx,\"h\").h(\"eq\",ctx,{\"block\":body_1},{\"key\":ctx.get([\"role\"], false),\"value\":\"admin\"},\"h\");}body_0.__dustBody=!0;function body_1(chk,ctx){return chk.w(\"bye!\\t\\t\");}body_1.__dustBody=!0;return body_0}(dust));";

		File input = new File("src/test/resources/test_template.html");
		DustSource dustSource = new DustSource(input);

		String actualResult = dustCompiler.compile(dustSource);

		assertEquals(expectedResult, actualResult);
	}
}
