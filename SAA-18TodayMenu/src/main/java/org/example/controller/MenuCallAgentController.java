package org.example.controller;

import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgent;
import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgentOptions;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAgentApi;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zzyybs@126.com
 * @create 2025-09-11 19:04
 * @Description TODO
 */
@RestController
public class MenuCallAgentController
{
    // 百炼平台的appid
    @Value("${spring.ai.dashscope.agent.options.app-id}")
    private String appId;

//    // 百炼云平台的智能体接口对象
//    private DashScopeAgent dashScopeAgent;
//
//    public MenuCallAgentController(DashScopeAgentApi dashScopeAgentApi)
//    {
//        this.dashScopeAgent = new DashScopeAgent(dashScopeAgentApi);
//    }
//
//    @GetMapping(value = "/eatAgent")
//    public String eatAgent(@RequestParam(name = "msg",defaultValue = "青椒土豆丝盖饭有什么特色") String msg)
//    {
//        DashScopeAgentOptions options = DashScopeAgentOptions.builder().withAppId(appId).build();
//
//        Prompt prompt = new Prompt(msg, options);
//
//        return dashScopeAgent.call(prompt).getResult().getOutput().getText();
//    }
    //百炼云平台的智能体接口对象
    private DashScopeAgent dashScopeAgent;

    public MenuCallAgentController(DashScopeAgentApi dashScopeAgentApi){
        this.dashScopeAgent = new DashScopeAgent(dashScopeAgentApi);
    }

    @GetMapping(value = "/eatAgent")
    public String eatAgent(@RequestParam(name = "msg",defaultValue = "青椒土豆丝盖饭有什么特色") String msg)
    {
        DashScopeAgentOptions agentOptions = DashScopeAgentOptions.builder().withAppId(appId).build();
        return dashScopeAgent.call(new Prompt(msg, agentOptions))
                .getResult().getOutput().getText();
    }
}
