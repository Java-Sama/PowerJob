# 设置变量，由 .devcontainer.json 中的 args 传入
ARG VARIANT
# 指定 node 版本
FROM debain:${VARIANT}

# 设置编码
ENV LANG C.UTF-8

# 挂载 volume
VOLUME ["/root/.m2"]
# 设置 bash 为默认 shell
ENV SHELL /bin/bash

# 安装一些工具
RUN if command -v apt-get &> /dev/null; then \
        apt-get update && apt-get install -y sudo curl git wget apt-transport-https ; \
        sh -c "$(wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | tee /etc/apt/trusted.gpg.d/adoptium.gpg > /dev/null)"; \
        sh -c "$(echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list)"; \
        apt-get update && apt-get install -y temurin-8-jdk ; \
    else \
        echo "apt-get command does not exist."; \
    fi
