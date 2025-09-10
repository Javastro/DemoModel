package org.ivoa.dm.demodm.instances;


/*
 * Created on 02/09/2025 by Paul Harrison (paul.harrison@manchester.ac.uk).
 */

import org.ivoa.dm.demodm.DemoDMModel;
import org.ivoa.dm.demodm.PhotometricSystem;
import org.ivoa.dm.demodm.PhotometryFilter;
import org.ivoa.dm.demodm.catalog.*;
import org.ivoa.dm.demodm.catalog.inner.SourceCatalogue;
import org.ivoa.dm.ivoa.RealQuantity;
import org.ivoa.vodml.stdtypes.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.ivoa.dm.demodm.PhotometryFilter.createPhotometryFilter;
import static org.ivoa.dm.demodm.catalog.LuminosityMeasurement.createLuminosityMeasurement;
import static org.ivoa.dm.demodm.catalog.SDSSSource.createSDSSSource;
import static org.ivoa.dm.demodm.catalog.SkyCoordinate.createSkyCoordinate;
import static org.ivoa.dm.demodm.catalog.inner.SourceCatalogue.createSourceCatalogue;

public class InstanceGenerator {

   public static DemoDMModel createModel() {
      // create the model instance here.
      DemoDMModel retval = new DemoDMModel();

      final Unit jansky = new Unit("Jy");
      final Unit degree = new Unit("degree");
      final Unit GHz = new Unit("GHz");
      final SkyCoordinateFrame frame =
            new SkyCoordinateFrame()
                  .withName("J2000")
                  .withEquinox("J2000.0")
                  .withDocumentURI("http://coord.net");

      final AlignedEllipse ellipseError = new AlignedEllipse(.2, .1);
      SDSSSource sdss =
            new SDSSSource()
                  .withPositionError(ellipseError); // UNUSED, but just checking position error
      // subsetting.
      sdss.setPositionError(ellipseError);
      AlignedEllipse theError = sdss.getPositionError();

      final List<PhotometryFilter> filters =
            List.of(
                  createPhotometryFilter(
                        fl -> {
                           fl.bandName = "C-Band";
                           fl.spectralLocation = new RealQuantity(5.0, GHz);
                           fl.dataValidityFrom = new Date();
                           fl.dataValidityTo = new Date();
                           fl.description = "radio band";
                           fl.name = fl.bandName;
                        }),
                  createPhotometryFilter(
                        fl -> {
                           fl.bandName = "L-Band";
                           fl.spectralLocation = new RealQuantity(1.5, GHz);
                           fl.dataValidityFrom = new Date();
                           fl.dataValidityTo = new Date();
                           fl.description = "radio band";
                           fl.name = fl.bandName;
                        }));

      PhotometricSystem ps = new PhotometricSystem("test photometric system", 1, filters);
      SourceCatalogue sc = createSourceCatalogue(
            c -> {
               c.name = "testCat";
               c.entry =
                     Arrays.asList(
                           createSDSSSource(
                                 s -> {
                                    s.name = "testSource";
                                    s.classification = SourceClassification.TARGET;
                                    s.objectType = "cepheid";
                                    s.position =
                                          createSkyCoordinate(
                                                co -> {
                                                   co.frame = frame;
                                                   co.latitude = new RealQuantity(52.5, degree);
                                                   co.longitude = new RealQuantity(2.5, degree);
                                                });
                                    s.positionError = ellipseError; // note subsetting
                                    // forces compile need
                                    // AlignedEllipse

                                    s.luminosity =
                                          Arrays.asList(
                                                createLuminosityMeasurement(
                                                      l -> {
                                                         l.description = "lummeas";
                                                         l.type = LuminosityType.FLUX;
                                                         l.value = new RealQuantity(2.5, jansky);
                                                         l.error = new RealQuantity(.25, jansky);
                                                         l.filter = filters.get(0);
                                                      }),
                                                createLuminosityMeasurement(
                                                      l -> {
                                                         l.description = "lummeas2";
                                                         l.filter = filters.get(1);
                                                         l.type = LuminosityType.FLUX;
                                                         l.value = new RealQuantity(3.5, jansky);
                                                         l.error =
                                                               new RealQuantity(.25, jansky); // TODO should be allowed to be null
                                                      }));
                                 }));
            });

      sc.setATestMore(new ArrayList<>());
      retval.addContent(sc);
      retval.addContent(ps);
      return retval;
   }
}
