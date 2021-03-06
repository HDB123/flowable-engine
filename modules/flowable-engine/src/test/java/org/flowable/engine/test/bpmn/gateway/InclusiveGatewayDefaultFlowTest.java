/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.engine.test.bpmn.gateway;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.impl.test.PluggableFlowableTestCase;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;

public class InclusiveGatewayDefaultFlowTest extends PluggableFlowableTestCase {

    private static final String PROCESS_DEFINITION_KEY = "InclusiveGatewayDefaultFlowTest";

    private String deploymentId;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deploymentId = repositoryService.createDeployment()
                .addClasspathResource("org/flowable/engine/test/bpmn/gateway/InclusiveGatewayTest.defaultFlowTest.bpmn20.xml")
                .deploy().getId();
    }

    @Override
    protected void tearDown() throws Exception {
        repositoryService.deleteDeployment(deploymentId, true);
        super.tearDown();
    }

    public void testDefaultFlowOnly() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).activityId("usertask1").singleResult();
        assertNotNull(execution);
        assertEquals("usertask1", execution.getActivityId());
    }

    public void testCompatibleConditionFlow() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("var1", "true");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);

        Execution execution = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).activityId("usertask2").singleResult();
        assertNotNull(execution);
        assertEquals("usertask2", execution.getActivityId());
    }
}
