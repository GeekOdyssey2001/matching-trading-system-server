---
name: troubleshoot-maven-import
description: 排查本项目 Maven 无法导入、IDEA 源码或依赖大面积报红的问题，并根据实际错误恢复项目导入。
---

# 排查 Maven 项目导入

先保留现有改动，检查根目录 `pom.xml`、IDEA Maven 配置与 Maven 实际输出。运行 `mvn -version` 和 `mvn -o validate`；离线模式缺少缓存时，区别 POM 解析错误与依赖下载失败，必要时在网络可用的环境重试。`validate` 成功只说明项目模型可解析，不证明依赖已下载、源码可编译或 IDEA 导入成功。

## 已发生的问题

- 新增 `com.google.guava:guava` 时未指定版本，父 POM 未管理该版本，导致 `dependencies.dependency.version ... is missing`，IDEA 可能无法建立 Maven 模块，源码和依赖随之报红。修复时为该依赖指定适用版本，或通过已有 dependencyManagement 管理，然后重新验证。2026-11-26 使用 `33.5.0-jre` 修复；将来按实际依赖和兼容性检查，不要无条件替换用户版本。
- 之前 `org.mybatis.spring.boot:mybatis-spring-boot-starter` 也出现过缺少版本，解决方式相同。检查所有没有版本的新增依赖是否由父 POM 或 BOM 管理，不要假设每个第三方库都会被管理。
- 之前 IDEA 使用 Maven Wrapper 时出现 `ArtifactHandlerManager` 的 Guice 注入错误。本机 `/usr/local/maven` 的 Maven 3.9.9 可以启动并解析项目；如果该错误再次出现，检查 IDEA 的 Maven home、JDK 与 Wrapper，并可尝试在 Settings → Build, Execution, Deployment → Build Tools → Maven 选择可用的本机 Maven。具体故障根因未确认，不要断言 Wrapper 已损坏。

## 完成检查

POM 解析通过后，检查依赖解析和与改动相关的构建；记录网络、缓存、权限或测试导致的限制。让 IDEA 在 Maven 工具窗口重新加载项目；必要时确认根目录的 `pom.xml` 已作为 Maven 项目添加、Project SDK 使用项目要求的 Java 版本（当前为 17）。如果命令行正常而 IDEA 仍异常，继续检查 IDEA 导入日志、Maven/JDK 设置和索引状态，不要直接删除 `.idea` 或缓存。向用户说明观察到的具体错误、修改和验证结果。

##  mvn validate
后修改完 pom.xml，先在终端执行 mvn validate，确认没有 POM 错误后，在 IDEA 的 Maven 窗口点击刷新
