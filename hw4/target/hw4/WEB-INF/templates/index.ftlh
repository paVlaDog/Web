<#import "./common.ftlh" as c>

<@c.page>
    <div style="display: flex; flex-direction: column-reverse">
        <#list posts as post>
            <@c.generatePost post "Part"/>
        </#list>
    </div>
</@c.page>
