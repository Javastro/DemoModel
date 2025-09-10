Demo Data Model Project
===========================

A smallish model that can be used to illustrate various features of IVOA services that use it.


To create this in your local maven cache
```terminaloutput
gradle -x test clean  publishtomavenlocal
```

[![Java CI with Gradle](https://github.com/Javastro/DemoModel/actions/workflows/gradletest.yml/badge.svg)](https://github.com/Javastro/DemoModel/actions/workflows/gradletest.yml)

__FIXME__ This demo model illustrates a problem with the JSON serialization of contained references - it is really a problem with the generated schema

```json
{
  "valid" : false,
  "evaluationPath" : "/properties/DemoDMModel/properties/content/items/anyOf/0/$ref/properties/photometryFilter/items/$ref",
  "schemaLocation" : "https://ivoa.net/dm/DemoDM.vo-dml.json#/$defs/PhotometryFilter",
  "instanceLocation" : "/DemoDMModel/content/1/photometryFilter/1",
  "errors" : {
    "type" : "integer found, object expected"
  }
}
    
```

[![site deploy](https://github.com/Javastro/DemoModel/actions/workflows/site.yml/badge.svg)](https://github.com/Javastro/DemoModel/actions/workflows/site.yml)

[Read the model documentation](https://javastro.github.io/DemoModel/)