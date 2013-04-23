#!/bin/bash
##
# Copyright 2013 by Cloudsoft Corp.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
##
#set -x # DEBUG

JAVA=$(which java)
if [ "${JAVA_HOME}" ]; then 
    JAVA=${JAVA_HOME}/bin/java
fi
if [ ! -x "${JAVA}" ]; then
  echo "Cannot find java. Set JAVA_HOME or add java to the path."
  exit 1
fi

if [ ! -f "builds-*.jar" ]; then
  echo "This command must be run from the directory where it is installed."
  exit 1
fi

${JAVA} -Xms256m -Xmx1024m -XX:MaxPermSize=1024m -classpath "conf/:*:patch/*:lib/*" \
        io.cloudsoft.builds.Infrastructure "$@"
