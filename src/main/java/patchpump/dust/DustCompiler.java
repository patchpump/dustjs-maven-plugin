package patchpump.dust;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * DustCompiler backed by the JavaScript ScriptEngine.
 * 
 * @author dmonroe
 * @author patchpump
 */
public class DustCompiler extends AbstractJavaScriptCompiler {

	public DustCompiler(String dustVersion) throws IOException, ScriptException {
		super("/META-INF/" + dustVersion);
	}

	public String compile(String templateSource, String templateName) throws ScriptException {

		synchronized(script) {
			ScriptEngine engine = script.getEngine();
			Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
			bindings.put("templateSource", templateSource);
			bindings.put("templateName", templateName);
			return (String)engine.eval("dust.compile(templateSource, templateName)", bindings);
		}
	}

	public void compileAndSave(File input, File output, boolean force) throws IOException, ScriptException, NoSuchMethodException {
		String templateName = FilenameUtils.getBaseName(input.getName());
		String templateSource = FileUtils.readFileToString(input, StandardCharsets.UTF_8);
		if (force || !output.exists() || output.lastModified() < input.lastModified() || output.length() < 1) {
			String compiledTemplate = compile(templateSource, templateName);
			FileUtils.writeStringToFile(output, compiledTemplate, StandardCharsets.UTF_8);
		}
	}
}
