package az.eagro.animalhusbandry.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScriptExecutor {
    private final ObjectMapper objectMapper;
    private final ScriptEngine engine;

    public JavaScriptExecutor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.engine =  new ScriptEngineManager().getEngineByName("Graal.js");
        init();
    }

    private void init() {
        if (engine != null) {
            try {
                engine.eval("function calculateWrapper(p) { return JSON.stringify(calculate(JSON.parse(p))) }");
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new NullPointerException("Engine is null");
        }
    }

    public <T> T executeAndGetResult(String jsFunctionBody, Object value, Class<T> returnType) {
        try {
            engine.eval("function calculate(p) { " + jsFunctionBody + " }");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        Invocable jsInvoke = (Invocable) engine;
        Object result = null;
        try {
            result = jsInvoke.invokeFunction("calculateWrapper", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (returnType.isInstance(result)) {
            return returnType.cast(result);
        } else {
            throw new RuntimeException("JavaScript function result could not be cast to " + returnType.getName());
        }
    }
}
