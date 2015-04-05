#!/bin/bash

java -Xbootclasspath/a:./ext-lib/jcstress-core.jar:./ext-lib/jopt-simple.jar:./target/jcstress-tmjee.jar -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:-RestrictContended -jar target/jcstress-tmjee.jar -time 5000
