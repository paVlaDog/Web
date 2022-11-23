<#import "commons.ftlh" as c>

<@c.page>
    <#if user?? && user?has_content>
        <div>${user.id}</div>
        <div>${user.login}</div>
        <div>${user.creationTime}</div>
    <#else>
        <div>No such user ;(</div>
    </#if>

</@c.page>
