package com.ds.config.swagger;

import com.ds.SpringbootApplication;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.springframework.boot.SpringApplication;

import java.net.URL;
import java.nio.file.Paths;

/**
 * @Description: 生成markdown文档
 * @Author lt
 * @Date 2023/5/8 14:34
 */
public class GenerateMarkdownDoc {

    public static void generateMarkdownDocsToFile() throws Exception {
        // 输出Markdown到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8078/v2/api-docs?group=1.0"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/markdown/generated/all"));
    }

    public static void main(String[] args) {
        try {
            SpringApplication.run(SpringbootApplication.class, args);
            GenerateMarkdownDoc.generateMarkdownDocsToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
