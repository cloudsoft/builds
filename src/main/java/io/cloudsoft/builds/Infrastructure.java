/*
 * Copyright 2013 by Cloudsoft Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cloudsoft.builds;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import brooklyn.catalog.Catalog;
import brooklyn.entity.Effector;
import brooklyn.entity.Group;
import brooklyn.entity.basic.AbstractApplication;
import brooklyn.entity.basic.BasicGroup;
import brooklyn.entity.basic.MethodEffector;
import brooklyn.entity.proxying.EntitySpecs;
import brooklyn.launcher.BrooklynLauncher;
import brooklyn.management.internal.LocalManagementContext;
import brooklyn.util.CommandLineUtil;

import com.google.common.collect.Lists;

@Catalog(name="Infrastructure", description="Infrastructure")
public class Infrastructure extends AbstractApplication {

    private static final Logger LOG = LoggerFactory.getLogger(Infrastructure.class);

    public static final Effector<Void> INIT = new MethodEffector<Void>(Infrastructure.class, "init");

    Group jenkins;

    @Override
    public void init() {
        LOG.info("Initializing");
        jenkins = addChild(EntitySpecs.spec(BasicGroup.class)
                .displayName("Jenkins"));
    }

    public static void main(String[] argv) {
        List<String> args = Lists.newArrayList(argv);
        String port =  CommandLineUtil.getCommandLineOption(args, "--port", "8081+");
        String location = CommandLineUtil.getCommandLineOption(args, "--location", "monterey");

        LocalManagementContext mgmt = new LocalManagementContext();

        BrooklynLauncher.newInstance()
                .application(EntitySpecs.appSpec(Infrastructure.class)
                        .displayName("Infrastructure"))
                .webconsolePort(port)
                .managementContext(mgmt)
                .location(location)
                .start();
    }

}
