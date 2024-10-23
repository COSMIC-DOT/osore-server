package com.dot.osore.global.resolver;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class LanguageResolver {
    private static final Map<String, String> extensionToLanguageMap = new HashMap<>();

    static {
        extensionToLanguageMap.put("js", "javascript");
        extensionToLanguageMap.put("jsx", "javascript");
        extensionToLanguageMap.put("ts", "typescript");
        extensionToLanguageMap.put("tsx", "typescript");
        extensionToLanguageMap.put("py", "python");
        extensionToLanguageMap.put("java", "java");
        extensionToLanguageMap.put("cpp", "cpp");
        extensionToLanguageMap.put("c", "c");
        extensionToLanguageMap.put("cs", "csharp");
        extensionToLanguageMap.put("rb", "ruby");
        extensionToLanguageMap.put("go", "go");
        extensionToLanguageMap.put("php", "php");
        extensionToLanguageMap.put("html", "html");
        extensionToLanguageMap.put("css", "css");
        extensionToLanguageMap.put("json", "json");
        extensionToLanguageMap.put("xml", "xml");
        extensionToLanguageMap.put("yaml", "yaml");
        extensionToLanguageMap.put("md", "markdown");
        extensionToLanguageMap.put("rs", "rust");
        extensionToLanguageMap.put("swift", "swift");
        extensionToLanguageMap.put("kt", "kotlin");
        extensionToLanguageMap.put("sh", "shell");
        extensionToLanguageMap.put("bat", "batchfile");
        extensionToLanguageMap.put("dockerfile", "dockerfile");
        extensionToLanguageMap.put("toml", "toml");
        extensionToLanguageMap.put("ini", "ini");
        extensionToLanguageMap.put("sql", "sql");
        extensionToLanguageMap.put("r", "r");
        extensionToLanguageMap.put("pl", "perl");
        extensionToLanguageMap.put("dart", "dart");
        extensionToLanguageMap.put("scala", "scala");
        extensionToLanguageMap.put("lua", "lua");
        extensionToLanguageMap.put("perl", "perl");
        extensionToLanguageMap.put("vb", "vb");
    }

    public static String getLanguageByExtension(String extension) {
        return extensionToLanguageMap.getOrDefault(extension.toLowerCase(), "plaintext");
    }

}
