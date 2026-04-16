package org.example.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesizer;
import com.alibaba.dashscope.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;


@RestController
public class Text2VoiceController {
    // 每个音色支持的语言不同，合成日语、韩语等非中文语言时，需选择支持对应语言的音色。详见CosyVoice音色列表。
    private static String model = "cosyvoice-v3-flash";
    // 音色
    private static String voice = "longanyang";

    private byte[] generateAudio(String text) {
        SpeechSynthesisParam param =
                SpeechSynthesisParam.builder()
                        .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                        .model(model)
                        .voice(voice)
                        .build();

        SpeechSynthesizer synthesizer = new SpeechSynthesizer(param, null);
        ByteBuffer audio = null;
        try {
            audio = synthesizer.call(text);
        } catch (Exception e) {
            throw new RuntimeException("音频生成失败: " + e.getMessage(), e);
        } finally {
            synthesizer.getDuplexApi().close(1000, "bye");
        }

        if (audio != null) {
            return audio.array();
        }
        throw new RuntimeException("音频数据为空");
    }

    @GetMapping("/t2v/voice")
    public ResponseEntity<byte[]> voice(@RequestParam(name = "msg", defaultValue = "温馨提醒，支付宝到账100元请注意查收") String msg) {
        Constants.baseWebsocketApiUrl = "wss://dashscope.aliyuncs.com/api-ws/v1/inference";

        byte[] audioData = generateAudio(msg);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
        headers.setContentDispositionFormData("attachment", "output.mp3");
        headers.setContentLength(audioData.length);

        return ResponseEntity.ok().headers(headers).body(audioData);
    }

}
