LC4J
--
LangChain for Java.  
WIP.

支持的模型列表：

| 模型名称       | √-支持 其他-不支持原因          |
|------------|------------------------|
| ChatGPT3.5 | √                      |
| ChatGPT4   | √                      |
| 星火大模型      | 不支持——内置Agent干扰Agent流程。 |
| 文心一言       | √                      |
| 通义千问       | √                      |
| 智谱清言       | 不支持——太容易莫名其妙截断。        |
| 盘古大模型      | WIP                    |

硬依赖：  
[BukkitHTTP](https://github.com/BukkitHTTP/BukkitHTTP) 函数库。  
软依赖：  
[Crawl4J](https://github.com/huzpsb/crawl4j) 构建知识库。

基本代码结构：

```mermaid
graph LR
    Input(用户输入)
    Flow(处理流程)
    Input --> Flow
    Agent --> Flow
    Flow --> Output(输出)
    subgraph agent
        Agent
        Credential -->|CredentialManager| LLM
        LLM --> Agent
        Transformer --> Agent
    end    
```

C4J知识注入：

```mermaid
graph TB
    Crawl4J -->|数据抓取| c4j.db
    Input(用户输入) --> Inject[知识注入]
    Input -->|向量搜索| C4JTransformer
    C4JTransformer --> Transformer
    c4j.db --> Transformer
    Transformer -->|有用的知识| Inject
    Inject --> Flow(处理后的用户输入)
    Processor[模型回答]
    LLM[LLM或者其他Agent]
    LLM --> Processor
    Flow --> Processor
    Processor --> Output(最终输出)
```

LaTeX格式生成：

```mermaid
graph TB
    Input(用户输入)
    Input(用户输入) --> Inject[Prompt注入]
    LatexTransformer --> Transformer
    Transformer -->|内置Prompt| Inject
    Inject --> Flow(处理后的用户输入)
    LLMPrompt[LLM或者其他Agent]
    Processor[模型回答]
    LLMPrompt --> Processor
    Flow --> Processor
    Processor --> Output(模型输出)
    Output --> LatexTransformer
    LatexTransformer -->|LaTeX部分| LatexOutput(最终输出)
```

Sample:  
C4J效果展示
![C4J](pic\C4J.png)
TEX效果展示
![LaTeX](pic\TEX.png)

Author:  
@huzpsb  
All rights reserved.
