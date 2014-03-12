package org.jetbrains.debugger;

import com.intellij.openapi.util.ActionCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.debugger.values.Value;

public interface ValueModifier {
  // expression can contains reference to another variables in current scope, so, we should evaluate it before set
  // http://youtrack.jetbrains.com/issue/WEB-2342#comment=27-512122

  // we don't worry about performance in case of simple primitive values - boolean/string/numbers,
  // it works quickly and we don't want to complicate our code and debugger SDK
  ActionCallback setValue(@NotNull Variable variable, String newValue, EvaluateContext evaluateContext);

  ActionCallback setValue(@NotNull Variable variable, Value newValue);
}