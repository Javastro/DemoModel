package org.ivoa.dm.demodm;
/*
 * Created on 10/05/2023 by Paul Harrison (paul.harrison@manchester.ac.uk).
 */

import org.ivoa.dm.demodm.instances.InstanceGenerator;
import org.ivoa.vodml.testing.AutoRoundTripWithValidationTest;

/**
 * This will run a XML and JSON round trip test on the model inst
 */
public class DemoModelTest extends AutoRoundTripWithValidationTest<DemoDMModel> {
    @Override
    public DemoDMModel createModel() {

       return InstanceGenerator.createModel();
    }

    @Override
    public void testModel(DemoDMModel mymodelModel) {
        //this could do specialized testing on the model instance
    }
}
