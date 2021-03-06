// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.codeInsight;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.containers.ContainerUtil;

import java.util.Set;

/**
 * @author peter
 */
class CheckerFrameworkNullityUtil {
  static boolean isCheckerDefault(boolean nullable, PsiAnnotation.TargetType[] types, PsiAnnotation anno) {
    String qName = anno.getQualifiedName();
    if ("org.checkerframework.framework.qual.DefaultQualifier".equals(qName)) {
      PsiAnnotationMemberValue value = anno.findAttributeValue(PsiAnnotation.DEFAULT_REFERENCED_METHOD_NAME);
      return value instanceof PsiClassObjectAccessExpression &&
             isNullityAnnotationReference(nullable, (PsiClassObjectAccessExpression)value) &&
             hasAppropriateTarget(types, anno.findAttributeValue("locations"));
    }
    
    if ("org.checkerframework.framework.qual.DefaultQualifiers".equals(qName)) {
      PsiAnnotationMemberValue value = anno.findAttributeValue(PsiAnnotation.DEFAULT_REFERENCED_METHOD_NAME);
      for (PsiAnnotationMemberValue initializer : AnnotationUtil.arrayAttributeValues(value)) {
        if (initializer instanceof PsiAnnotation && isCheckerDefault(nullable, types, (PsiAnnotation)initializer)) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean isNullityAnnotationReference(boolean nullable, PsiClassObjectAccessExpression value) {
    PsiClass valueClass = PsiUtil.resolveClassInClassTypeOnly(value.getOperand().getType());
    NullableNotNullManager instance = NullableNotNullManager.getInstance(value.getProject());
    return valueClass != null && (nullable ? instance.getNullables() : instance.getNotNulls()).contains(valueClass.getQualifiedName());
  }

  private static boolean hasAppropriateTarget(PsiAnnotation.TargetType[] types, PsiAnnotationMemberValue locations) {
    Set<String> locationNames = ContainerUtil.map2SetNotNull(AnnotationUtil.arrayAttributeValues(locations), l -> l instanceof PsiReferenceExpression ? ((PsiReferenceExpression)l).getReferenceName() : null);
    if (locationNames.contains("ALL")) return true;
    for (PsiAnnotation.TargetType type : types) {
      if (type == PsiAnnotation.TargetType.FIELD) return locationNames.contains("FIELD");
      if (type == PsiAnnotation.TargetType.METHOD) return locationNames.contains("RETURN");
      if (type == PsiAnnotation.TargetType.PARAMETER) return locationNames.contains("PARAMETER");
    }
    return false;
  }
}
