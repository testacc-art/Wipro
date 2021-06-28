#!/bin/bash

# Clean any previous Dokka docs.
rm -rf reports

# Apply Spotless and generate Documentation
./gradlew clean spotlessApply dokkaHtmlMultiModule