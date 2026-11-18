package demoblaze;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejecuta todos los features de demoblaze (signup y login) y genera:
 *  - target/karate-reports/karate-summary.html   (reporte nativo de Karate)
 *  - target/cucumber-html-reports/overview-features.html (reporte Cucumber)
 */
class DemoblazeApiTest {

    @Test
    void ejecutarPruebasApiDemoblaze() {
        Results results = Runner.path("classpath:demoblaze")
                .tags("~@ignore", "~@defecto")
                .outputCucumberJson(true)
                .parallel(1);
        generarReporteCucumber(results.getReportDir());
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    private static void generarReporteCucumber(String carpetaReportesKarate) {
        List<String> jsons;
        try (Stream<Path> archivos = Files.walk(Paths.get(carpetaReportesKarate))) {
            jsons = archivos.map(Path::toString)
                    .filter(p -> p.endsWith(".json"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        Configuration config = new Configuration(new File("target"), "Reto TCS - API Demoblaze (Karate)");
        new ReportBuilder(jsons, config).generateReports();
    }
}
