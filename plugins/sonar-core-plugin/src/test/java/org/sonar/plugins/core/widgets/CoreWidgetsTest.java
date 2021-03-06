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
package org.sonar.plugins.core.widgets;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.reflections.Reflections;
import org.sonar.plugins.core.CorePlugin;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class CoreWidgetsTest {

  @Test
  public void widget_ids_should_be_unique() {
    Collection<CoreWidget> widgets = widgets();
    Collection<String> widgetIds = Collections2.transform(widgets, new Function<CoreWidget, String>() {
      public String apply(@Nullable CoreWidget widget) {
        return widget.getId();
      }
    });
    assertThat(widgetIds).hasSize(Sets.newHashSet(widgetIds).size());
  }

  @Test
  public void widget_templates_should_be_unique() {
    Collection<CoreWidget> widgets = widgets();
    Collection<String> templates = Collections2.transform(widgets, new Function<CoreWidget, String>() {
      public String apply(@Nullable CoreWidget widget) {
        return widget.getTemplatePath();
      }
    });
    assertThat(templates).hasSize(Sets.newHashSet(templates).size());
  }

  @Test
  public void widget_titles_should_be_unique() {
    Collection<CoreWidget> widgets = widgets();
    Collection<String> templates = Collections2.transform(widgets, new Function<CoreWidget, String>() {
      public String apply(@Nullable CoreWidget widget) {
        return widget.getTitle();
      }
    });
    assertThat(templates).hasSize(Sets.newHashSet(templates).size());
  }

  @Test
  public void should_find_templates() {
    for (CoreWidget widget : widgets()) {
      assertThat(widget.getClass().getResource(widget.getTemplatePath()))
        .as("Template not found: " + widget.getTemplatePath())
        .isNotNull();
    }
  }

  @Test
  public void should_be_registered_as_an_extension() {
    for (CoreWidget widget : widgets()) {
      assertThat(new CorePlugin().getExtensions()).as("Widget not registered: " + widget.getClass()).contains(widget.getClass());
    }
  }

  @Test
  public void should_find_core_widgets() {
    assertThat(widgets().size()).isGreaterThan(23);
  }

  private Set<Class<? extends CoreWidget>> widgetClasses() {
    String[] packages = {"org.sonar.plugins.core.widgets", "org.sonar.plugins.core.widgets.issues"};
    return new Reflections(packages).getSubTypesOf(CoreWidget.class);
  }

  private Collection<CoreWidget> widgets() {
    return Collections2.transform(widgetClasses(), new Function<Class<? extends CoreWidget>, CoreWidget>() {
      public CoreWidget apply(@Nullable Class<? extends CoreWidget> aClass) {
        try {
          return aClass.newInstance();
        } catch (Exception e) {
          throw Throwables.propagate(e);
        }
      }
    });
  }
}
