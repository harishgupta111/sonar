/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.wsclient.issue;

import org.json.simple.JSONValue;
import org.sonar.wsclient.component.Component;
import org.sonar.wsclient.rule.Rule;
import org.sonar.wsclient.unmarshallers.JsonUtils;
import org.sonar.wsclient.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @since 3.6
 */
class IssueJsonParser {

  Issues parseIssues(String json) {
    Issues result = new Issues();
    Map jsonRoot = (Map) JSONValue.parse(json);
    List<Map> jsonIssues = (List) jsonRoot.get("issues");
    if (jsonIssues != null) {
      for (Map jsonIssue : jsonIssues) {
        result.add(new Issue(jsonIssue));
      }
    }
    parseRules(result, jsonRoot);
    parseUsers(result, jsonRoot);
    parseComponents(result, jsonRoot);
    parseProjects(result, jsonRoot);
    parseActionPlans(result, jsonRoot);
    parsePaging(result, jsonRoot);
    return result;
  }

  private void parsePaging(Issues result, Map jsonRoot) {
    Map paging = (Map) jsonRoot.get("paging");
    result.setPaging(new Paging(paging));
    result.setMaxResultsReached(JsonUtils.getBoolean(jsonRoot, "maxResultsReached"));
  }

  private void parseProjects(Issues result, Map jsonRoot) {
    List<Map> jsonProjects = (List) jsonRoot.get("projects");
    if (jsonProjects != null) {
      for (Map jsonProject : jsonProjects) {
        result.addProject(new Component(jsonProject));
      }
    }
  }

  private void parseComponents(Issues result, Map jsonRoot) {
    List<Map> jsonComponents = (List) jsonRoot.get("components");
    if (jsonComponents != null) {
      for (Map jsonComponent : jsonComponents) {
        result.addComponent(new Component(jsonComponent));
      }
    }
  }

  private void parseUsers(Issues result, Map jsonRoot) {
    List<Map> jsonUsers = (List) jsonRoot.get("users");
    if (jsonUsers != null) {
      for (Map jsonUser : jsonUsers) {
        result.add(new User(jsonUser));
      }
    }
  }

  private void parseRules(Issues result, Map jsonRoot) {
    List<Map> jsonRules = (List) jsonRoot.get("rules");
    if (jsonRules != null) {
      for (Map jsonRule : jsonRules) {
        result.add(new Rule(jsonRule));
      }
    }
  }

  private void parseActionPlans(Issues result, Map jsonRoot) {
    List<Map> jsonRules = (List) jsonRoot.get("actionPlans");
    if (jsonRules != null) {
      for (Map jsonRule : jsonRules) {
        result.add(new ActionPlan(jsonRule));
      }
    }
  }

  List<String> parseTransitions(String json) {
    List<String> transitions = new ArrayList<String>();
    Map jRoot = (Map) JSONValue.parse(json);
    List<String> jTransitions = (List) jRoot.get("transitions");
    for (String jTransition : jTransitions) {
      transitions.add(jTransition);
    }
    return transitions;
  }

  List<String> parseActions(String json) {
    List<String> actions = new ArrayList<String>();
    Map jRoot = (Map) JSONValue.parse(json);
    List<String> jActions = (List) jRoot.get("actions");
    for (String jAction : jActions) {
      actions.add(jAction);
    }
    return actions;
  }
}
