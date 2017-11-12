package org.x.job.executor.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.x.job.commons.job.Job;
import org.x.job.executor.pipeline.PipelineExecutor;
import org.x.job.executor.receive.TaskHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务分发器
 * @author Eightmonth
 */
public class Distributor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Distributor.class);

    @Autowired
    private PipelineExecutor pipelineExecutor;

    public void doDistribute() throws Exception {

        if(LOGGER.isInfoEnabled())
            LOGGER.info(">>>>>>>>> Task action <<<<<<<<");
        List<String> machines = TaskHandler.getMachines().get();
        List<List<String>> firstUUIDs = TaskHandler.getMasterJobs().get();
        List<List<Job>> allJobs = new ArrayList<>();

        if(LOGGER.isInfoEnabled())
            LOGGER.info(">>>>>>>>> Task getting <<<<<<<<");

        for(List<String> secondUUIDs : firstUUIDs){
            allJobs.add(getJobByUUID(secondUUIDs));
        }

        if(LOGGER.isInfoEnabled())
            LOGGER.info(">>>>>>>>> Task got end <<<<<<<<");

        if(LOGGER.isInfoEnabled())
            LOGGER.info(">>>>>>>>> Task executing <<<<<<<<");
        // 传入哪个地址，然后根据地址执行哪台的Job，这里可以考虑成多线程执行。
        // 同时或许需要同步锁。（参考），已有threadLocal
        for (String addr : machines) {
            executor(addr);
        }
        if(LOGGER.isInfoEnabled())
            LOGGER.info(">>>>>>>>> Task end <<<<<<<<");
    }

    public List<Job> getJobByUUID(List<String> uuid) throws Exception {
        // 根据UUID从第三方存储得到job的文件，进行编译并得到Job对象。
        return null;
    }

    public void executor(String addr) throws Exception {
        pipelineExecutor.doIt();
    }
}
