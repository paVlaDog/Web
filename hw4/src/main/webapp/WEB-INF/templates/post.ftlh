<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id?has_content && post_id?is_number>
        <#assign ind = c.findIndex(posts, "id", post_id)/>
    <#else>
        <#assign ind = -1/>
    </#if>

    <#if ind == -1>
        <@c.notfound/>
    <#else>
        <#assign post = c.findCur(posts, ind)!/>
        <@c.generatePost post "All"/>
    </#if>
</@c.page>
