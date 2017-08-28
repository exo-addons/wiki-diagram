package org.exoplatform.wiki.rendering.macro.diagram;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.block.Block;
import org.xwiki.rendering.block.RawBlock;
import org.xwiki.rendering.macro.AbstractMacro;
import org.xwiki.rendering.macro.MacroExecutionException;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.transformation.MacroTransformationContext;

import org.exoplatform.web.application.JavascriptManager;
import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.wiki.rendering.macro.diagram.DiagramMacro.DiagramMacroParameters;

/**
 * A Diagram Macro.
 */
@Component
@Named("diagram")
public class DiagramMacro extends AbstractMacro<DiagramMacroParameters> {
  /**
   * Create and initialize the descriptor of the macro.
   */
  public DiagramMacro() {
    super("Mermaid Diagram Macro", "Macro to generate mermaid diagram", DiagramMacroParameters.class);
    setDefaultCategory(DEFAULT_CATEGORY_CONTENT);
  }

  /**
   * {@inheritDoc}
   */
  public List<Block> execute(DiagramMacroParameters parameters,
                             String content,
                             MacroTransformationContext context) throws MacroExecutionException {
    StringBuilder sb = new StringBuilder();
    sb.append("<div class='mermaid-diagram hidden' wikiparam='");
    sb.append(content.replaceAll("'", "\\'").replaceAll("&g", ">"));
    sb.append("'></div>");

    RequestContext currentInstance = WebuiRequestContext.getCurrentInstance();
    if (currentInstance != null && currentInstance instanceof WebuiRequestContext) {
      JavascriptManager javascriptManager = ((WebuiRequestContext) currentInstance).getJavascriptManager();
      javascriptManager.require("SHARED/wikiMermaid");
    }

    RawBlock rawBlock = new RawBlock(sb.toString(), Syntax.XHTML_1_0);
    return Collections.singletonList((Block) rawBlock);
  }

  /**
   * {@inheritDoc}
   */
  public boolean supportsInlineMode() {
    return true;
  }

  public static class DiagramMacroParameters {
  }
}
