/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.plugins.github.api.data;

import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.io.mandatory.Mandatory;
import org.jetbrains.io.mandatory.RestModel;

import java.util.Date;

@RestModel
@SuppressWarnings("UnusedDeclaration")
public class GithubIssue {
  private String url;
  @Mandatory private String htmlUrl;
  @Mandatory private Long number;
  @Mandatory private String state;
  @Mandatory private String title;
  private String body;

  @Mandatory private GithubUser user;
  private GithubUser assignee;

  private Date closedAt;
  @Mandatory private Date createdAt;
  @Mandatory private Date updatedAt;

  @NotNull
  public String getHtmlUrl() {
    return htmlUrl;
  }

  public long getNumber() {
    return number;
  }

  @NotNull
  public String getState() {
    return state;
  }

  @NotNull
  public String getTitle() {
    return title;
  }

  @NotNull
  public String getBody() {
    return StringUtil.notNullize(body);
  }

  @NotNull
  public GithubUser getUser() {
    return user;
  }

  @Nullable
  public GithubUser getAssignee() {
    return assignee;
  }

  @Nullable
  public Date getClosedAt() {
    return closedAt;
  }

  @NotNull
  public Date getCreatedAt() {
    return createdAt;
  }

  @NotNull
  public Date getUpdatedAt() {
    return updatedAt;
  }
}
