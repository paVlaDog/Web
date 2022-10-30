<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <div style="display: flex; flex-direction: column-reverse">
        <#list posts as post>
            <#if user_id?has_content && post.user_id == user_id>
                <@c.generatePost post "part"/>
            </#if>
        </#list>
    </div>


</@c.page>
