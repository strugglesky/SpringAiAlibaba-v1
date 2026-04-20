package org.example.utils;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

public class DateTimeTools {
    @Tool(description = "获取现在的时间,返回字符串", returnDirect = false)
    public String getNowTime() {
        return "现在时间是：" + java.time.LocalDateTime.now();
    }

}
