package schemacrawler.tools.command.text.diagram;

import static java.nio.file.Files.lines;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizEngine;
import guru.nidi.graphviz.engine.GraphvizJdkEngine;
import guru.nidi.graphviz.engine.GraphvizV8Engine;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerRuntimeException;
import schemacrawler.tools.command.text.diagram.options.DiagramOutputFormat;

public final class GraphvizJavaExecutorUtility {

  private static final Logger LOGGER =
      Logger.getLogger(GraphvizJavaExecutorUtility.class.getName());

  /**
   * Need a static method to account for imports of pure Java Graphviz library.
   *
   * @param dotFile Path to DOT file
   * @param outputFile Path to output file
   * @param diagramOutputFormat Output format
   * @throws SchemaCrawlerException Thrown on an exception
   */
  public static void generateGraph(
      final Path dotFile, final Path outputFile, final DiagramOutputFormat diagramOutputFormat) {
    requireNonNull(dotFile, "No DOT file provided");
    requireNonNull(outputFile, "No diagram output file provided");
    requireNonNull(diagramOutputFormat, "No diagram output format provided");

    try {
      // Strip all line breaks, in order to use the pure Java engine for
      // Graphviz
      final String dotSource = lines(dotFile).collect(joining(" "));

      final List<GraphvizEngine> engines = loadGraphvizEngines();
      Graphviz.useEngine(engines);

      final Format format = map(diagramOutputFormat);
      if (format == null) {
        throw new SchemaCrawlerRuntimeException(
            String.format("Unsupported output format <%s>", diagramOutputFormat));
      }

      Graphviz.fromString(dotSource).render(format).toFile(outputFile.toFile());
    } catch (final Throwable e) {
      throw new SchemaCrawlerRuntimeException(
          String.format("Cannot generate diagram from <%s>", dotFile), e);
    }
  }

  private static List<GraphvizEngine> loadGraphvizEngines() {
    final List<GraphvizEngine> engines = new ArrayList<>();

    try {
      final GraphvizEngine engine = new GraphvizJdkEngine();
      engines.add(engine);
      LOGGER.log(Level.CONFIG, "Loaded GraphvizJdkEngine");
    } catch (final Throwable e) {
      LOGGER.log(Level.CONFIG, "Cannot load GraphvizJdkEngine");
    }

    try {
      final GraphvizEngine engine = new GraphvizV8Engine();
      engines.add(engine);
      LOGGER.log(Level.CONFIG, "Loaded GraphvizV8Engine");
    } catch (final Throwable e) {
      LOGGER.log(Level.CONFIG, "Cannot load GraphvizV8Engine");
    }

    return engines;
  }

  private static Format map(final DiagramOutputFormat diagramOutputFormat) {
    if (diagramOutputFormat == null) {
      return null;
    }
    final Format format;
    switch (diagramOutputFormat) {
      case svg:
        format = Format.SVG;
        break;
      case png:
        format = Format.PNG;
        break;
      case ps:
        format = Format.PS;
        break;
      case xdot:
        format = Format.XDOT;
        break;
      case plain:
        format = Format.PLAIN;
        break;
      default:
        format = null;
        break;
    }
    return format;
  }

  private GraphvizJavaExecutorUtility() {
    // Prevent instantiation
  }
}
